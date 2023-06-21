package com.pragma.powerup;

import com.pragma.powerup.domain.client.UserClientPort;
import com.pragma.powerup.infrastructure.constants.Role;
import com.pragma.powerup.infrastructure.out.feign.UserClientAdapter;
import com.pragma.powerup.infrastructure.out.feign.UserServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserClientAdapterTest {
    UserServiceClient userServiceClient;
    UserClientPort userClient;

    @BeforeEach
    void setUp(){
        userServiceClient = mock(UserServiceClient.class);
        userClient = new UserClientAdapter(userServiceClient);
    }

    @Test
    void Should_ReturnFalse_When_IdIsNotRelatedToOwner(){
        Long userId = 1L;

        when(userServiceClient.findOwnerById(userId)).thenReturn(false);

        assertFalse(userClient.validateOwnerById(userId));

    }

    @Test
    void Should_ReturnTrue_When_UserIsAdmin(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        when(userServiceClient.roles(token)).thenReturn(List.of(Role.ADMIN.getMessage()));

        assertTrue(userClient.validateAdministrator(token));
    }

    @Test
    void Should_ReturnFalse_When_UserIsNotAdmin(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        when(userServiceClient.roles(token)).thenReturn(List.of(Role.EMPLOYEE.getMessage()));

        assertFalse(userClient.validateAdministrator(token));

    }

    @Test
    void Should_ReturnTrue_When_UserIsClient(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        when(userServiceClient.roles(token)).thenReturn(List.of(Role.CLIENT.getMessage()));

        assertTrue(userClient.validateClientByToken(token));
    }

    @Test
    void Should_ReturnFalse_When_UserIsNotClient(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        when(userServiceClient.roles(token)).thenReturn(List.of(Role.OWNER.getMessage()));

        assertFalse(userClient.validateClientByToken(token));
    }

    @Test
    void Should_ReturnTrue_When_IdIsRelatedToOwner(){
        Long userId = 1L;

        when(userServiceClient.findOwnerById(userId)).thenReturn(true);

        assertTrue(userClient.validateOwnerById(userId));
    }

  @Test
    void Should_ReturnClientId_When_GivenToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        Long expectedId = 1L;

        when(userServiceClient.findClientId(token)).thenReturn(1L);

      assertThat(userClient.findClientId(token)).isEqualTo(expectedId);
  }

    @Test
    void Should_ReturnEmployeeId_When_GivenToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        Long expectedId = 1L;

        when(userServiceClient.findEmployeeId(token)).thenReturn(1L);

        assertThat(userClient.findEmployeeId(token)).isEqualTo(expectedId);
    }

    @Test
    void Should_ReturnOwnerId_When_GivenToken(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJhYmNkMTIzIiwiZXhwaXJ5IjoxNjQ2NjM1NjExMzAxfQ.3Thp81rDFrKXr3WrY1MyMnNK8kKoZBX9lg-JwFznR-M";

        Long expectedId = 1L;

        when(userServiceClient.findOwner(token)).thenReturn(1L);

        assertThat(userClient.findOwnerId(token)).isEqualTo(expectedId);
    }

    @Test
    void Should_ReturnPhoneNumber_When_GivenClientId(){
        Long clientId = 1L;
        String expectedPhoneNumber = "+5878152";

        when(userServiceClient.findPhoneById(clientId)).thenReturn("+5878152");

        assertThat(userClient.findPhoneByClientId(clientId)).isEqualTo(expectedPhoneNumber);
    }

}
