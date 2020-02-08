package com.springboot.rewardcal.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.springboot.rewardcal.bean.CustomerBean;

/* This class get the ResultSet from Repository class and maps the column values to CustomerBean class
 * Just used different styles for two different calls CustomerInvoiceBean and CustomerBean in the way data retreived */

class CustomerMapper implements RowMapper {

	@Override
	public CustomerBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		CustomerBean customerBean = new CustomerBean();

		customerBean.setCustomerId(resultSet.getString("id"));
		customerBean.setMonthlyRewards(resultSet.getInt("month_rewards"));
		customerBean.setThreeMonthRewards(resultSet.getInt("three_month_rewards"));
		customerBean.setTotalRewards(resultSet.getInt("total_rewards"));
		return customerBean;

	}
}