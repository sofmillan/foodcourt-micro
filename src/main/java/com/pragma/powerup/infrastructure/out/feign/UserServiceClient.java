package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
@FeignClient(name="UserMicroservice",url = "${feign.client}", configuration= FeignClientConfig.class)
public interface UserServiceClient {
    @GetMapping(value= "owner/{id}")
    boolean findOwnerById(@PathVariable("id") Long id);

    @GetMapping(value = "token/{token}")
    List<String> roles(@PathVariable("token") String token);

    @GetMapping("rightOwner/{token}")
    Long findOwner(@PathVariable("token") String token);

    @GetMapping("client/{token}")
    Long findClientId(@PathVariable("token") String token);


    @GetMapping("employee/{token}")
    Long findEmployeeId(@PathVariable("token") String token);

    @GetMapping("clientPhone/{clientId}")
    String findPhoneById(@PathVariable("clientId") Long clientId);
}


