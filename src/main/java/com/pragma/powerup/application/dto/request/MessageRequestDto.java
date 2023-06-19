package com.pragma.powerup.application.dto.request;

import lombok.Data;

@Data
public class MessageRequestDto {
    private String phoneNumber;
    private String securityCode;

}
