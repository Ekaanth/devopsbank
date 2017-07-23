package com.userfront.enums;

public enum AppointmentEnum {
	
	REDIRECT_USER_FRONT_VIEW_NAME("redirect:/userFront"),
	DATE_FORMAT("yyyy-MM-dd hh:mm"),
	APPOINTMENT_VIEW_NAME("appointment/appointment"),
	MODEL_APPOINTMENT("appointment"),
	MODEL_DATE("dateString");

    private final String message;

    AppointmentEnum(String schedule) {
        this.message = schedule;
    }

    public String getMessage() {
        return message;
    }

}
