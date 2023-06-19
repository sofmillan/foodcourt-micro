package com.pragma.powerup.infrastructure.out.feign;

import com.pragma.powerup.domain.client.TwilioClientPort;
import com.pragma.powerup.domain.model.CancelModel;
import com.pragma.powerup.domain.model.MessageModel;
import com.pragma.powerup.application.mapper.IMessageRequestMapper;
import com.pragma.powerup.domain.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TwilioClientAdapter implements TwilioClientPort {

    private final TwilioServiceClient twilioServiceClient;
    private final IMessageRequestMapper messageRequestMapper;

    @Override
    public Boolean sendMessage(MessageModel messageModel) {
        try{
            return twilioServiceClient.sendMessage(messageRequestMapper.toRequestDto(messageModel));
        }catch(Exception e){
            throw new DataNotFoundException("Could not send twilio message");
        }
    }

    @Override
    public Boolean cancel(CancelModel cancelModel) {
        try{
            return twilioServiceClient.cancel(messageRequestMapper.toCancelDto(cancelModel));
        }catch (Exception e){
            throw new DataNotFoundException("Could not send twilio message");

        }
    }
}

