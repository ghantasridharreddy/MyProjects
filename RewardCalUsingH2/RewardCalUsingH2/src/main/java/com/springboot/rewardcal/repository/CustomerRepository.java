package com.springboot.rewardcal.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.rewardcal.bean.CustomerBean;
import com.springboot.rewardcal.bean.CustomerInvoiceBean;

@Repository
public class CustomerRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	CustomerBean customerBean;

	public List<CustomerInvoiceBean> getCustomerRecordsById(String uid) {

		List<CustomerInvoiceBean> customerInvoiceBean = new ArrayList<CustomerInvoiceBean>();

		try {

			customerInvoiceBean = jdbcTemplate.query("select * from customerInvoice where id =?", new Object[] { uid },
					(resultSet, rowNum) -> new CustomerInvoiceBean(resultSet.getString("id"),
							resultSet.getDate("date_of_purchase"), resultSet.getDouble("billed_amt")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerInvoiceBean;

	}

	public List<CustomerInvoiceBean> getCustomerInvoiceRecords() {
		List<CustomerInvoiceBean> allInvoiceRecords = new ArrayList<CustomerInvoiceBean>();
		try {
			allInvoiceRecords = jdbcTemplate.query("select * from customerInvoice",
					(resultSet, rowNum) -> new CustomerInvoiceBean(resultSet.getString("id"),
							resultSet.getDate("date_of_purchase"), resultSet.getDouble("billed_amt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allInvoiceRecords;
	}

	public List<CustomerBean> getAllCustomerRecords() {
		List<CustomerBean> allCustomerRecords = new ArrayList<CustomerBean>();
		try {
			allCustomerRecords = namedParameterJdbcTemplate.query("select * from customerNew", new CustomerMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allCustomerRecords;
	}

}
