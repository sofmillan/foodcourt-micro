package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.infrastructure.constants.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserClientAdapter implements UserClientPort {
    private final UserServiceClient userServiceClient;

    public boolean validateAdministrator(String token){
        return userServiceClient.roles(token).contains(Role.ADMIN.getMessage());
    }


    @Override
    public boolean validateOwnerById(Long id) {
        return userServiceClient.findOwnerById(id);
    }

    @Override
    public boolean validateClientByToken(String token) {
        return userServiceClient.roles(token).contains(Role.CLIENT.getMessage());
    }

    @Override
    public Long findOwnerId(String token) {
        return userServiceClient.findOwner(token);
    }

    @Override
    public Long findClientId(String token) {
        return userServiceClient.findClientId(token);
    }

    @Override
    public Long findEmployeeId(String token) {
        return userServiceClient.findEmployeeId(token);
    }

    @Override
    public String findPhoneByClientId(Long clientId) {
        return userServiceClient.findPhoneById(clientId);
    }

}
