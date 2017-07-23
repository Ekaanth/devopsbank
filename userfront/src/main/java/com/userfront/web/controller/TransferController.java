package com.userfront.web.controller;

import java.security.Principal;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.userfront.backend.domain.PrimaryAccount;
import com.userfront.backend.domain.Recipient;
import com.userfront.backend.domain.SavingsAccount;
import com.userfront.backend.domain.User;
import com.userfront.backend.service.TransactionService;
import com.userfront.backend.service.UserService;
import com.userfront.enums.TransferEnum;

@Controller
@RequestMapping("/transfer")
public class TransferController {
	
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(TransferController.class);
    
    public static final String BETWEEN_ACCOUNTS_URL_MAPPING = "/betweenAccounts";
    public static final String RECIPIENT_URL_MAPPING = "/recipient";
    public static final String TO_SOMEONE_ELSE_URL_MAPPING = "/toSomeoneElse";
    public static final String RECIPIENT_SAVE_URL_MAPPING = "/recipient/save";
    public static final String RECIPIENT_EDIT_URL_MAPPING = "/recipient/edit";
    public static final String RECIPIENT_DELETE_URL_MAPPING = "/recipient/delete";
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserService userService;


	/**
	 * Between Accounts method that leads to the between accounts page
	 * 
	 * @param model - The spring model to map the objects on the HTML page 
	 * @return - The view name page
	 */
	@RequestMapping(value = BETWEEN_ACCOUNTS_URL_MAPPING, method = RequestMethod.GET)
	public String betweenAccounts(Model model){
		
		model.addAttribute(TransferEnum.MODEL_TRANSFER_FROM.getMessage(), "");
		model.addAttribute(TransferEnum.MODEL_TRANSFER_TO.getMessage(), "");
		model.addAttribute(TransferEnum.MODEL_AMOUNT.getMessage(), "");

		return TransferEnum.BETWEEN_ACCOUNTS_VIEW_NAME.getMessage();
	}
	
	/**
	 * Between Accounts post method to execute the transfer process between the accounts chosen by user
	 * 
	 * @param transferFrom - The Account type chosen by user to transfer from
	 * @param transferTo - The Account type chosen by user to transfer to
	 * @param amount - The amount the user wants to transfer
	 * @param principal - The java security class to check the valid user
	 * @return - The main page if success
	 * @throws Exception - If an error occurs
	 */
	@RequestMapping(value = BETWEEN_ACCOUNTS_URL_MAPPING, method = RequestMethod.POST)
	public String betweenAccountsPost(@ModelAttribute("transferFrom") String transferFrom,
									  @ModelAttribute("transferTo") String transferTo,
									  @ModelAttribute("amount") String amount,
									  Principal principal) throws Exception {
		
		LOG.info("Getting the variables from HTML page: transferFrom {}, transferTo {} and amount {}", transferFrom, transferTo, amount);
		
		User user = userService.findByUsername(principal.getName());
		
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		SavingsAccount savingsAccount = user.getSavingsAccount();
		
		transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, primaryAccount, savingsAccount);
		
