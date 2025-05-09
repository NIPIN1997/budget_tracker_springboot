package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.AddBudgetRequestDTO;
import com.example.demo.dto.FilterByMonthAndYearRequestDTO;
import com.example.demo.dto.MonthlyComparisonResponseDTOClass;
import com.example.demo.dto.MonthlyExpenseAnalysisResponseDTOImpl;
import com.example.demo.dto.TransactionViewResponseDTO;
import com.example.demo.entity.Budget;
import com.example.demo.entity.Profile;
import com.example.demo.enums.Category;
import com.example.demo.enums.ExpenseTypes;
import com.example.demo.service.BudgetService;
import com.example.demo.service.ProfileService;

@Controller
public class MainController {

	@Autowired
	private ProfileService profileService;
	@Autowired
	private BudgetService budgetService;

	@GetMapping(path = "/")
	public String welcome(Model model) {
		List<Profile> profiles = profileService.retrieveAllProfiles();
		model.addAttribute("profiles", profiles);
		return "home";
	}

	@GetMapping(path = "/add-profile")
	public String displayAddProfile(Model model) {
		model.addAttribute("profile", new Profile());
		return "add_profile";
	}

	@PostMapping(path = "/add-profile")
	public String addNewProfile(@ModelAttribute Profile profile, Model model) {
		profileService.addNewProfile(profile);
		return "redirect:/";
	}

	@GetMapping(path = "/get-budget/{profileId}")
	public String getBudget(@PathVariable int profileId, Model model) {
		Profile profile = profileService.getProfileById(profileId);
		double totalIncome = budgetService.getTotalIncome(profileId);
		double totalExpense = budgetService.getTotalExpense(profileId);
		double remainingBudget = totalIncome - totalExpense >= 0 ? totalIncome - totalExpense : 0;
		Month month = LocalDate.now().getMonth();
		int year = LocalDate.now().getYear();
		List<MonthlyExpenseAnalysisResponseDTOImpl> monthlyExpenseAnalysisResponseDTO = budgetService
				.getMonthlyExpenseAnalysis(profileId);
		List<MonthlyComparisonResponseDTOClass> comparisonResponseDTO = budgetService.getMonthlyComparison(profileId);
		List<TransactionViewResponseDTO> recentTransactions = budgetService.getRecentTransactions(profileId);
		Map<String, Object> models = new HashMap<>();
		models.put("profile", profile);
		models.put("totalIncome", totalIncome);
		models.put("totalExpense", totalExpense);
		models.put("remainingBudget", remainingBudget);
		models.put("month", month);
		models.put("year", year);
		models.put("expenseAnalysis", monthlyExpenseAnalysisResponseDTO);
		models.put("monthlyComparison", comparisonResponseDTO);
		models.put("recentTransactions", recentTransactions);
		model.addAllAttributes(models);
		return "dashboard";
	}

	@GetMapping(path = "/edit-profile/{profileId}")
	public String displayEditProfile(@PathVariable int profileId, Model model) {
		Profile profile = profileService.getProfileById(profileId);
		model.addAttribute("profile", profile);
		return "edit_profile";
	}

	@PostMapping(path = "/edit-profile")
	public String editProfile(@ModelAttribute Profile profile, Model model) {
		profileService.editProfile(profile);
		return "redirect:/get-budget/" + profile.getId();
	}

	@GetMapping(path = "/delete-profile/{profileId}")
	public String deleteProfile(@PathVariable int profileId, Model model) {
		profileService.deleteProfile(profileId);
		return "redirect:/";
	}

