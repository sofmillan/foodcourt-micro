package com.pragma.powerup;

import com.pragma.powerup.application.dto.request.CancelRequestDto;
import com.pragma.powerup.application.dto.request.MessageRequestDto;
import com.pragma.powerup.application.mapper.IMessageRequestMapper;
import com.pragma.powerup.domain.client.TwilioClientPort;
import com.pragma.powerup.domain.exception.DataNotFoundException;
import com.pragma.powerup.domain.model.CancelModel;
import com.pragma.powerup.domain.model.MessageModel;
import com.pragma.powerup.infrastructure.out.feign.TwilioClientAdapter;
import com.pragma.powerup.infrastructure.out.feign.TwilioServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TwilioClientAdapterTest {
    TwilioClientPort twilioClient;
    TwilioServiceClient twilioService;
    IMessageRequestMapper messageRequestMapper;

    @BeforeEach
    void setUp(){
        twilioService = mock(TwilioServiceClient.class);
        messageRequestMapper = mock(IMessageRequestMapper.class);
        twilioClient = new TwilioClientAdapter(twilioService, messageRequestMapper);
    }

    @Test
    void Should_SendMessage(){
        MessageModel messageModel = new MessageModel();
        messageModel.setPhoneNumber("+574855845");
        messageModel.setSecurityCode("a4c54");

        MessageRequestDto messageDto = new MessageRequestDto();
        messageDto.setPhoneNumber("+574855845");
        messageDto.setSecurityCode("a4c54");

        when(messageRequestMapper.toRequestDto(messageModel)).thenReturn(messageDto);
        when(twilioService.sendMessage(messageDto)).thenReturn(true);

        Boolean result = twilioClient.sendMessage(messageModel);

        assertTrue(result);
    }

    @Test
    void Should_Cancel(){
        CancelModel cancelModel = new CancelModel();
        cancelModel.setPhoneNumber("+574855845");


        CancelRequestDto cancelDto = new CancelRequestDto();
        cancelDto.setPhoneNumber("+574855845");

        when(messageRequestMapper.toCancelDto(cancelModel)).thenReturn(cancelDto);
        when(twilioService.cancel(cancelDto)).thenReturn(true);

        Boolean result = twilioClient.cancel(cancelModel);

        assertTrue(result);
    }

    @Test
    void Should_ThrowException_WhenCancelOrder(){
        CancelModel cancelModel = new CancelModel();
        cancelModel.setPhoneNumber("+574855845");


        CancelRequestDto cancelDto = new CancelRequestDto();
        cancelDto.setPhoneNumber("+574855845");

        when(messageRequestMapper.toCancelDto(cancelModel)).thenReturn(cancelDto);
        when(twilioService.cancel(cancelDto)).thenThrow(DataNotFoundException.class);

        assertThrows(DataNotFoundException.class,
                ()-> twilioClient.cancel(cancelModel));
    }

    @Test
    void Should_ThrowException_WhenSendMessage(){
        MessageModel messageModel = new MessageModel();
        messageModel.setPhoneNumber("+574855845");
        messageModel.setSecurityCode("a4c54");

        MessageRequestDto messageDto = new MessageRequestDto();
        messageDto.setPhoneNumber("+574855845");
        messageDto.setSecurityCode("a4c54");

        when(messageRequestMapper.toRequestDto(messageModel)).thenReturn(messageDto);
        when(twilioService.sendMessage(messageDto)).thenThrow(DataNotFoundException.class);


        assertThrows(DataNotFoundException.class,
                ()-> twilioClient.sendMessage(messageModel));
    }
}
