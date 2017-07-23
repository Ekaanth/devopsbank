package com.userfront.integration;

import com.userfront.UserfrontApplication;
import com.userfront.backend.domain.User;
import com.userfront.backend.domain.security.Role;
import com.userfront.backend.repositories.RoleRepository;
import com.userfront.backend.repositories.UserRepository;
import com.userfront.backend.service.AccountService;
import com.userfront.backend.service.UserService;
import com.userfront.enums.RolesEnum;
import com.userfront.utils.UserUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by root on 01/07/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserfrontApplication.class)
public class UserServiceIntegrationTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    protected AccountService accountService;

    @Rule
    public TestName testName = new TestName();

    /**
     * Test method to create a new role for the user
     * 
     * @throws Exception
     */
    @Test
    public void testCreateNewRole() throws Exception {
        
    	Role basicRole = createBasicRole(RolesEnum.BASIC);
        
        roleRepository.save(basicRole);
        
        Role retrieveRole = roleRepository.findByName(RolesEnum.BASIC.getRoleName());
        
        Assert.assertNotNull(retrieveRole);

    }

    /**
     * Test method to create a new User
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    public void testCreateNewUser() throws Exception {

        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@devopsbuddy.com";

        User basicUser = createUser(username, email);

        for (Role role : basicUser.getRoles()) {

            roleRepository.save(role);
        }

        /** Now that all relationship entities have been
         * saved, it saves the user entity */
        basicUser = userRepository.save(basicUser);

        /** Getting a user in database */
        User newlyCreatedUser = userRepository.findOne(basicUser.getId());

        // Tests ============================================>>>
        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);

        Set<Role> newlyCreatedUserUserRoles = newlyCreatedUser.getRoles();
        for (Role ur : newlyCreatedUserUserRoles) {
            Assert.assertNotNull(ur.getName());
            Assert.assertNotNull(ur.getId());
        }

    }


    /**
     * Test method to retrieve user object by email in database
     * 
     * @throws Exception
     */
    @Test
    public void testGetUserByEmail() throws Exception {

        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@devopsbank.com";

        User user = createUser(username, email);

        User newlyFoundUser = userRepository.findByEmail(user.getEmail());
        Assert.assertNotNull(newlyFoundUser);
        Assert.assertNotNull(newlyFoundUser.getId());

    }

    /**
     * Test method to update the user's password
     * 
     * @throws Exception
     */
    @Test
    public void testUpdateUserPassword() throws Exception {

        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@devopsbank.com";

        User user = createUser(username, email);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

        String newPassword = UUID.randomUUID().toString();

        userRepository.updateUserPassword(user.getId(), newPassword);

        user = userRepository.findOne(user.getId());

        Assert.assertEquals(newPassword, user.getPassword());

    }

    /**
     * Method that creates a new user
     * 
     * @param username
     * @param email
     * @return
     */
    protected User createUser(String username, String email) {

        /** Creates a role */
        Role basicRole = createBasicRole(RolesEnum.BASIC);

        /** Creates the user and add the plan object as a foreign key */
        User basicUser = UserUtils.createUser(username, email);
        
        /** Set the User accounts information here */
        basicUser.setPrimaryAccount(accountService.createPrimaryAccount());
        basicUser.setSavingsAccount(accountService.createSavingsAccount());

        /** Creates a Set collection of roles due to the
         * one to many relationship between entities */
        Set<Role> roles = new HashSet<>();

        /** Creates the object that represent the one to many
         * relationship between user and role entities and add
         * both objects on entity as foreign key */
        roles.add(basicRole);

        /** Adding the object collection of user roles to user entity
         * (Always call the get method of Set collection to add objects in JPA)*/
        basicUser.setRoles(roles);
        

        return basicUser;
    }

    /**
     * Method to create a new role for the user
     * 
     * @param rolesEnum
     * @return
     */
    protected Role createBasicRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

}
