package com.userfront.enums;

public enum SelectAccountsEnum {
	
    PRIMARY_ACCOUNT("Primary"),
    SAVINGS_ACCOUNT("Savings");
	
    private final String account;

    SelectAccountsEnum(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

}
