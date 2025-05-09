package com.example.demo.enums;

public enum ExpenseTypes {

	GROCERIES, RENT, UTILITIES, TRANSPORTATION, INSURANCE, HEALTHCARE, EDUCATION, DINING_OUT, ENTERTAINMENT, TRAVEL,
	HOBBIES, SUBSCRIPTIONS, CLOTHING, LOAN_PAYMENT, CREDIT_CARD_PAYMENT, DONATIONS, GIFTS, TAXES, MISC;
	
	public String getFormattedName() {
	    return name().replace('_', ' ').toLowerCase();
	}

}
