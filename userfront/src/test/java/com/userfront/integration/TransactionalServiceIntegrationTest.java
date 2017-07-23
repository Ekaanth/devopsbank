package com.userfront.integration;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.userfront.UserfrontApplication;
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
import com.userfront.backend.repositories.UserRepository;
import com.userfront.backend.service.AccountService;
import com.userfront.backend.service.TransactionService;
import com.userfront.enums.StatusEnum;
import com.userfront.enums.TransactionsEnum;
import com.userfront.utils.RecipientUtils;
import com.userfront.utils.TransactionUtils;
import com.userfront.utils.UserUtils;

/**
 * Created by root on 02/07/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserfrontApplication.class)
@Transactional
public class TransactionalServiceIntegrationTest {

    @Autowired
    private PrimaryTransactionsRepository primaryTransactionsRepository;

    @Autowired
    private SavingsTransactionsRepository savingsTransactionsRepository;
    
    @Autowired
    private PrimaryAccountRepository primaryAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private RecipientRepository recipientRepository;

    @Rule
    public TestName testName = new TestName();

    private double amount = 300;

    
    @Before
    public void init(){
        Assert.assertFalse(amount == 0);
    }
    
    /**
     * Test method to create a new Savings transaction
     * 
     * @throws Exception
     */
    @Test
    public void testCreateNewSavingsTransaction() throws Exception {
    	/** Flag used to check if the user is doing a deposit or withdraw and it will be used in the TransctionalUtils object */
    	String flag = "withdraw";
    	
    	SavingsAccount savingsAccount = accountService.createSavingsAccount();		
    	
    	SavingsTransaction savingsTransaction = TransactionUtils.createSavingsTransaction(savingsAccount, amount, flag, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
    	
    	savingsTransactionsRepository.save(savingsTransaction);
    	
    	Iterable<SavingsTransaction> transactionalSaved = savingsTransactionsRepository.findAll();
    	
    	Assert.assertTrue(transactionalSaved.iterator().hasNext());
    	
    	while (transactionalSaved.iterator().hasNext()){
    		
    		SavingsTransaction savingsTrSaved = transactionalSaved.iterator().next();
    		
    		Assert.assertNotNull(savingsTrSaved);
    		
    	}
    	
    }

    /**
     * Test method to create a new primary transaction
     * 
     * @throws Exception
     */
    @Test
    public void testCreateNewPrimaryTransaction() throws Exception {
    	/** Flag used to check if the user is doing a deposit or withdraw and it will be used in the TransctionalUtils object */
    	String flag = "withdraw";

        PrimaryAccount primaryAccount = accountService.createPrimaryAccount();

        PrimaryTransaction primaryTransaction = TransactionUtils.createPrimaryTransaction(primaryAccount, amount, flag, TransactionsEnum.ACCOUNT_KEY.getMessage(), StatusEnum.FINISHED.getStatus());
        
        primaryTransactionsRepository.save(primaryTransaction);

        Iterable<PrimaryTransaction> transactionalSaved = primaryTransactionsRepository.findAll();

        Assert.assertTrue(transactionalSaved.iterator().hasNext());

        while (transactionalSaved.iterator().hasNext()){

            PrimaryTransaction primaryTrSaved = transactionalSaved.iterator().next();

            Assert.assertNotNull(primaryTrSaved);

        }

    }
    
    @Test
    public void createNewBetweenAccountsTransferPrimaryToSavingsTest() throws Exception {
    	
        PrimaryAccount primaryAccount = accountService.createPrimaryAccount();
        SavingsAccount savingsAccount = accountService.createSavingsAccount();
        
        transactionService.betweenAccountsTransfer(TransactionsEnum.PRIMARY_ACCOUNT.getMessage(), TransactionsEnum.SAVINGS_ACCOUNT.getMessage(), String.valueOf(amount), primaryAccount, savingsAccount);
        
        Iterable<PrimaryTransaction> transactionalSaved = primaryTransactionsRepository.findAll();
        
        Assert.assertTrue(transactionalSaved.iterator().hasNext());
        
        while (transactionalSaved.iterator().hasNext()){

            PrimaryTransaction primaryTrSaved = transactionalSaved.iterator().next();

            Assert.assertNotNull(primaryTrSaved);

        }
        
    }
    
    @Test
    public void testCreateNewRecipient() throws Exception {

    	// Test the new name and new email email
        String name = testName.getMethodName();
        String email = testName.getMethodName() + "@devopsbuddy.com";
        
        // Creates the new user object with the tested parameters
        User user = UserUtils.createNewUser(name, email);
        
        // Saves the linked accounts to the user into database and gets the newly saved ones
        // to put into the user relationships
        user.setPrimaryAccount(primaryAccountRepository.save(user.getPrimaryAccount()));
        user.setSavingsAccount(savingsAccountRepository.save(user.getSavingsAccount()));
        
        // Save the user object into database
        user = userRepository.save(user);

        // Creates the recipient object
        Recipient recipient = createRecipient(name, email);

        // Add the user to link with recipient
        recipient.setUser(user);
        
        /** Now that all relationship entities have been
         * saved, it saves the recipient entity */
        recipient = recipientRepository.save(recipient);

        /** Getting a user in database */
        Recipient newlyCreatedRecipient = recipientRepository.findOne(recipient.getId());

        // Tests ============================================>>>
        Assert.assertNotNull(newlyCreatedRecipient);
        
        Assert.assertTrue(newlyCreatedRecipient.getId() != 0);
        
        Assert.assertNotNull(newlyCreatedRecipient.getUser());
        Assert.assertNotNull(newlyCreatedRecipient.getUser().getPrimaryAccount());
        Assert.assertNotNull(newlyCreatedRecipient.getUser().getSavingsAccount());
        
        Assert.assertTrue(newlyCreatedRecipient.getUser().getId() != 0);
        Assert.assertTrue(newlyCreatedRecipient.getUser().getPrimaryAccount().getId() != 0);
        Assert.assertTrue(newlyCreatedRecipient.getUser().getSavingsAccount().getId() != 0);
        
        Recipient recipiendReached = transactionService.findRecipientByName(newlyCreatedRecipient.getName());
        
        Assert.assertNull(recipiendReached);
        Assert.assertTrue(recipient.getId() != 0);

    }
    
    protected Recipient createRecipient(String name, String email) {

        /** Creates the user and add the plan object as a foreign key */
        Recipient recipient = RecipientUtils.createRecipient(name, email);
        
        return recipient;
    }
}
