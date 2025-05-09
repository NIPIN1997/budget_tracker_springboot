package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BudgetDao;
import com.example.demo.dao.ProfileDao;
import com.example.demo.dto.AddBudgetRequestDTO;
import com.example.demo.dto.FilterByMonthAndYearRequestDTO;
import com.example.demo.dto.MonthlyComparisonResponseDTO;
import com.example.demo.dto.MonthlyComparisonResponseDTOClass;
import com.example.demo.dto.MonthlyComparisonResponseDTOClass.CategoryAmountDTO;
import com.example.demo.dto.MonthlyExpenseAnalysisResponseDTO;
import com.example.demo.dto.MonthlyExpenseAnalysisResponseDTOImpl;
import com.example.demo.dto.TransactionViewResponseDTO;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Profile;
import com.example.demo.enums.Category;

@Service
public class BudgetService {

	@Autowired
	private BudgetDao budgetDao;
	@Autowired
	private ProfileDao profileDao;

	public List<Budget> getBudgetsOfAProfile(int profileId) {
		List<Budget> budgets = budgetDao.getBudgetsOfAProfile(profileId);
		return budgets;
	}

	public double getTotalIncome(int profileId) {
		return budgetDao.getTotalValue(profileId, Category.INCOME.name());
	}

	public double getTotalExpense(int profileId) {
		return budgetDao.getTotalValue(profileId, Category.EXPENSE.name());
	}

	public List<Budget> filterByMonthAndYear(FilterByMonthAndYearRequestDTO filterByMonthAndYearRequestDTO) {
		int month = 0;
		switch (filterByMonthAndYearRequestDTO.getMonth()) {
		case JANUARY:
			month = 1;
			break;
		case FEBRUARY:
			month = 2;
			break;
		case MARCH:
			month = 3;
			break;
		case APRIL:
			month = 4;
			break;
		case MAY:
			month = 5;
			break;
		case JUNE:
			month = 6;
			break;
		case JULY:
			month = 7;
			break;
		case AUGUST:
			month = 8;
			break;
		case SEPTEMBER:
			month = 9;
			break;
		case OCTOBER:
			month = 10;
			break;
		case NOVEMBER:
			month = 11;
			break;
		case DECEMBER:
			month = 12;
			break;
		default:
			System.out.println("Invalid input");
		}
		return budgetDao.filterByMonthAndYear(filterByMonthAndYearRequestDTO.getId(), month,
				filterByMonthAndYearRequestDTO.getYear());
	}

	public double getTotalIncome(FilterByMonthAndYearRequestDTO filterByMonthAndYearRequestDTO) {
		int month = 0;
		switch (filterByMonthAndYearRequestDTO.getMonth()) {
		case JANUARY:
			month = 1;
			break;
		case FEBRUARY:
			month = 2;
			break;
		case MARCH:
			month = 3;
			break;
		case APRIL:
			month = 4;
			break;
		case MAY:
			month = 5;
			break;
		case JUNE:
			month = 6;
			break;
		case JULY:
			month = 7;
			break;
		case AUGUST:
			month = 8;
			break;
		case SEPTEMBER:
			month = 9;
			break;
		case OCTOBER:
			month = 10;
			break;
		case NOVEMBER:
			month = 11;
			break;
		case DECEMBER:
			month = 12;
			break;
		default:
			System.out.println("Invalid input");
		}
		return budgetDao.getTotalValue(filterByMonthAndYearRequestDTO.getId(), month,
				filterByMonthAndYearRequestDTO.getYear(), Category.INCOME.name());
	}

