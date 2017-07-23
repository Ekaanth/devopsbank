package com.userfront.enums;

/**
 * Created by root on 10/06/17.
 */
public enum AccountsEnum {

	PRIMARY_ACCOUNT_VIEW_NAME("accounts/primaryAccount"),
	SAVINGS_ACCOUNTS_VIEW_NAME("accounts/savingsAccount"),
	MODEL_PRIMARY_ACCOUNT("primaryAccount"),
	MODEL_SAVINGS_ACCOUNT("savingsAccount");

    private final String account;

    AccountsEnum(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }
}
