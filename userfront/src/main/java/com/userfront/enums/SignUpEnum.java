package com.userfront.enums;

/**
 * Created by root on 26/06/17.
 */
public enum SignUpEnum {

    HOME_VIEW_NAME("redirect:/index"),
    SIGNED_UP_MESSAGE_KEY("signedUp"),
    ERROR_MESSAGE_KEY("message"),
    GENERIC_ERROR_VIEW_NAME("error/errorPage"),
    EMAIL_EXIST_BOOL("emailExist"),
    USERNAME_EXIST_BOOL("usernameExist"),
    USER_KEY_NAME("user"),

    /** The Sign Up view name */
    SIGN_UP_VIEW_NAME("registration/signup");

    private String value;

    private SignUpEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
