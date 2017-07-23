package com.userfront.enums;

public enum TransactionsEnum {
	
	/* View Name Mapping */
	REDIRECT_USER_FRONT_VIEW_NAME("redirect:/userFront"),
	DEPOSIT_VIEW_NAME("transactions/deposit"),
	WITHDRAW_VIEW_NAME("transactions/withdraw"),
	
	/* Model Attributes */
	MODEL_ACCOUNT_TYPE("accountType"),
	MODEL_AMOUNT("amount"),
	MODEL_PRIMARY_TRANSACTION_LIST("primaryTransactionList"),
    MODEL_SAVINGS_TRANSACTION_LIST("savingsTransactionList"),
	ACCOUNT_KEY("Account"),
	
	/* Account Type */
	PRIMARY_ACCOUNT("Primary"),
	SAVINGS_ACCOUNT("Savings"),
	
	/* Messages from .properties file */
	DEPOSIT_PRIMARY_ACCOUNT_TRANSACTION("deposit.primary.account.message.text"),
    DEPOSIT_SAVINGS_ACCOUNT_TRANSACTION("deposit.savings.account.message.text"),
    WITHDRAW_PRIMARY_ACCOUNT_TRANSACTION("withdraw.primary.account.message.text"),
    WITHDRAW_SAVINGS_ACCOUNT_TRANSACTION("withdraw.savings.account.message.text");

	
    private final String message;
	
	TransactionsEnum(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
