package org.djago.service;

import java.time.LocalDate;
import java.util.List;

import org.djago.model.Expense;

public interface ExpenseService {

	List<Expense> findAll();

	List<Expense> findExpensesByDate(LocalDate date);

	List<Expense> findExpensesByDateRange(LocalDate startDate, LocalDate endDate);
	
	List<Expense> findExpensesByOrderID(Long id);

	void save(Expense expense);

	void save(List<Expense> expenses);

}