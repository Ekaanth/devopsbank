package com.userfront.backend.service;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.backend.domain.PrimaryAccount;
import com.userfront.backend.domain.PrimaryTransaction;
import com.userfront.backend.domain.Recipient;
import com.userfront.backend.domain.SavingsAccount;
import com.userfront.backend.domain.SavingsTransaction;
import com.userfront.backend.domain.User;
import com.userfront.backend.repositories.PrimaryAccountRepository;
import com.userfront.backend.repositories.PrimaryTransactionsRepository;
import com.userfront.backend.repositories.RecipientRepository;
import com.userfront.backend.repositories.SavingsAccountRepository;
import com.userfront.backend.repositories.SavingsTransactionsRepository;
import com.userfront.enums.SelectAccountsEnum;
import com.userfront.enums.StatusEnum;
import com.userfront.enums.TransactionsEnum;
import com.userfront.enums.TransferEnum;
import com.userfront.utils.TransactionUtils;

@Service
@Transactional
public class TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PrimaryTransactionsRepository primaryTransactionsRepository;
	
	@Autowired
	private SavingsTransactionsRepository savingsTransactionsRepository;
	
	@Autowired
	private PrimaryAccountRepository primaryAccountRepository;
	
	@Autowired
	private SavingsAccountRepository savingsAccountRepository;

	@Autowired
	private I18NService i18nService;
	
	@Autowired
	private RecipientRepository recipientRepository;
	
	/**
	 * This method gets from a Primary Account object its Transaction List object to be returned
	 * 
	 * @param username - The based username parameter to get the user object info.
	 * @return The primary transaction object in a Set collection from the user.
	 */
	public Set<PrimaryTransaction> findPrimaryTransactionList(String username) {
		
		User user = userService.findByUsername(username);
		
		Set<PrimaryTransaction> primaryTransactions = user.getPrimaryAccount().getPrimaryListTransactions();
		
		return primaryTransactions;
	}

	
	/**
	 * This method gets from a Savings Account object its Transaction List object to be returned
	 * 
	 * @param username - The based username parameter to get the user object info.
	 * @return The savings transaction object in a Set collection from the user.
	 */
	public Set<SavingsTransaction> findSavingsTransactionList(String username) {
		
		User user = userService.findByUsername(username);
		
		Set<SavingsTransaction> savingsTransactions = user.getSavingsAccount().getSavingsListTransactions();
		
		return savingsTransactions;
	}
	
	/**
	 * Method that saves Primary Transaction object in database
	 * 
	 * @param primaryTransaction - The primary transaction object
	 */
	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction){
		
		primaryTransactionsRepository.save(primaryTransaction);
		
	}
	
	/**
	 * Method that saves Savings Transaction object in database
	 * 
	 * @param savingsTransaction - The savings transaction object
	 */
	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction){
		
		savingsTransactionsRepository.save(savingsTransaction);
		
	}
	
	/**
	 * Method that saves Primary Transaction object in database
	 * 
	 * @param primaryTransaction - The primary transaction object
	 */
	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction){
		
		primaryTransactionsRepository.save(primaryTransaction);
		
	}
	
	/**
	 * Method that saves Savings Transaction object in database
	 * 
	 * @param savingsTransaction - The savings transaction object
	 */
	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction){
		
		savingsTransactionsRepository.save(savingsTransaction);
		
	}
	
	/**
	 * Method that saves Primary Account Transfer to Savings Transaction object in database
	 * 
	 * @param primaryTransaction - The primary transaction object
	 */
	public void savePrimaryTransferAccountToSavingsTransaction(PrimaryTransaction primaryTransaction){
		
		primaryTransactionsRepository.save(primaryTransaction);
		
	}

	/**
	 * Between Accounts method to transfer from some account to another some account by user's choice
	 * 
	 * @param transferFrom - Account type the user requested to transfer from
	 * @param transferTo - Account type the user requested to transfer to
	 * @param amount - Value the user requested to transfer
	 * @param primaryAccount - Account the user request to transfer from or transfer to
	 * @param savingsAccount - Account the user request to transfer from or transfer to
	 * @throws Exception - If any errors occur, thows an exception
	 */
	public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount,
										PrimaryAccount primaryAccount, SavingsAccount savingsAccount) throws Exception {
    	/** Description String variable to receive a message from messages.properties */
    	String description = "";
		
    	/** Checks what account the user chose to make the transfer */
		if(transferFrom.equalsIgnoreCase(TransactionsEnum.PRIMARY_ACCOUNT.getMessage()) && transferTo.equalsIgnoreCase(TransactionsEnum.SAVINGS_ACCOUNT.getMessage())){
			
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			
			primaryAccountRepository.save(primaryAccount);
			savingsAccountRepository.save(savingsAccount);
			
			 /** Gets the string message from messages.properties file and put it in the string variable*/
            description = i18nService.getTwoParamMessage(TransferEnum.TRANSFER_PRIMARY_TO_SAVINGS_ACCOUNT_TRANSACTION.getMessage(), transferFrom, transferTo);
			
			 /** Creates a transaction to register the deposit here */
            PrimaryTransaction primaryTransaction = TransactionUtils.createPrimaryTransaction(primaryAccount, Double.parseDouble(amount), description, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
           
            
            primaryTransactionsRepository.save(primaryTransaction);
            
		} else if(transferFrom.equalsIgnoreCase(TransactionsEnum.SAVINGS_ACCOUNT.getMessage()) && transferTo.equalsIgnoreCase(TransactionsEnum.PRIMARY_ACCOUNT.getMessage())){
			
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			
			primaryAccountRepository.save(primaryAccount);
			savingsAccountRepository.save(savingsAccount);
			
			 /** Gets the string message from messages.properties file and put it in the string variable*/
            description = i18nService.getTwoParamMessage(TransferEnum.TRANSFER_PRIMARY_TO_SAVINGS_ACCOUNT_TRANSACTION.getMessage(), transferFrom, transferTo);
			
			 /** Creates a transaction to register the deposit here */
            SavingsTransaction savingsTransaction = TransactionUtils.createSavingsTransaction(savingsAccount, Double.parseDouble(amount), description, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
           
            savingsTransactionsRepository.save(savingsTransaction);
			
		} else {
			
			throw new Exception("Invalid transfer");
			
		}
		
	}
	
	public Set<Recipient> findRecipients(Principal principal){
		
		String username = principal.getName();
		
		Set<Recipient> recipients = recipientRepository.findAll().stream() // Converts the Set to stream
				.filter(recipient -> username.equals(recipient.getUser().getUsername())) // filters the stream line to check if each instance matches the conditions 
				.collect(Collectors.toSet());
		
		return recipients;
		
	}
	
	public Recipient saveRecipient(Recipient recipient){
		
		return recipientRepository.save(recipient);
		
	}

	public Recipient findRecipientByName(String recipientName){
		
		return recipientRepository.findByName(recipientName);
		
	}
	
	public void deleteByRecipientName(String recipientName){
		
		recipientRepository.deleteByName(recipientName);
		
	}
	
	public void toSomeoneElseTransfer(Recipient recipient, String accountType, String amount, PrimaryAccount primaryAccount, SavingsAccount savingsAccount){
    	/** Description String variable to receive a message from messages.properties */
    	String description = "";
		
		if(accountType.equalsIgnoreCase(SelectAccountsEnum.PRIMARY_ACCOUNT.getAccount())){
			
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			
			primaryAccountRepository.save(primaryAccount);
			
			 /** Gets the string message from messages.properties file and put it in the string variable*/
           description = i18nService.getParamMessage(TransferEnum.TRANSFER_TO_SOMEONE_ELSE_ACCOUNT_TRANSACTION.getMessage(), recipient.getName());
			
			 /** Creates a transaction to register the deposit here */
           PrimaryTransaction primaryTransaction = TransactionUtils.createPrimaryTransaction(primaryAccount, Double.parseDouble(amount), description, TransferEnum.TRANSFER_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
          
           
           primaryTransactionsRepository.save(primaryTransaction);
			
		} else if(accountType.equalsIgnoreCase(SelectAccountsEnum.SAVINGS_ACCOUNT.getAccount())){
			
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			
			savingsAccountRepository.save(savingsAccount);
			
			 /** Gets the string message from messages.properties file and put it in the string variable*/
           description = i18nService.getParamMessage(TransferEnum.TRANSFER_TO_SOMEONE_ELSE_ACCOUNT_TRANSACTION.getMessage(), recipient.getName());
			
			 /** Creates a transaction to register the deposit here */
           SavingsTransaction savingsTransaction = TransactionUtils.createSavingsTransaction(savingsAccount, Double.parseDouble(amount), description, TransferEnum.TRANSFER_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
          
           savingsTransactionsRepository.save(savingsTransaction);
			
		}
	}
}
