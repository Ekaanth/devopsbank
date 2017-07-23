package com.userfront.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.backend.domain.User;
import com.userfront.backend.service.UserService;
import com.userfront.enums.UserEnum;

@Controller
@RequestMapping("/user")
public class UserController {
	
	public static final String PROFILE_URL_MAPPING = "/profile"; 

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = PROFILE_URL_MAPPING, method = RequestMethod.GET)
	public String profile(Principal principal, Model model){
		
		User user = userService.findByUsername(principal.getName());
		
		model.addAttribute(UserEnum.MODEL_USER.getValue(), user);
		
		return UserEnum.PROFILE_VIEW_NAME.getValue();
	}
	
	@RequestMapping(value = PROFILE_URL_MAPPING, method = RequestMethod.POST)
	public String profilePost(@ModelAttribute("user") User newUser, Model model){
		
		User user = userService.findByUsername(newUser.getUsername());
		
		user.setUsername(newUser.getUsername());
		user.setFirstName(newUser.getFirstName());
		user.setLastName(newUser.getLastName());
		user.setEmail(newUser.getEmail());
		user.setPhone(newUser.getPhone());
		
		model.addAttribute(UserEnum.MODEL_USER.getValue(), user);
		
		userService.saveUser(user);
	
		
		return UserEnum.PROFILE_VIEW_NAME.getValue();
	}
}
