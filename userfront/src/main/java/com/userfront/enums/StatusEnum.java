package com.userfront.enums;

/**
 * Created by root on 10/06/17.
 */
public enum StatusEnum {

    /** Status */
    FINISHED("Finished"),
    PENDING("Pending");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
