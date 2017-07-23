package com.userfront.backend.service;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userfront.backend.domain.PrimaryAccount;
import com.userfront.backend.domain.PrimaryTransaction;
import com.userfront.backend.domain.SavingsAccount;
import com.userfront.backend.domain.SavingsTransaction;
import com.userfront.backend.domain.User;
import com.userfront.backend.repositories.PrimaryAccountRepository;
import com.userfront.backend.repositories.SavingsAccountRepository;
import com.userfront.backend.repositories.UserRepository;
import com.userfront.enums.StatusEnum;
import com.userfront.enums.TransactionsEnum;
import com.userfront.utils.AccountUtils;
import com.userfront.utils.TransactionUtils;

/**
 * Created by root on 01/07/17.
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private PrimaryAccountRepository primaryAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;
    
	@Autowired
	private I18NService i18nService;

    /**
     * Creates a new Primary Account object to be saved in User object as
     * the id column for the user entity
     *
     * @return the Primary Account object with the id filled
     */
    public PrimaryAccount createPrimaryAccount(){

        PrimaryAccount primaryAccount = new PrimaryAccount();
        
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        
        primaryAccount.setAccountNumber(AccountUtils.accountGen());

        primaryAccountRepository.save(primaryAccount);

        return primaryAccountRepository.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    /**
     * Creates a new Savings Account object to be saved in User object as
     * the id column for the user entity
     *
     * @return the Savings Account object with the id filled
     */
    public SavingsAccount createSavingsAccount(){

        SavingsAccount savingsAccount = new SavingsAccount();
        
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        
        savingsAccount.setAccountNumber(AccountUtils.accountGen());

        savingsAccountRepository.save(savingsAccount);

        return savingsAccountRepository.findByAccountNumber(savingsAccount.getAccountNumber());
    }

    /**
     * Method that deposit the amount in the database
     * 
     * @param accountType - The account type chosen by the user
     * @param amount - The amount informed by user to be deposited
     * @param principal - The java security object that will be used to retrieve the user's credentials
     */
    public void deposit(String accountType, double amount, Principal principal) {
    	/** Description String variable to receive a message from messages.properties */
    	String description = "";
    	
        User user = userRepository.findByUsername(principal.getName());
        
        /**
         * Check the type of account the user request to deposit, 
         * whether is in his primary account or in his savings account
         */
        if (accountType.equalsIgnoreCase("Primary")){

        	/** Gets the account from the user object */
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            
            /** Update the amount with new more values */
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            
            /** Saves the new amount in database */
            primaryAccountRepository.save(primaryAccount);
            
            /** Gets the string message from messages.properties file and put it in the string variable*/
            description = i18nService.getMessage(TransactionsEnum.DEPOSIT_PRIMARY_ACCOUNT_TRANSACTION.getMessage());

            /** Creates a transaction to register the deposit here */
            PrimaryTransaction primaryTransaction = TransactionUtils.createPrimaryTransaction(primaryAccount, amount, description, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
           
            /** Persist the amount through deposit action in the primary transactions database entity */
            transactionService.savePrimaryDepositTransaction(primaryTransaction);

        } else if (accountType.equalsIgnoreCase("Savings")){

        	/** Gets the account from the user object */
            SavingsAccount savingsAccount = user.getSavingsAccount();

            /** Update the amount with new more values */
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            
            /** Saves the new amount in database */
            savingsAccountRepository.save(savingsAccount);
            
            /** Gets the string message from messages.properties file and put it in the string variable*/
            description = i18nService.getMessage(TransactionsEnum.DEPOSIT_SAVINGS_ACCOUNT_TRANSACTION.getMessage());
            
            /** Creates a transaction to register the deposit here */
            SavingsTransaction savingsTransaction = TransactionUtils.createSavingsTransaction(savingsAccount, amount, description, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
            
            /** Persist the amount through deposit action in the savings transactions database entity */
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }
    

    /**
     * Method that makes the withdraw the amount in the database
     * 
     * @param accountType - The account type chosen by the user
     * @param amount - The amount informed by user for withdraw
     * @param principal - The java security object that will be used to retrieve the user's credentials
     */
    public boolean withdraw(String accountType, double amount, Principal principal) {
    	/** Description String variable to receive a message from messages.properties */
    	String description = "";
    	boolean hasNoValue = true;
    	
        User user = userRepository.findByUsername(principal.getName());
        
        /**
         * Check the type of account the user request to withdraw, 
         * whether is in his primary account or in his savings account
         */
        if (accountType.equalsIgnoreCase("Primary")){

        	/** Gets the account from the user object */
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            
            if(primaryAccount.getAccountBalance().intValue() > 0){
            	
            	/** Update the amount with new more values */
            	primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            	
            	/** Saves the new amount with the values after withdraw */
            	primaryAccountRepository.save(primaryAccount);
            	
            	/** Gets the string message from messages.properties file and put it in the string variable*/
            	description = i18nService.getMessage(TransactionsEnum.WITHDRAW_PRIMARY_ACCOUNT_TRANSACTION.getMessage());
            	
            	/** Creates a transaction to register the deposit here */
            	PrimaryTransaction primaryTransaction = TransactionUtils.createPrimaryTransaction(primaryAccount, amount, description, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
            	
            	/** Persist the amount through deposit action in the primary transactions database entity */
            	transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
            	
            	hasNoValue = false;

            }

        } else if (accountType.equalsIgnoreCase("Savings")){

        	/** Gets the account from the user object */
            SavingsAccount savingsAccount = user.getSavingsAccount();
        
            if(savingsAccount.getAccountBalance().intValue() > 0){
            	
            	/** Update the amount with the values after withdraw */
            	savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            	
            	/** Saves the new amount in database */
            	savingsAccountRepository.save(savingsAccount);
            	
            	/** Gets the string message from messages.properties file and put it in the string variable*/
            	description = i18nService.getMessage(TransactionsEnum.WITHDRAW_SAVINGS_ACCOUNT_TRANSACTION.getMessage());
            	
            	/** Creates a transaction to register the deposit here */
            	SavingsTransaction savingsTransaction = TransactionUtils.createSavingsTransaction(savingsAccount, amount, description, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
            	
            	/** Persist the amount through deposit action in the savings transactions database entity */
            	transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
           
            	hasNoValue = false;
            	
            }
        }
    
        return hasNoValue;
        
    }
}
