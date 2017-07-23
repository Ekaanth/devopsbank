package com.userfront.utils;

import com.userfront.backend.domain.Recipient;

/**
 * Created by root on 01/07/17.
 */
public class RecipientUtils {

    /**
     * Non instantiable
     */
    private RecipientUtils(){
        throw new AssertionError("Non instantiable");
    }

    /**
     * Creates a recipient with basic attributes set for test.
     *
     * @param name The name parameter.
     * @param email The email parameter.
     * @return The Recipient object.
     */
    public static Recipient createRecipient(String name, String email) {
    	
    	Recipient recipient = new Recipient();
        
    	recipient.setName(name);
        recipient.setEmail(email);
        recipient.setPhone("123456789");
        recipient.setAccountNumber("123456789");

        return recipient;
    }
}
