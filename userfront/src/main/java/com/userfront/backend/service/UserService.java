package com.userfront.backend.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.userfront.backend.domain.User;
import com.userfront.backend.domain.security.Role;
import com.userfront.backend.repositories.RoleRepository;
import com.userfront.backend.repositories.UserRepository;
import com.userfront.exceptions.ErrorRepositoryException;

/**
 * Created by root on 27/06/17.
 */
@Service
@Transactional
public class UserService {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    public User create(User user){

        User createdUser = userRepository.findByEmail(user.getEmail());

        // Check if the user already exist
        if (createdUser != null){

            LOG.info("User with username {} already exist.", user.getUsername());

        } else {

            try {

                // encrypting the password field to be saved in database
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encryptedPassword);

                /** Saves the other side of the user to roles
                 * relationship by persisting all roles in
                 * the UserRoles collection */
                for (Role role : user.getRoles()) {

                    roleRepository.save(role);
                }

                // Set the User accounts information here
                user.setPrimaryAccount(accountService.createPrimaryAccount());
                user.setSavingsAccount(accountService.createSavingsAccount());

                // Gets the saved User object in database
                createdUser = userRepository.save(user);

            } catch (Exception e){
                throw new ErrorRepositoryException(e);
            }
        }


        return createdUser;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }

    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }


    public Set<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void enableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void disableUser (String username) {
        User user = findByUsername(username);
        user.setEnabled(false);

        LOG.info("The username {} is {}", username, user.isEnabled());

        userRepository.save(user);

        LOG.info("The username {} is {}", username, user.isEnabled());
    }
    
    /**
     * This method comes from the User profile page, requested by the new register
     * 
     * @param user - The User object param
     * @return The user object saved in database
     */
    public User saveUser(User user){
    	return userRepository.save(user);
    }
}
