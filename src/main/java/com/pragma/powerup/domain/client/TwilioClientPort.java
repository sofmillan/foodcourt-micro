package com.pragma.powerup.domain.client;

import com.pragma.powerup.domain.model.CancelModel;
import com.pragma.powerup.domain.model.MessageModel;

public interface TwilioClientPort {
    Boolean sendMessage(MessageModel messageModel);

    Boolean cancel(CancelModel cancelModel);
}

