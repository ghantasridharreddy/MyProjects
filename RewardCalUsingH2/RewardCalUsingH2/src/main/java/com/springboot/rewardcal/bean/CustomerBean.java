package com.springboot.rewardcal.bean;

import org.springframework.stereotype.Component;

/* a Simple Bean class */

@Component
public class CustomerBean {

	String customerId;
	int totalRewards;
	int monthlyRewards;
	int threeMonthRewards;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public int getTotalRewards() {
		return totalRewards;
	}

	public void setTotalRewards(int totalRewards) {
		this.totalRewards = totalRewards;
	}

	public int getMonthlyRewards() {
		return monthlyRewards;
	}

	public void setMonthlyRewards(int monthlyRewards) {
		this.monthlyRewards = monthlyRewards;
	}

	public int getThreeMonthRewards() {
		return threeMonthRewards;
	}

	public void setThreeMonthRewards(int threeMonthRewards) {
		this.threeMonthRewards = threeMonthRewards;
	}

	@Override
	public String toString() {
		return "CustomerBean [customerId=" + customerId + ", totalRewards=" + totalRewards + ", monthlyRewards="
				+ monthlyRewards + ", threeMonthRewards=" + threeMonthRewards + "]";
	}

}
