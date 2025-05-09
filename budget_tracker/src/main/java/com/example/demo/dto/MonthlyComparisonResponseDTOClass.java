package com.example.demo.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MonthlyComparisonResponseDTOClass {

	private String month;
	private List<CategoryAmountDTO> list;
	
	@Data
	@NoArgsConstructor
	public static class CategoryAmountDTO{
		private String category;
		private double amount;
		
		public CategoryAmountDTO(String category, double amount) {
			this.category=category;
			this.amount=amount;
		}
	}
}
