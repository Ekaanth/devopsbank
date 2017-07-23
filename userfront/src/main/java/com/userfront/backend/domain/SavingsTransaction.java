package com.userfront.backend.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class SavingsTransaction extends Transactions implements Serializable {

	/** The serial versionUID for Serializable classes */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "savings_account_id")
	@JsonBackReference
	private SavingsAccount savingsAccount;

	public SavingsTransaction() {}

	public SavingsTransaction(Date date, String description, String type, String status, Double amount, BigDecimal availableBalance, SavingsAccount savingsAccount) {
		super(date, description, type, status, amount, availableBalance);
		this.savingsAccount = savingsAccount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public SavingsAccount getSavingsAccount() {
		return savingsAccount;
	}

	public void setSavingsAccount(SavingsAccount savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

}
