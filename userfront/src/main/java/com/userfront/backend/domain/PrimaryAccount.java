package com.userfront.backend.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PrimaryAccount implements Serializable {

	/** The serial versionUID for Serializable classes */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int accountNumber;
	private BigDecimal accountBalance;

	@OneToMany(mappedBy = "primaryAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<PrimaryTransaction> primaryListTransactions = new HashSet<>();

	public PrimaryAccount() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

    public Set<PrimaryTransaction> getPrimaryListTransactions() {
        return primaryListTransactions;
    }

    public void setPrimaryListTransactions(Set<PrimaryTransaction> primaryListTransactions) {
        this.primaryListTransactions = primaryListTransactions;
    }
}
