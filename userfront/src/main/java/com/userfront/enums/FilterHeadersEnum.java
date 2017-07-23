package com.userfront.enums;

public enum FilterHeadersEnum {
	
	HEADER_ORIGIN("Access-Control-Allow-Origin", "http://localhost:4200"),
    HEADER_METHODS("Access-Control-Allow-Methods", "POST,PUT,GET,OPTIONS,DELETE"),
    HEADER_SOME_METHODS("Access-Control-Allow-Methods", "POST,GET,DELETE"),
    HEADER_MAX_AGE("Access-Control-Max-Age", "3600"),
    HEADER_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials", "true"),
    HEADER_ALLOW_HEADERS("Access-Control-Allow-Headers", "authorization,x-requested-with,x-xsrf-token");

    private final String mKey;

    private final String mValue;

    FilterHeadersEnum(String mKey, String mValue) {
        this.mKey = mKey;
        this.mValue = mValue;
    }

	public String getmKey() {
		return mKey;
	}

	public String getmValue() {
		return mValue;
	}

}
