package com.example.demo.enums;

public enum Category {

	INCOME, EXPENSE;
	
	public String getFormattedName() {
		return name().toLowerCase();
	}
}
