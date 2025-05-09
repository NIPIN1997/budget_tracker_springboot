package com.example.demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.MonthlyComparisonResponseDTO;
import com.example.demo.dto.MonthlyExpenseAnalysisResponseDTO;
import com.example.demo.entity.Budget;

@Repository
public interface BudgetDao extends JpaRepository<Budget, Integer> {

	@Query(nativeQuery = true, value = "select * from budget b where b.profile_id=:profileId and MONTH(b.date)= MONTH(CURRENT_DATE) and YEAR(b.date) = YEAR(CURRENT_DATE)")
	List<Budget> getBudgetsOfAProfile(int profileId);

	@Query(nativeQuery = true, value = "select coalesce(sum(b.amount),0) from budget b where b.profile_id=:profileId and b.category=:category and MONTH(b.date)= MONTH(CURRENT_DATE) and YEAR(b.date) = YEAR(CURRENT_DATE)")
	double getTotalValue(int profileId, String category);

	@Query(nativeQuery = true, value = "select * from budget b where b.profile_id=:id and MONTH(b.date)= :month and YEAR(b.date) = :year")
	List<Budget> filterByMonthAndYear(int id, int month, int year);

	@Query(nativeQuery = true, value = "select coalesce(sum(b.amount),0) from budget b where b.profile_id=:id and b.category=:category and MONTH(b.date)= :month and YEAR(b.date) = :year")
	double getTotalValue(int id, int month, int year, String category);

	@Query(nativeQuery = true, value = "select sum(b.amount) as amount, b.expense_type as expenseType from budget b where b.profile_id=:profileId and b.category=:category and MONTH(b.date)= MONTH(CURRENT_DATE) and YEAR(b.date) = YEAR(CURRENT_DATE) group by b.expense_type")
	List<MonthlyExpenseAnalysisResponseDTO> getMonthlyExpenseAnalysis(int profileId, String category);

	@Query(nativeQuery = true, value = "select sum(b.amount) as amount, b.expense_type as expenseType from budget b where b.profile_id=:id and b.category=:category and MONTH(b.date)= :monthValue and YEAR(b.date) = :year group by b.expense_type")
	List<MonthlyExpenseAnalysisResponseDTO> getMonthlyExpenseAnalysis(int id, String category, int monthValue,
			int year);

	@Query(nativeQuery = true, value = "select sum(b.amount) as amount, b.category as category, Month(b.date) as monthNumber from budget b where b.profile_id=:profileId and YEAR(b.date)=YEAR(CURRENT_DATE) group by monthNumber, category")
	List<MonthlyComparisonResponseDTO> getMonthlyComparison(int profileId);

	@Query(nativeQuery = true, value = "select sum(b.amount) as amount, b.category as category, Month(b.date) as monthNumber from budget b where b.profile_id=:id and YEAR(b.date)=:year group by monthNumber, category")
	List<MonthlyComparisonResponseDTO> getMonthlyComparisonByYear(int id, int year);

	@Query(nativeQuery = true, value = "select * from budget b where b.profile_id=:profileId and MONTH(b.date)= MONTH(CURRENT_DATE) and YEAR(b.date) = YEAR(CURRENT_DATE) order by b.date desc limit 5")
	List<Budget> getRecentTransactions(int profileId);

	@Query(nativeQuery = true, value = "select * from budget b where b.profile_id=:id and MONTH(b.date)= :monthValue and YEAR(b.date) = :year order by b.date desc limit 5")
	List<Budget> getRecentTransactionsByMonthAndYear(int id, int monthValue, int year);

	@Query(nativeQuery = true, value = "select * from budget b where b.profile_id=:id and MONTH(b.date)= :monthValue and YEAR(b.date) = :year order by b.date desc")
	Page<Budget> getTransactions(int id, int monthValue, int year, Pageable pageable);

	@Query(nativeQuery = true, value = "select * from budget b where b.profile_id=:id and MONTH(b.date)= :monthValue and YEAR(b.date) = :year order by b.date desc")
	List<Budget> getAllTransactionByMonthAndYear(int id, int monthValue, int year);

}
