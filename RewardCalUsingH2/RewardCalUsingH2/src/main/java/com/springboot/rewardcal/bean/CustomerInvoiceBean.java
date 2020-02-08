package com.springboot.rewardcal.bean;

import java.util.Date;

/* 
 A simple Bean class to class 
 */

public class CustomerInvoiceBean {

	String customerId;
	Date billed_date;
	double billed_amt;

	public CustomerInvoiceBean(String customerId, Date billed_date, double billed_amt) {
		super();
		this.customerId = customerId;
		this.billed_date = billed_date;
		this.billed_amt = billed_amt;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getBilled_date() {
		return billed_date;
	}

	public void setBilled_date(Date billed_date) {
		this.billed_date = billed_date;
	}

	public double getBilled_amt() {
		return billed_amt;
	}

	public void setBilled_amt(double billed_amt) {
		this.billed_amt = billed_amt;
	}

	@Override
	public String toString() {
		return "CustomerInvoiceBean [customerId=" + customerId + ", billed_date=" + billed_date + ", billed_amt="
				+ billed_amt + "]";
	}

}