		return TransferEnum.REDIRECT_USER_FRONT_VIEW_NAME.getMessage();
	}
	
	/**
	 * Method that goes to the recipient main page
	 * 
	 * @param model - The spring model to map the objects on the HTML page 
	 * @param principal - The java security class to check the valid user
	 * @return - The recipient main page
	 */
	@RequestMapping(value = RECIPIENT_URL_MAPPING, method = RequestMethod.GET)
	public String recipient(Model model, Principal principal){
		
		Set<Recipient> recipients = transactionService.findRecipients(principal);
		
		Recipient recipient = new Recipient();

		model.addAttribute(TransferEnum.MODEL_RECIPIENT_LIST.getMessage(), recipients);
		model.addAttribute(TransferEnum.MODEL_RECIPIENT.getMessage(), recipient);
		
		return TransferEnum.RECIPIENT_VIEW_NAME.getMessage();
	}
	
	/**
	 * Method that posts the recipient information to the database
	 * 
	 * @param recipient - The recipient object to be persisted
	 * @param principal - The java security class to check the valid user
	 * @return - The redirect to the recipient page
	 */
	@RequestMapping(value = RECIPIENT_SAVE_URL_MAPPING, method = RequestMethod.POST)
	public String recipientPost(@ModelAttribute("recipient") Recipient recipient, Model model, Principal principal){
		
		User user = userService.findByUsername(principal.getName());
		recipient.setUser(user);
		Recipient recipientAdded = transactionService.saveRecipient(recipient);
		
		if(recipientAdded == null){
			// Set the message key to show the correct bootstrap alert message on the screen
			model.addAttribute(TransferEnum.ERROR_MESSAGE_KEY.getMessage(), "Recipient not created");
			model.addAttribute(TransferEnum.RECIPIENT_MESSAGE_KEY.getMessage(), "false");
			
			return TransferEnum.RECIPIENT_VIEW_NAME.getMessage();
			
		}
		
        // Set the message key to show the correct bootstrap alert message on the screen
        model.addAttribute(TransferEnum.RECIPIENT_MESSAGE_KEY.getMessage(), "true");
		
		return TransferEnum.REDIRECT_RECIPIENT_VIEW_NAME.getMessage();
	}
	
	/**
	 * Recipient edit method to update the recipient information
	 * 
	 * @param recipientName - The recipient name to retrieve the respective recipient
	 * @param model - The spring model to map the objects on the HTML page 
	 * @param principal - The java security class to check the valid user
	 * @return
	 */
	@RequestMapping(value = RECIPIENT_EDIT_URL_MAPPING, method = RequestMethod.GET)
	public String recipientEdit(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){
		
		Recipient recipient = transactionService.findRecipientByName(recipientName);
		Set<Recipient> recipients = transactionService.findRecipients(principal);
		
		model.addAttribute(TransferEnum.MODEL_RECIPIENT_LIST.getMessage(), recipients);
		model.addAttribute(TransferEnum.MODEL_RECIPIENT.getMessage(), recipient);
		
		return TransferEnum.RECIPIENT_VIEW_NAME.getMessage();
		
	}
	
	/**
	 * Recipient delete method to delete the recipient information from the database
	 * 
	 * @param recipientName - The recipient name to retrieve the respective recipient
	 * @param model - The spring model to map the objects on the HTML page 
	 * @param principal - The java security class to check the valid user
	 * @return
	 */
	@RequestMapping(value = RECIPIENT_DELETE_URL_MAPPING, method = RequestMethod.GET)
	@Transactional
	public String recipientDelete(@RequestParam(value = "recipientName") String recipientName, Model model, Principal principal){
		
		transactionService.deleteByRecipientName(recipientName);
		
		Set<Recipient> recipients = transactionService.findRecipients(principal);

		Recipient recipient = new Recipient();
		
		model.addAttribute(TransferEnum.MODEL_RECIPIENT_LIST.getMessage(), recipients);
		model.addAttribute(TransferEnum.MODEL_RECIPIENT.getMessage(), recipient);
		
		return TransferEnum.REDIRECT_RECIPIENT_VIEW_NAME.getMessage();
	}
	
	/**
	 * Method that goes to the HTML page 'to someone else'
	 * 
	 * @param model - The spring model to map the objects on the HTML page 
	 * @param principal - The java security class to check the valid user
	 * @return - The someone else HTML page view name
	 */
	@RequestMapping(value = TO_SOMEONE_ELSE_URL_MAPPING, method = RequestMethod.GET)
	public String toSomeoneElse(Model model, Principal principal){
		
		Set<Recipient> recipients = transactionService.findRecipients(principal);
		
		model.addAttribute(TransferEnum.MODEL_RECIPIENT_LIST.getMessage(), recipients);
		model.addAttribute(TransferEnum.MODEL_ACCOUNT_TYPE.getMessage(), "");
		
		return TransferEnum.TO_SOMEONE_ELSE_VIEW_NAME.getMessage();
		
	}
	
	/**
	 * Method that makes the transfer to someone else by executing the 'toSomeoneElseTransfer' method
	 * 
	 * @param recipientName - The recipient name received as parameter
	 * @param accountType - The account type from which the amount will be transfered
	 * @param amount - The amount value to be transfered
	 * @param principal - The java security class to check the valid user
	 * @return - Redirect to user front main page if success
	 */
	@RequestMapping(value = TO_SOMEONE_ELSE_URL_MAPPING, method = RequestMethod.POST)
	public String toSomeoneElsePost(@ModelAttribute("recipientName") String recipientName, @ModelAttribute("accountType") String accountType, @ModelAttribute("amount") String amount, Principal principal){
		
		User user = userService.findByUsername(principal.getName());
		Recipient recipient = transactionService.findRecipientByName(recipientName);
		transactionService.toSomeoneElseTransfer(recipient, accountType, amount, user.getPrimaryAccount(), user.getSavingsAccount());

		return TransferEnum.REDIRECT_USER_FRONT_VIEW_NAME.getMessage();
		
	}
}
