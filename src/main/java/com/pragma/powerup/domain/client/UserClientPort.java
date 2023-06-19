package com.pragma.powerup.domain.client;

public interface UserClientPort {

    boolean validateAdministrator(String token);

    boolean validateOwnerById(Long id);


    boolean validateClientByToken(String token);

    Long findOwnerId(String token);

    Long findClientId(String token);

    Long findEmployeeId(String token);

    String findPhoneByClientId(Long clientId);
}

