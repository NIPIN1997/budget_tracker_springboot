package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.enums.Category;
import com.example.demo.enums.ExpenseTypes;

import lombok.Data;

@Data
public class AddBudgetRequestDTO {

	private int profileId;
	private LocalDate date;
	private Category category;
	private ExpenseTypes expenseType;
	private double amount;
	private String description;
}