	public double getTotalExpense(FilterByMonthAndYearRequestDTO filterByMonthAndYearRequestDTO) {
		int month = 0;
		switch (filterByMonthAndYearRequestDTO.getMonth()) {
		case JANUARY:
			month = 1;
			break;
		case FEBRUARY:
			month = 2;
			break;
		case MARCH:
			month = 3;
			break;
		case APRIL:
			month = 4;
			break;
		case MAY:
			month = 5;
			break;
		case JUNE:
			month = 6;
			break;
		case JULY:
			month = 7;
			break;
		case AUGUST:
			month = 8;
			break;
		case SEPTEMBER:
			month = 9;
			break;
		case OCTOBER:
			month = 10;
			break;
		case NOVEMBER:
			month = 11;
			break;
		case DECEMBER:
			month = 12;
			break;
		default:
			System.out.println("Invalid input");
		}
		return budgetDao.getTotalValue(filterByMonthAndYearRequestDTO.getId(), month,
				filterByMonthAndYearRequestDTO.getYear(), Category.EXPENSE.name());
	}

	public Budget addBudget(AddBudgetRequestDTO addBudgetRequestDTO) {
		Profile profile = profileDao.findById(addBudgetRequestDTO.getProfileId());
		Budget budget = new Budget();
		budget.setAmount(addBudgetRequestDTO.getAmount());
		budget.setCategory(addBudgetRequestDTO.getCategory());
		budget.setDate(addBudgetRequestDTO.getDate());
		budget.setDescription(addBudgetRequestDTO.getDescription());
		budget.setExpenseType(addBudgetRequestDTO.getExpenseType());
		budget.setProfile(profile);
		return budgetDao.save(budget);
	}

	public List<MonthlyExpenseAnalysisResponseDTOImpl> getMonthlyExpenseAnalysis(int profileId) {
		List<MonthlyExpenseAnalysisResponseDTO> list = budgetDao.getMonthlyExpenseAnalysis(profileId,
				Category.EXPENSE.name());
		List<MonthlyExpenseAnalysisResponseDTOImpl> expenses = new ArrayList<>();
		for (MonthlyExpenseAnalysisResponseDTO monthlyExpenseAnalysisResponseDTO : list) {
			expenses.add(new MonthlyExpenseAnalysisResponseDTOImpl(monthlyExpenseAnalysisResponseDTO.getAmount(),
					monthlyExpenseAnalysisResponseDTO.getExpenseType().replace('_', ' ')));
		}
		return expenses;
	}

	public List<MonthlyExpenseAnalysisResponseDTOImpl> getMonthlyExpenseAnalysisByMonthAndYear(int id, Month month,
			int year) {
		int monthValue = 0;
		switch (month) {
		case JANUARY:
			monthValue = 1;
			break;
		case FEBRUARY:
			monthValue = 2;
			break;
		case MARCH:
			monthValue = 3;
			break;
		case APRIL:
			monthValue = 4;
			break;
		case MAY:
			monthValue = 5;
			break;
		case JUNE:
			monthValue = 6;
			break;
		case JULY:
			monthValue = 7;
			break;
		case AUGUST:
			monthValue = 8;
			break;
		case SEPTEMBER:
			monthValue = 9;
			break;
		case OCTOBER:
			monthValue = 10;
			break;
		case NOVEMBER:
			monthValue = 11;
			break;
		case DECEMBER:
			monthValue = 12;
			break;
		default:
			System.out.println("Invalid input");
		}
		List<MonthlyExpenseAnalysisResponseDTO> list = budgetDao.getMonthlyExpenseAnalysis(id, Category.EXPENSE.name(),
				monthValue, year);
		List<MonthlyExpenseAnalysisResponseDTOImpl> expenses = new ArrayList<>();
		for (MonthlyExpenseAnalysisResponseDTO monthlyExpenseAnalysisResponseDTO : list) {
			expenses.add(new MonthlyExpenseAnalysisResponseDTOImpl(monthlyExpenseAnalysisResponseDTO.getAmount(),
					monthlyExpenseAnalysisResponseDTO.getExpenseType().replace('_', ' ')));
		}
		return expenses;
	}

