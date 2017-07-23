package com.userfront.utils;

import java.math.BigDecimal;

import com.userfront.backend.domain.PrimaryAccount;
import com.userfront.backend.domain.SavingsAccount;

public class AccountUtils {
	
    // Static field account number from here
    private static int nextAccountNumber = 1622;
    

    /**
     * Method that increases account number value to be saved in user object
     *
     * @return the account number increased by 1
     */
    public static int accountGen(){
    	
    	int fourLastDigits = (int)(Math.random() * 10) + 3145;
    	
    	String accountNumberGenerated = String.valueOf(nextAccountNumber) + String.valueOf(fourLastDigits);
    	
        return Integer.parseInt(accountNumberGenerated);
    }
    
    public static PrimaryAccount generatePrimaryAccount(){

        PrimaryAccount primaryAccount = new PrimaryAccount();
        
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        
        primaryAccount.setAccountNumber(AccountUtils.accountGen());
        
        return primaryAccount;
    }
    
    public static SavingsAccount generateSavingsAccount(){

        SavingsAccount savingsAccount = new SavingsAccount();
        
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        
        savingsAccount.setAccountNumber(AccountUtils.accountGen());
        
        return savingsAccount;
        
    }
    
}
