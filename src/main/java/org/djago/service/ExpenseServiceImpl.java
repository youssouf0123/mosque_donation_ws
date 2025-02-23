package org.djago.service;

import java.time.LocalDate;
import java.util.List;

import org.djago.model.Expense;
import org.djago.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("expenseService")
@Transactional(propagation = Propagation.REQUIRED)
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;

	public List<Expense> findAll() {
		return expenseRepository.findAll();
	}

	@Override
	public List<Expense> findExpensesByDate(LocalDate date) {
		return expenseRepository.findExpensesByDate(date);
	}

	@Override
	public List<Expense> findExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
		return expenseRepository.findExpensesByDateRange(startDate, endDate);
	}

	@Override
	public void save(Expense expense) {
		expenseRepository.save(expense);
	}
	
	@Override
	public void save(List<Expense> expenses) {
		expenseRepository.saveAll(expenses);
	}

	@Override
	public List<Expense> findExpensesByOrderID(Long id) {
		
		return expenseRepository.findExpensesByOrderID(id);
	}
}