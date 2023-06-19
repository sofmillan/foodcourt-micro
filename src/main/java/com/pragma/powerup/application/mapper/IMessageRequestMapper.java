package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.CancelRequestDto;
import com.pragma.powerup.application.dto.request.MessageRequestDto;
import com.pragma.powerup.domain.model.CancelModel;
import com.pragma.powerup.domain.model.MessageModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IMessageRequestMapper {
    MessageRequestDto toRequestDto(MessageModel messageModel);

    MessageModel toModel(MessageRequestDto messageRequestDto);

    CancelModel toCancelModel(CancelRequestDto cancelRequestDto);

    CancelRequestDto toCancelDto(CancelModel cancelModel);
}