	@PostMapping(path = "/filter-by-month-and-year")
	public String filterByMonthAndYear(@ModelAttribute FilterByMonthAndYearRequestDTO filterByMonthAndYearRequestDTO,
			Model model) {
		Profile profile = profileService.getProfileById(filterByMonthAndYearRequestDTO.getId());
		double totalIncome = budgetService.getTotalIncome(filterByMonthAndYearRequestDTO);
		double totalExpense = budgetService.getTotalExpense(filterByMonthAndYearRequestDTO);
		double remainingBudget = totalIncome - totalExpense >= 0 ? totalIncome - totalExpense : 0;
		Month month = filterByMonthAndYearRequestDTO.getMonth();
		int year = filterByMonthAndYearRequestDTO.getYear();
		List<MonthlyExpenseAnalysisResponseDTOImpl> monthlyExpenseAnalysisResponseDTO = budgetService
				.getMonthlyExpenseAnalysisByMonthAndYear(filterByMonthAndYearRequestDTO.getId(), month, year);
		List<MonthlyComparisonResponseDTOClass> comparisonResponseDTO = budgetService
				.getMonthlyComparisonByYear(filterByMonthAndYearRequestDTO.getId(), month, year);
		List<TransactionViewResponseDTO> recentTransactions = budgetService
				.getRecentTransactionsByMonthAndYear(filterByMonthAndYearRequestDTO.getId(), month, year);
		Map<String, Object> models = new HashMap<>();
		models.put("profile", profile);
		models.put("totalIncome", totalIncome);
		models.put("totalExpense", totalExpense);
		models.put("remainingBudget", remainingBudget);
		models.put("month", month);
		models.put("year", year);
		models.put("expenseAnalysis", monthlyExpenseAnalysisResponseDTO);
		models.put("monthlyComparison", comparisonResponseDTO);
		models.put("recentTransactions", recentTransactions);
		model.addAllAttributes(models);
		return "dashboard";
	}

	@GetMapping(path = "/add-budget/{profileId}")
	public String displayAddBudget(@PathVariable int profileId, Model model) {
		Map<String, Object> models = new HashMap<>();
		models.put("budget", new Budget());
		models.put("profileId", profileId);
		models.put("categories", Category.values());
		models.put("expenseTypes", ExpenseTypes.values());
		model.addAllAttributes(models);
		return "add_budget";
	}

	@PostMapping(path = "/add-budget")
	public String addBudget(@ModelAttribute AddBudgetRequestDTO addBudgetRequestDTO, Model model) {
		Budget budget = budgetService.addBudget(addBudgetRequestDTO);
		return "redirect:/get-budget/" + addBudgetRequestDTO.getProfileId();
	}

	@GetMapping(path = "/view-all-transactions")
	public String viewAllTransactions(@RequestParam("idValue") int id, @RequestParam("monthName") Month month,
			@RequestParam("yearValue") int year, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, Model model) {
		Profile profile = profileService.getProfileById(id);
		Page<Budget> list = budgetService.getTransactions(id, month, year, page, size);
		List<TransactionViewResponseDTO> transactions = new ArrayList<>();
		for (Budget budget : list) {
			TransactionViewResponseDTO transactionViewResponseDTO = new TransactionViewResponseDTO();
			transactionViewResponseDTO.setDate(budget.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			transactionViewResponseDTO.setAmount(budget.getAmount());
			String category = budget.getCategory().name().substring(0, 1).toUpperCase()
					+ budget.getCategory().name().substring(1).toLowerCase();
			String expenseType = budget.getExpenseType() != null
					? budget.getExpenseType().getFormattedName().substring(0, 1).toUpperCase()
							+ budget.getExpenseType().getFormattedName().substring(1).toLowerCase()
					: "Not applicable";
			transactionViewResponseDTO.setCategory(category);
			transactionViewResponseDTO.setDescription(budget.getDescription());
			transactionViewResponseDTO.setExpenseType(expenseType);
			transactions.add(transactionViewResponseDTO);
		}
		Map<String, Object> models = new HashMap<>();
		models.put("profile", profile);
		models.put("transactions", transactions);
		models.put("totalPages", list.getTotalPages());
		models.put("currentPage", page);
		models.put("month", month);
		models.put("year", year);
		model.addAllAttributes(models);
		return "transaction_view";
	}

	@GetMapping(path = "/download-all-transactions")
	public ResponseEntity<InputStreamResource> downloadAllTransactions(@RequestParam("idValue") int id,
			@RequestParam("monthName") Month month, @RequestParam("yearValue") int year) {
		ByteArrayInputStream byteArrayInputStream = budgetService.downloadAllTransactions(id, month, year);
		InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=budget.xlsx");
		return ResponseEntity.ok()
				.headers(headers)
				.contentType(
						MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(inputStreamResource);
	}
}
