package com.userfront.backend.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class PrimaryTransaction extends Transactions implements Serializable {

	/** The serial versionUID for Serializable classes */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "primary_account_id")
	@JsonBackReference
	private PrimaryAccount primaryAccount;

	public PrimaryTransaction() {}

	public PrimaryTransaction(Date date, String description, String type, String status, Double amount, BigDecimal availableBalance, PrimaryAccount primaryAccount) {
		super(date, description, type, status, amount, availableBalance);
		this.primaryAccount = primaryAccount;
	}

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PrimaryAccount getPrimaryAccount() {
		return primaryAccount;
	}

	public void setPrimaryAccount(PrimaryAccount primaryAccount) {
		this.primaryAccount = primaryAccount;
	}
}
