package com.example.demo.dto;

import lombok.Data;

@Data
public class TransactionViewResponseDTO {

	private String date;
	private String category;
	private String expenseType;
	private double amount;
	private String description;
}
