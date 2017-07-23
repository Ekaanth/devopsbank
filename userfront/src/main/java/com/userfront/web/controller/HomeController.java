package com.userfront.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.backend.domain.PrimaryAccount;
import com.userfront.backend.domain.SavingsAccount;
import com.userfront.backend.domain.User;
import com.userfront.backend.service.UserService;

/**
 * Created by root on 25/06/17.
 */
@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    /** Testing the screen signup
     * >This is for the index page */
    @RequestMapping("/")
    public String home(){
        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/userFront")
    public String userFront(Principal principal, Model model){

        User user = userService.findByUsername(principal.getName());

        PrimaryAccount primaryAccount = user.getPrimaryAccount();
        SavingsAccount savingsAccount = user.getSavingsAccount();

        model.addAttribute("primaryAccount", primaryAccount);
        model.addAttribute("savingsAccount", savingsAccount);

        return "mainPage/userFront";
    }
}
