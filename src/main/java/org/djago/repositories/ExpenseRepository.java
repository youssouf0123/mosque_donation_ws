package org.djago.repositories;

import java.time.LocalDate;
import java.util.List;

import org.djago.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

	@Query("SELECT e FROM Expense e WHERE e.expenseDate = :date")
	List<Expense> findExpensesByDate(@Param("date") LocalDate date);

	@Query("SELECT e FROM Expense e WHERE e.expenseDate between :startDate and :endDate")
	List<Expense> findExpensesByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query("SELECT e FROM Expense e WHERE e.id = :id")
	List<Expense> findExpensesByOrderID(@Param("id") Long id);
}