package com.userfront.enums;

public enum TransferEnum {
	
	/* View Name Mapping */
	REDIRECT_USER_FRONT_VIEW_NAME("redirect:/userFront"),
	REDIRECT_RECIPIENT_VIEW_NAME("redirect:/transfer/recipient"),
	BETWEEN_ACCOUNTS_VIEW_NAME("transfer/betweenAccounts"),
	RECIPIENT_VIEW_NAME("transfer/recipient"),
	TO_SOMEONE_ELSE_VIEW_NAME("transfer/toSomeoneElse"),
	
	TRANSFER_KEY("Transfer"),
	RECIPIENT_MESSAGE_KEY("recipientCreated"),
	ERROR_MESSAGE_KEY("message"),
	
	/* Model Attributes */
	MODEL_RECIPIENT_LIST("recipientList"),
	MODEL_RECIPIENT("recipient"),
	MODEL_TRANSFER_FROM("transferFrom"),
	MODEL_TRANSFER_TO("transferTo"),
	MODEL_ACCOUNT_TYPE("accountType"),
	MODEL_AMOUNT("amount"),
	MODEL_PRIMARY_TRANSACTION_LIST("primaryTransactionList"),
    MODEL_SAVINGS_TRANSACTION_LIST("savingsTransactionList"),
    
    
	/* Messages from .properties file */
    TRANSFER_TO_SOMEONE_ELSE_ACCOUNT_TRANSACTION("transfer.to.someone.else.account.description"),
	TRANSFER_PRIMARY_TO_SAVINGS_ACCOUNT_TRANSACTION("transfer.from.primary.to.savings.account.description");

	
    private final String message;
	
	TransferEnum(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
