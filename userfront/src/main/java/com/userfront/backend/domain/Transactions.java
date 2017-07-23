package com.userfront.backend.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.MappedSuperclass;

// Annotation that tells hibernate to create these fields in database through its subclasses
@MappedSuperclass
public class Transactions {
	

	protected Date date;
	protected String description;
	protected String type;
	protected String status;
	protected Double amount; // Used Double type for displaying only
	protected BigDecimal availableBalance; // Used BigDecimal for arithmetical operations

	public Transactions() {}

	public Transactions(Date date, String description, String type, String status, Double amount, BigDecimal availableBalance) {
		this.date = date;
		this.description = description;
		this.type = type;
		this.status = status;
		this.amount = amount;
		this.availableBalance = availableBalance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
	}
}
