package com.springboot.rewardcal.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rewardcal.bean.CustomerBean;
import com.springboot.rewardcal.bean.CustomerInvoiceBean;
import com.springboot.rewardcal.repository.CustomerRepository;

/*  The RestController calRewards method accepts a PathVariable'uid' from RestEndpoint and will be passed to DB query
 *  Further rewards will be calculated
  */

@RestController
public class RewardCalController {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	CustomerRepository customerRepository; /*
											 * Autowiring required bean/repo/ Jdbc templates to use Spring magic - Auto
											 * object creation
											 */
	@Autowired
	CustomerBean customerBean;

	Logger logger = LoggerFactory.getLogger(RewardCalController.class);

	/* Endpoint to get record of rewards using userId */
	@PostMapping(value = "/showRewards/{uid}")
	public String calculateRewardsById(@PathVariable String uid) {
		if (uid != null && uid.matches("[A-Za-z0-9_]+")) {

			int totalRewards = 0; /* To hold the values of each record */
			int monthlyRewards = 0;
			int quarterRewards = 0;
			int result = 0;
			try {
				List<CustomerInvoiceBean> customerInvoiceList = customerRepository.getCustomerRecordsById(uid);
				if (customerInvoiceList.size() > 0 && !customerInvoiceList.isEmpty()) {
					logger.info("The billing list isn't empty"); // Just to track the flow
					customerBean.setCustomerId(uid);
					for (CustomerInvoiceBean invoiceBean : customerInvoiceList) {
						double billed_amt = invoiceBean.getBilled_amt();
						if (billed_amt > 100 && invoiceBean.getCustomerId().equalsIgnoreCase(uid)) {
							LocalDate todayDate = LocalDate.now(); // getting current date
							LocalDate billedDate = new java.sql.Date(invoiceBean.getBilled_date().getTime())
									.toLocalDate(); /*
													 * converting db date to LocalDate format
													 */
							long diffInDays = ChronoUnit.DAYS.between(billedDate, todayDate); // getting days diff
							int transctionRewards = ((int) Math.floor(billed_amt - 100) * 2)
									+ ((int) Math.floor(billed_amt - 100) / 50); // Logic to calculate rewards, as the
																					// amount is
																					// in double format used floor

							totalRewards = transctionRewards + totalRewards;

							if (diffInDays < 30) {
								monthlyRewards = transctionRewards + monthlyRewards;
							}
							if (diffInDays < 90) {
								quarterRewards = transctionRewards + quarterRewards;
							}
						}
						customerBean.setTotalRewards(totalRewards);
						customerBean.setMonthlyRewards(monthlyRewards);
						customerBean.setThreeMonthRewards(quarterRewards);
						result = update(customerBean);
					}
					if (result >= 1) {
						logger.info("Success");
					}
					logger.info("Monthly Rewards Are :" + monthlyRewards);
					logger.info("Total Rewards are :" + totalRewards);
					logger.info("Three Month Period Rewards are :" + quarterRewards);

				} else {
					return "No user records found";
				}
			} catch (NullPointerException nullPointerException) {
				nullPointerException.printStackTrace();
			} catch (Exception exception) {
				exception.printStackTrace();
			}

			return customerBean.toString();
		} else {
			return "Invalid User";
		}
	}

	/* end point to calculate all user rewards available in Database */

	@PostMapping(value = "/showRewards")
	public List<CustomerBean> calculateAllUserRewards() throws SQLException {
		List<CustomerInvoiceBean> invoiceList = customerRepository.getCustomerInvoiceRecords();
		List<CustomerBean> customerList = customerRepository.getAllCustomerRecords();
		if (customerList.size() > 0 && !customerList.isEmpty()) {
			for (CustomerBean customerBean : customerList) {
				int totalRewards = 0; /* To hold the values of each record */
				int monthlyRewards = 0;
				int quarterRewards = 0;
				int result = 0;
				String customerId = customerBean.getCustomerId();
				if (invoiceList.size() > 0 && !invoiceList.isEmpty()) {
					logger.info("The billing list isn't empty"); // Just to track the flow
					for (CustomerInvoiceBean invoiceBean : invoiceList) {
						double billed_amt = invoiceBean.getBilled_amt();
						if (billed_amt > 100 && invoiceBean.getCustomerId().equalsIgnoreCase(customerId)) {
							LocalDate todayDate = LocalDate.now(); // getting current date
							LocalDate billedDate = new java.sql.Date(invoiceBean.getBilled_date().getTime())
									.toLocalDate(); /*
													 * converting db date to LocalDate format
													 */
							long diffInDays = ChronoUnit.DAYS.between(billedDate, todayDate); // getting days diff
							int transctionRewards = ((int) Math.floor(billed_amt - 100) * 2)
									+ ((int) Math.floor(billed_amt - 100) / 50); // Logic to calculate rewards, as the
																					// amount is
																					// in double format used floor

							totalRewards = transctionRewards + totalRewards;

							if (diffInDays < 30) {
								monthlyRewards = transctionRewards + monthlyRewards;
							}
							if (diffInDays < 90) {
								quarterRewards = transctionRewards + quarterRewards;
							}
							customerBean.setCustomerId(customerId);
							customerBean.setTotalRewards(totalRewards);
							customerBean.setMonthlyRewards(monthlyRewards);
							customerBean.setThreeMonthRewards(quarterRewards);
							result = update(customerBean);
						}
					}
					if (result >= 1) {
						logger.info("Success");
					}
				}
				logger.info("Monthly Rewards Are :" + monthlyRewards);
				logger.info("Total Rewards are :" + totalRewards);
				logger.info("Three Month Period Rewards are :" + quarterRewards);

			}
			List<CustomerBean> customerListAfterRewards = customerRepository.getAllCustomerRecords();
			return customerListAfterRewards;

		} else {
			return customerList;
		}

	}

	public int update(CustomerBean customerBean) {
		return jdbcTemplate.update(
				"update customerNew " + " set total_rewards = ?, month_rewards = ?, three_month_rewards = ?"
						+ " where id = ?",
				new Object[] { customerBean.getTotalRewards(), customerBean.getMonthlyRewards(),
						customerBean.getThreeMonthRewards(), customerBean.getCustomerId() });

	}
}