package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.application.dto.request.CancelRequestDto;
import com.pragma.powerup.application.dto.request.MessageRequestDto;
import com.pragma.powerup.infrastructure.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="TwilioMicroservice",url = "${feign.twilio}", configuration= FeignClientConfig.class)
public interface TwilioServiceClient {
    @PostMapping("/message")
    Boolean sendMessage(MessageRequestDto messageRequestDto);

    @PostMapping("/cancel")
    Boolean cancel(CancelRequestDto cancelRequestDto);
}
