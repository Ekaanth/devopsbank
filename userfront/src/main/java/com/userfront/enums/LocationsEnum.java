package com.userfront.enums;

public enum LocationsEnum {

	/* Messages from .properties file */
    NEW_YORK("New York"),
    CHICAGO("Chicago"),
    NEW_ORLEANS("New Orleans"),
    SAN_FRANCISCO("San Francisco"),
	LOS_ANGELES("Los Angeles");

	
    private final String place;
	
    LocationsEnum(String place){
		this.place = place;
	}

	public String getPlace() {
		return place;
	}
	
}
