package com.example.demo.dto;

import com.example.demo.enums.Category;

public interface MonthlyComparisonResponseDTO {

	int getMonthNumber();

	Category getCategory();

	double getamount();
}
