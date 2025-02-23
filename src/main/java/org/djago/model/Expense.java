package org.djago.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "expenses")
public class Expense implements Serializable {

	private static final long serialVersionUID = 102L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "expense_dt")
	private LocalDate expenseDate;

	@Column(name = "amount")
	private Double amount;

	@NotNull
	@Column(name = "explanation")
	private String explanation;

	@Column(name = "curr_user")
	private String currentUser;

	public Expense() {
		this.expenseDate = LocalDate.now();
	}

//	public Expense(ExpenseUIModel expenseModel) {
////		this.id = expenseModel.getId();
//		this.expenseDate = expenseModel.getExpenseDate();
//		this.amount = expenseModel.getAmount();
//		this.explanation = expenseModel.getExplanation();
//		this.currentUser = expenseModel.getCurrentUser();
//	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(LocalDate expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

}