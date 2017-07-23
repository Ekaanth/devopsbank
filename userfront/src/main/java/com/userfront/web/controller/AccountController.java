package com.userfront.web.controller;

import java.security.Principal;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.backend.domain.PrimaryAccount;
import com.userfront.backend.domain.PrimaryTransaction;
import com.userfront.backend.domain.SavingsAccount;
import com.userfront.backend.domain.SavingsTransaction;
import com.userfront.backend.domain.User;
import com.userfront.backend.service.AccountService;
import com.userfront.backend.service.TransactionService;
import com.userfront.backend.service.UserService;
import com.userfront.enums.AccountsEnum;
import com.userfront.enums.TransactionsEnum;

/**
 * Created by root on 02/07/17.
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
    
    public static final String PRIMARY_ACCOUNT_URL_MAPPING = "/primaryAccount";
    public static final String SAVINGS_ACCOUNT_URL_MAPPING = "/savingsAccount";
    public static final String DEPOSIT_URL_MAPPING = "/deposit";
    public static final String WITHDRAW_URL_MAPPING = "/withdraw";

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private TransactionService transactionService;

    /**
     * Method that takes the user request to the primary account detail page
     * 
     * @param model - The Spring web model that contains the values in its fields
     * @param principal - The java security object used to retrieve the user credentials
     * @return The view page name where the user will be redirected to.
     */
    @RequestMapping(PRIMARY_ACCOUNT_URL_MAPPING)
    public String primaryAccount(Model model, Principal principal){
    	LOG.info("inside method primaryAccount");
    	
    	/** Gets the primary transaction list by username using Principal class */
    	Set<PrimaryTransaction> primaryTransactions = transactionService.findPrimaryTransactionList(principal.getName());

    	/** Gets the user by username using Principal class */
        User user = userService.findByUsername(principal.getName());

        /** Gets his primary account linked to the entity*/
        PrimaryAccount primaryAccount = user.getPrimaryAccount();

        /** Insert the primary account in the Spring model object to be used on the HTML page */
        model.addAttribute(AccountsEnum.MODEL_PRIMARY_ACCOUNT.getAccount(), primaryAccount);
        /** Insert the primary transaction list in the Spring model object to be used on the HTML page */
        model.addAttribute(TransactionsEnum.MODEL_PRIMARY_TRANSACTION_LIST.getMessage(), primaryTransactions);

        /** Goes to the primaryAccount HTML page */
        return AccountsEnum.PRIMARY_ACCOUNT_VIEW_NAME.getAccount();
    }

    /**
     * Method that takes the user request to the savings account detail page
     * 
     * @param model - The Spring web model that contains the values in its fields
     * @param principal - The java security object used to retrieve the user credentials
     * @return The view page name where the user will be redirected to.
     */
    @RequestMapping(SAVINGS_ACCOUNT_URL_MAPPING)
    public String savingsAccount(Model model, Principal principal){
    	LOG.info("inside method savingsAccount");
    	
    	/** Gets the primary transaction list by username using Principal class */
    	Set<SavingsTransaction> savingsTransactions = transactionService.findSavingsTransactionList(principal.getName());

    	/** Gets the user by username using Principal class */
        User user = userService.findByUsername(principal.getName());

        /** Gets his savings account linked to the entity*/
        SavingsAccount savingsAccount = user.getSavingsAccount();

        /** Insert the primary account in the Spring model object to be used on the HTML page */
        model.addAttribute(AccountsEnum.MODEL_SAVINGS_ACCOUNT.getAccount(), savingsAccount);
        /** Insert the savings transaction list in the Spring model object to be used on the HTML page */
        model.addAttribute(TransactionsEnum.MODEL_SAVINGS_TRANSACTION_LIST.getMessage(), savingsTransactions);

        /** Goes to the savingsAccount HTML page */
        return AccountsEnum.SAVINGS_ACCOUNTS_VIEW_NAME.getAccount();
    }
    
    /**
     * Method that makes the deposit according to the amount inserted by user
     * 
     * @param model - The Spring web model that contains the values in its fields
     * @return the web page name where spring will will redirect to.
     */
    @RequestMapping(value = DEPOSIT_URL_MAPPING, method = RequestMethod.GET)
    public String deposit(Model model){
    	LOG.info("inside method deposit");
    	
    	/* Initializing the accountType variable to empty so the user can fill it */
    	model.addAttribute(TransactionsEnum.MODEL_ACCOUNT_TYPE.getMessage(), "");
    	/* Initializing the amount variable to empty so the user can fill it */
    	model.addAttribute(TransactionsEnum.MODEL_AMOUNT.getMessage(), "");
    	
    	return TransactionsEnum.DEPOSIT_VIEW_NAME.getMessage();
    }

    /**
     * Method that makes the withdraw according to the amount requested by user
     * 
     * @param model - The Spring web model that contains the values in its fields
     * @return the web page name where spring will will redirect to.
     */
    @RequestMapping(value = WITHDRAW_URL_MAPPING, method = RequestMethod.GET)
    public String withdraw(Model model){
    	LOG.info("inside method withdraw");

    	/* Initializing the accountType variable to empty so the user can fill it */
        model.addAttribute(TransactionsEnum.MODEL_ACCOUNT_TYPE.getMessage(), "");
        /* Initializing the amount variable to empty so the user can fill it */
        model.addAttribute(TransactionsEnum.MODEL_AMOUNT.getMessage(), "");

        return TransactionsEnum.WITHDRAW_VIEW_NAME.getMessage();
    }
    
    /**
     * Method that makes the deposit post to the database
     * 
     * @param amount - The amount informed by user
     * @param accountType - The account type informed by user
     * @param principal - The java security object used to retrieve the user credentials
     * @return the link that redirects to the main page if the insertion is successful
     */
    @RequestMapping(value = DEPOSIT_URL_MAPPING, method = RequestMethod.POST)
    public String depositPost(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Principal principal){
    	LOG.info("inside method depositPost");
    	
    	// Makes the deposit to the database
    	accountService.deposit(accountType, Double.parseDouble(amount), principal);
    	
    	return TransactionsEnum.REDIRECT_USER_FRONT_VIEW_NAME.getMessage();
    }

    /**
     * Method that makes the withdraw post from the database
     * 
     * @param amount - The amount informed by user
     * @param accountType - The account type informed by user
     * @param principal - The java security object used to retrieve the user credentials
     * @return the link that redirects to the main page if the insertion is successful
     */
    @RequestMapping(value = WITHDRAW_URL_MAPPING, method = RequestMethod.POST)
    public String withdrawPost(@ModelAttribute("amount") String amount, @ModelAttribute("accountType") String accountType, Model model, Principal principal){
    	LOG.info("inside method depositPost");
    	
    	// Makes the withdraw from the database
        boolean hasNoValue = accountService.withdraw(accountType, Double.parseDouble(amount), principal);

        if(hasNoValue){
        	
        	model.addAttribute("", hasNoValue);
        	
        	return TransactionsEnum.WITHDRAW_VIEW_NAME.getMessage();
        	
        } else {
        	
        	return TransactionsEnum.REDIRECT_USER_FRONT_VIEW_NAME.getMessage();
        	
        }
        
    }
}
