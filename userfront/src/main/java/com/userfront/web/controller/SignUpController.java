package com.userfront.web.controller;

import com.userfront.backend.domain.User;
import com.userfront.backend.domain.security.Role;
import com.userfront.backend.service.UserService;
import com.userfront.enums.RolesEnum;
import com.userfront.enums.SignUpEnum;
import com.userfront.exceptions.ErrorRepositoryException;
import com.userfront.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by root on 26/06/17.
 */
@Controller
public class SignUpController {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(SignUpController.class);

    public static final String SIGNUP_URL_MAPPING = "/signup";
    public static final String USER_MODEL_KEY_NAME = "user";

    @Autowired
    private UserService userService;


    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.GET)
    public String signUp(ModelMap model){

        model.addAttribute(SignUpEnum.USER_KEY_NAME.getValue(), new User());

        return SignUpEnum.SIGN_UP_VIEW_NAME.getValue();
    }

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.POST)
    public String signUpPost(@ModelAttribute(USER_MODEL_KEY_NAME) @Valid User user, ModelMap model){

        if (userService.checkUserExists(user.getUsername(), user.getEmail())){

            if (userService.checkEmailExists(user.getEmail())){
                model.addAttribute(SignUpEnum.EMAIL_EXIST_BOOL.getValue(), "true");
                model.addAttribute(SignUpEnum.ERROR_MESSAGE_KEY.getValue(), "Email already exist");
            }

            if (userService.checkUsernameExists(user.getUsername())){
                model.addAttribute(SignUpEnum.USERNAME_EXIST_BOOL.getValue(), "true");
                model.addAttribute(SignUpEnum.ERROR_MESSAGE_KEY.getValue(), "Username already exist");
            }

            return SignUpEnum.SIGN_UP_VIEW_NAME.getValue();

        } else {

            /**
             * block will change to be compared to the department later
             * so it can be persisted dynamically
             * */
            Set<Role> roles = new HashSet<>();
            roles.add(new Role(RolesEnum.ADMIN));

            user.setRoles(roles);

            userService.create(user);

            model.addAttribute(SignUpEnum.SIGNED_UP_MESSAGE_KEY.getValue(), "true");

            return SignUpEnum.HOME_VIEW_NAME.getValue();
        }

    }

    /**
     * Using the Spring MVC Exception Handling to handle the StripeException and S3Exception classes
     * because both exceptions are only on the journey of sign up users to the database via the
     * Spring Controller.
     * It handles the exceptions created to the Stripe service and the Amazon S3 Cloud service
     *
     * @param request The HttpServletRequest object for manipulate the view
     * @param exception The exception that Spring will set automatically if it occurs
     * @return the ModelAndView object(combination of a ModelMap to a View name) with the data
     * filled to be shown on generalError HTML page
     */
    @ExceptionHandler({ErrorRepositoryException.class})
    public ModelAndView signUpException(HttpServletRequest request, Exception exception){
        LOG.error("Request {} raised exception {}", request.getRequestURL(), exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("timestamp", Utils.formatDate(LocalDate.now(Clock.systemUTC())));
        mav.setViewName(SignUpEnum.GENERIC_ERROR_VIEW_NAME.getValue());

        return mav;
    }
}