	public List<MonthlyComparisonResponseDTOClass> getMonthlyComparison(int profileId) {
		List<MonthlyComparisonResponseDTO> list = budgetDao.getMonthlyComparison(profileId);
		List<MonthlyComparisonResponseDTOClass> comparisons = new ArrayList<>();
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		int currentMonthNumber = LocalDate.now().getMonthValue();
		String[] validMonths = Arrays.copyOfRange(months, 0, currentMonthNumber - 1);
		for (int i = 0; i < validMonths.length; i++) {
			MonthlyComparisonResponseDTOClass monthlyComparisonResponseDTOClass = new MonthlyComparisonResponseDTOClass();
			monthlyComparisonResponseDTOClass.setMonth(validMonths[i]);
			double income = 0;
			double expense = 0;
			for (MonthlyComparisonResponseDTO monthlyComparisonResponseDTO : list) {
				if (monthlyComparisonResponseDTO.getMonthNumber() == (i + 1)) {
					if (monthlyComparisonResponseDTO.getCategory().equals(Category.INCOME)) {
						income = monthlyComparisonResponseDTO.getamount();
					} else {
						expense = monthlyComparisonResponseDTO.getamount();
					}
				}
			}
			List<CategoryAmountDTO> categoryAmountDTOs = new ArrayList<>();
			categoryAmountDTOs.add(new CategoryAmountDTO("Income", income));
			categoryAmountDTOs.add(new CategoryAmountDTO("expense", expense));
			categoryAmountDTOs.add(new CategoryAmountDTO("Savings", income - expense >= 0 ? income - expense : 0));
			monthlyComparisonResponseDTOClass.setList(categoryAmountDTOs);
			comparisons.add(monthlyComparisonResponseDTOClass);
		}
		return comparisons;
	}

	public List<MonthlyComparisonResponseDTOClass> getMonthlyComparisonByYear(int id, Month month, int year) {
		List<MonthlyComparisonResponseDTO> list = budgetDao.getMonthlyComparisonByYear(id, year);
		List<MonthlyComparisonResponseDTOClass> comparisons = new ArrayList<>();
		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		String[] validMonths;
		if (year == LocalDate.now().getYear()) {
			String monthName = month.name().substring(0, 1).toUpperCase() + month.name().substring(1).toLowerCase();
			int currentMonthNumber = Arrays.asList(months).indexOf(monthName);
			validMonths = Arrays.copyOfRange(months, 0, currentMonthNumber);
		} else {
			validMonths = months;
		}
		for (int i = 0; i < validMonths.length; i++) {
			MonthlyComparisonResponseDTOClass monthlyComparisonResponseDTOClass = new MonthlyComparisonResponseDTOClass();
			monthlyComparisonResponseDTOClass.setMonth(validMonths[i]);
			double income = 0;
			double expense = 0;
			for (MonthlyComparisonResponseDTO monthlyComparisonResponseDTO : list) {
				if (monthlyComparisonResponseDTO.getMonthNumber() == (i + 1)) {
					if (monthlyComparisonResponseDTO.getCategory().equals(Category.INCOME)) {
						income = monthlyComparisonResponseDTO.getamount();
					} else {
						expense = monthlyComparisonResponseDTO.getamount();
					}
				}
			}
			List<CategoryAmountDTO> categoryAmountDTOs = new ArrayList<>();
			categoryAmountDTOs.add(new CategoryAmountDTO("Income", income));
			categoryAmountDTOs.add(new CategoryAmountDTO("expense", expense));
			categoryAmountDTOs.add(new CategoryAmountDTO("Savings", income - expense >= 0 ? income - expense : 0));
			monthlyComparisonResponseDTOClass.setList(categoryAmountDTOs);
			comparisons.add(monthlyComparisonResponseDTOClass);
		}
		return comparisons;
	}

