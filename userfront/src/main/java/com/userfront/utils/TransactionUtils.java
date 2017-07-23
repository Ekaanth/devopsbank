package com.userfront.utils;

import java.util.Date;

import com.userfront.backend.domain.PrimaryAccount;
import com.userfront.backend.domain.PrimaryTransaction;
import com.userfront.backend.domain.SavingsAccount;
import com.userfront.backend.domain.SavingsTransaction;

/**
 * Created by root on 01/07/17.
 */
public class TransactionUtils {
	
    /**
     * Non instantiable
     */
    private TransactionUtils(){
        throw new AssertionError("Non instantiable");
    }
 
    
    /**
     * Creates a Savings Transaction object to be tested
     * 
     * @param savingsAccount - the savings account parameter
     * @param amount - the amount parameter to be deposited
     * @param flag - Used to verify whether is a deposit or not 
     * @return
     */
    public static SavingsTransaction createSavingsTransaction(SavingsAccount savingsAccount, double amount, String description, String type, String status) {
    	
    	Date date = new Date();
    	SavingsTransaction savingsTransaction =
    			new SavingsTransaction(date, description, "Account",
    					status, amount, savingsAccount.getAccountBalance(), savingsAccount);
    	
    	
    	return savingsTransaction;
    }

    /**
     * Creates a Primary Transaction object to be tested
     * 
     * @param primaryAccount - the primary account parameter
     * @param amount - the amount parameter to be deposited
     * @param flag - Used to verify whether is a deposit or not
     * @return
     */
    public static PrimaryTransaction createPrimaryTransaction(PrimaryAccount primaryAccount, double amount, String description, String type, String status) {

        Date date = new Date();
        PrimaryTransaction primaryTransaction =
                new PrimaryTransaction(date, description, type,
                        status, amount, primaryAccount.getAccountBalance(), primaryAccount);


        return primaryTransaction;
    }
}
