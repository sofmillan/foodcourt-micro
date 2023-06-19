package com.pragma.powerup.domain.model;

public class MessageModel {
    private String phoneNumber;
    private String securityCode;

    public MessageModel(){}
    public MessageModel(String phoneNumber, String securityCode) {
        this.phoneNumber = phoneNumber;
        this.securityCode = securityCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