	public List<TransactionViewResponseDTO> getRecentTransactions(int profileId) {
		List<Budget> list = budgetDao.getRecentTransactions(profileId);
		List<TransactionViewResponseDTO> recentTransactions = new ArrayList<>();
		for (Budget budget : list) {
			TransactionViewResponseDTO transactionViewResponseDTO = new TransactionViewResponseDTO();
			transactionViewResponseDTO.setDate(budget.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			transactionViewResponseDTO.setCategory(budget.getCategory().name().substring(0, 1).toUpperCase()
					+ budget.getCategory().name().substring(1).toLowerCase());
			transactionViewResponseDTO.setAmount(budget.getAmount());
			recentTransactions.add(transactionViewResponseDTO);
		}
		return recentTransactions;
	}

	public List<TransactionViewResponseDTO> getRecentTransactionsByMonthAndYear(int id, Month month, int year) {
		int monthValue = 0;
		switch (month) {
		case JANUARY:
			monthValue = 1;
			break;
		case FEBRUARY:
			monthValue = 2;
			break;
		case MARCH:
			monthValue = 3;
			break;
		case APRIL:
			monthValue = 4;
			break;
		case MAY:
			monthValue = 5;
			break;
		case JUNE:
			monthValue = 6;
			break;
		case JULY:
			monthValue = 7;
			break;
		case AUGUST:
			monthValue = 8;
			break;
		case SEPTEMBER:
			monthValue = 9;
			break;
		case OCTOBER:
			monthValue = 10;
			break;
		case NOVEMBER:
			monthValue = 11;
			break;
		case DECEMBER:
			monthValue = 12;
			break;
		default:
			System.out.println("Invalid input");
		}
		List<Budget> list = budgetDao.getRecentTransactionsByMonthAndYear(id, monthValue, year);
		List<TransactionViewResponseDTO> recentTransactions = new ArrayList<>();
		for (Budget budget : list) {
			TransactionViewResponseDTO transactionViewResponseDTO = new TransactionViewResponseDTO();
			transactionViewResponseDTO.setDate(budget.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			transactionViewResponseDTO.setCategory(budget.getCategory().name().substring(0, 1).toUpperCase()
					+ budget.getCategory().name().substring(1).toLowerCase());
			transactionViewResponseDTO.setAmount(budget.getAmount());
			recentTransactions.add(transactionViewResponseDTO);
		}
		return recentTransactions;
	}

	public Page<Budget> getTransactions(int id, Month month, int year, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		int monthValue = 0;
		switch (month) {
		case JANUARY:
			monthValue = 1;
			break;
		case FEBRUARY:
			monthValue = 2;
			break;
		case MARCH:
			monthValue = 3;
			break;
		case APRIL:
			monthValue = 4;
			break;
		case MAY:
			monthValue = 5;
			break;
		case JUNE:
			monthValue = 6;
			break;
		case JULY:
			monthValue = 7;
			break;
		case AUGUST:
			monthValue = 8;
			break;
		case SEPTEMBER:
			monthValue = 9;
			break;
		case OCTOBER:
			monthValue = 10;
			break;
		case NOVEMBER:
			monthValue = 11;
			break;
		case DECEMBER:
			monthValue = 12;
			break;
		default:
			System.out.println("Invalid input");
		}
		Page<Budget> transactions = budgetDao.getTransactions(id, monthValue, year, pageable);
		return transactions;
	}

	public ByteArrayInputStream downloadAllTransactions(int id, Month month, int year) {
		int monthValue = 0;
		switch (month) {
		case JANUARY:
			monthValue = 1;
			break;
		case FEBRUARY:
			monthValue = 2;
			break;
		case MARCH:
			monthValue = 3;
			break;
		case APRIL:
			monthValue = 4;
			break;
		case MAY:
			monthValue = 5;
			break;
		case JUNE:
			monthValue = 6;
			break;
		case JULY:
			monthValue = 7;
			break;
		case AUGUST:
			monthValue = 8;
			break;
		case SEPTEMBER:
			monthValue = 9;
			break;
		case OCTOBER:
			monthValue = 10;
			break;
		case NOVEMBER:
			monthValue = 11;
			break;
		case DECEMBER:
			monthValue = 12;
			break;
		default:
			System.out.println("Invalid input");
		}
		List<Budget> budgets = budgetDao.getAllTransactionByMonthAndYear(id, monthValue, year);
		List<TransactionViewResponseDTO> transactions = new ArrayList<>();
		for (Budget budget : budgets) {
			TransactionViewResponseDTO transactionViewResponseDTO = new TransactionViewResponseDTO();
			transactionViewResponseDTO.setAmount(budget.getAmount());
			String category = budget.getCategory().name().substring(0, 1).toUpperCase()
					+ budget.getCategory().name().substring(1).toLowerCase();
			transactionViewResponseDTO.setCategory(category);
			String expenseType = budget.getExpenseType() != null
					? budget.getExpenseType().getFormattedName().substring(0, 1).toUpperCase()
							+ budget.getExpenseType().getFormattedName().substring(1).toLowerCase()
					: "Not applicable";
			transactionViewResponseDTO.setExpenseType(expenseType);
			transactionViewResponseDTO.setDate(budget.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			transactionViewResponseDTO.setDescription(budget.getDescription());
			transactions.add(transactionViewResponseDTO);
		}
		try {
			Workbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			Sheet sheet = workbook.createSheet("Sheet 1");
			Font mainHeadingFont = workbook.createFont();
			mainHeadingFont.setBold(true);
			mainHeadingFont.setFontHeightInPoints((short) 18);
			CellStyle mainHeadingStyle = workbook.createCellStyle();
			mainHeadingStyle.setFont(mainHeadingFont);
			mainHeadingStyle.setAlignment(HorizontalAlignment.CENTER);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
			Row mainHeadingRow = sheet.createRow(0);
			Cell mainHeading = mainHeadingRow.createCell(0);
			mainHeading.setCellValue("Transactions for " + month + " " + year);
			mainHeading.setCellStyle(mainHeadingStyle);
			Font headingFont = workbook.createFont();
			headingFont.setBold(true);
			headingFont.setFontHeightInPoints((short) 16);
			CellStyle headingStyle = workbook.createCellStyle();
			headingStyle.setFont(headingFont);
			Row headingRow = sheet.createRow(1);
			Cell serialNoheading = headingRow.createCell(0);
			serialNoheading.setCellValue("Sl No.");
			serialNoheading.setCellStyle(headingStyle);
			Cell dateHeading = headingRow.createCell(1);
			dateHeading.setCellValue("Date");
			dateHeading.setCellStyle(headingStyle);
			Cell categoryHeading = headingRow.createCell(2);
			categoryHeading.setCellValue("Category");
			categoryHeading.setCellStyle(headingStyle);
			Cell expenseTypeHeading = headingRow.createCell(3);
			expenseTypeHeading.setCellValue("Expense Type");
			expenseTypeHeading.setCellStyle(headingStyle);
			Cell amountHeading = headingRow.createCell(4);
			amountHeading.setCellValue("Amount (in Indian Rupees)");
			amountHeading.setCellStyle(headingStyle);
			Cell descriptionHeading = headingRow.createCell(5);
			descriptionHeading.setCellValue("Description");
			descriptionHeading.setCellStyle(headingStyle);
			int rowCount = 2;
			Font contentFont = workbook.createFont();
			contentFont.setFontHeightInPoints((short) 14);
			CellStyle contentStyle = workbook.createCellStyle();
			contentStyle.setFont(contentFont);
			for (TransactionViewResponseDTO dto : transactions) {
				Row row = sheet.createRow(rowCount);
				Cell serialNo = row.createCell(0);
				serialNo.setCellValue(rowCount - 1);
				serialNo.setCellStyle(contentStyle);
				Cell date = row.createCell(1);
				date.setCellValue(dto.getDate());
				date.setCellStyle(contentStyle);
				Cell category = row.createCell(2);
				category.setCellValue(dto.getCategory());
				category.setCellStyle(contentStyle);
				Cell expenseType = row.createCell(3);
				expenseType.setCellValue(dto.getExpenseType());
				expenseType.setCellStyle(contentStyle);
				Cell amount = row.createCell(4);
				amount.setCellValue("Rs "+dto.getAmount());
				amount.setCellStyle(contentStyle);
				Cell description = row.createCell(5);
				description.setCellValue(dto.getDescription());
				description.setCellStyle(contentStyle);
				rowCount++;
			}
			workbook.write(byteArrayOutputStream);
			workbook.close();
			return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
