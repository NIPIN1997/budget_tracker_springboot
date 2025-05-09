package com.example.demo.dto;

public record MonthlyExpenseAnalysisResponseDTOImpl(double amount, String expenseType) implements MonthlyExpenseAnalysisResponseDTO{

	@Override
	public double getAmount() {
		return amount;
	}

	@Override
	public String getExpenseType() {
		return expenseType;
	}

}
