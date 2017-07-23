package com.userfront.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by root on 04/06/17.
 */
@Service
public class I18NService {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(I18NService.class);

    @Autowired
    private MessageSource messageSource;

    /**
     * Returns a message for the given message id and the default locale in the session context
     * @param messageId The key to the messages resource file
     **/
    public String getMessage(String messageId){
        LOG.info("Returning i18n text for messageId {}", messageId);

        Locale locale = LocaleContextHolder.getLocale();

        return getMessage(messageId, locale);

    }

    /**
     * Returns a message for the given message id, the two string parameters and the default locale in the session context
     * 
     * @param messageId - The key to the messages resource file
     * @param param1 - The first parameter for the first index of the object on .properties file
     * @param param2 - The second parameter for the second index of the object on .properties file
     * @return - the formatted messages for the calling method
     */
    public String getTwoParamMessage(String messageId, String param1, String param2){
        LOG.info("Returning i18n text for messageId {}, an its parameters, param1: {} and param2: {}", messageId, param1, param2);

        Locale locale = LocaleContextHolder.getLocale();
        
        Object[] values = new Object[]{param1, param2};
        
        return getParamsMessage(messageId, values, locale);
    }

    /**
     * Returns a message for the given message id, the string parameter and the default locale in the session context
     * 
     * @param messageId - The key to the messages resource file
     * @param param - The parameter for the first index of the object on .properties file
     * @return - the formatted messages for the calling method
     */
    public String getParamMessage(String messageId, String param){
        LOG.info("Returning i18n text for messageId {}, an its parameter, param: {}", messageId, param);

        Locale locale = LocaleContextHolder.getLocale();
        
        Object[] values = new Object[]{param};
        
        return getParamsMessage(messageId, values, locale);
    }
    
    /**
     * Returns a message for the given message id, the two string parameters and the default locale in the session context
     * 
     * @param messageId - The key to the messages resource file
     * @param values - The Object array of strings to be read by the messages.properties file
     * @param locale - The locale
     * @return the formatted message
     */
    private String getParamsMessage(String messageId, Object[] values, Locale locale){
    	
    	return messageSource.getMessage(messageId, values, locale);
    }
    
    /**
     * Returns a message for the given message id and locale
     * @param messageId The key to the messages resource file
     * @param locale The locale
     **/
    private String getMessage(String messageId, Locale locale){

        return messageSource.getMessage(messageId, null, locale);
    }
}
