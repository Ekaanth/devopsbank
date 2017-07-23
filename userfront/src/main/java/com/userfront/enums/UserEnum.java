package com.userfront.enums;

public enum UserEnum {

	/* Messages from .properties file */
	PROFILE_URL_MAPPING("/profile"),
	PROFILE_VIEW_NAME("profile/profile"),
	MODEL_USER("user");

	
    private final String value;
	
    UserEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
