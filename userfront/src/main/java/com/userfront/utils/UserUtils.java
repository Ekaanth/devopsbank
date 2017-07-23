package com.userfront.utils;

import java.util.HashSet;
import java.util.Set;

import com.userfront.backend.domain.User;
import com.userfront.backend.domain.security.Role;
import com.userfront.enums.RolesEnum;

/**
 * Created by root on 01/07/17.
 */
public class UserUtils {

    /**
     * Non instantiable
     */
    private UserUtils(){
        throw new AssertionError("Non instantiable");
    }

    /**
     * Creates a user with basic attributes set for test.
     *
     * @param username The username parameter.
     * @param email The email parameter.
     * @return The User object.
     */
    public static User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("secret");
        user.setEmail(email);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhone("123456789");
        user.setEnabled(true);
//        user.setProfileImageUrl("https://blablabla.images.com/basicuser");
        return user;
    }
    
    public static User createNewUser(String username, String email){
    	
        /** Creates a role */
        Role basicRole = createBasicRole(RolesEnum.BASIC);

        /** Creates the user and add the plan object as a foreign key */
        User basicUser = createUser(username, email);
        
        /** Set the User accounts information here */
        basicUser.setPrimaryAccount(AccountUtils.generatePrimaryAccount());
        basicUser.setSavingsAccount(AccountUtils.generateSavingsAccount());

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
    
    protected static Role createBasicRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }
}
