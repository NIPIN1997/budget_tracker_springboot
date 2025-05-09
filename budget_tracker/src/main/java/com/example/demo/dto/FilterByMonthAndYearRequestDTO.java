package com.example.demo.dto;

import java.time.Month;

import lombok.Data;

@Data
public class FilterByMonthAndYearRequestDTO {

	private int id;
	private Month month;
	private int year;
}
