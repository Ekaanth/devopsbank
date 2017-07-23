package com.userfront.backend.resource;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.userfront.backend.domain.PrimaryTransaction;
import com.userfront.backend.domain.SavingsTransaction;
import com.userfront.backend.domain.User;
import com.userfront.backend.service.TransactionService;
import com.userfront.backend.service.UserService;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasRole('ADMIN')")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
	public @ResponseBody Set<User> userList(){
		
		return userService.findAllUsers();
		
	}
	
	@RequestMapping(value = "/user/primary/transaction", method = RequestMethod.GET)
	public @ResponseBody Set<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username){
		return transactionService.findPrimaryTransactionList(username);
	}
	
	@RequestMapping(value = "/user/savings/transaction", method = RequestMethod.GET)
	public @ResponseBody Set<SavingsTransaction> getSavingsTransactionList(@RequestParam("username") String username){
		return transactionService.findSavingsTransactionList(username);
	}
	
	@RequestMapping("/user/{username}/enable")
	public void enableUser(@PathVariable("username") String username){
		userService.enableUser(username);
	}
	
	@RequestMapping("/user/{username}/disable")
	public void disableUser(@PathVariable("username") String username){
		userService.disableUser(username);
	}
}
