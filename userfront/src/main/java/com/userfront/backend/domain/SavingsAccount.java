package com.userfront.backend.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SavingsAccount implements Serializable {

	/** The serial versionUID for Serializable classes */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int accountNumber;
	private BigDecimal accountBalance;

	@OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<SavingsTransaction> savingsListTransactions = new HashSet<>();

	public SavingsAccount() {}

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
	    if (accountBalance == null){
	        accountBalance = new BigDecimal(0.0);
        }
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

    public Set<SavingsTransaction> getSavingsListTransactions() {
        return savingsListTransactions;
    }

    public void setSavingsListTransactions(Set<SavingsTransaction> savingsListTransactions) {
        this.savingsListTransactions = savingsListTransactions;
    }
}
