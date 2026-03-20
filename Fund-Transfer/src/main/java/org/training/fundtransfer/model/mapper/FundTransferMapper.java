package org.training.fundtransfer.model.mapper;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.training.fundtransfer.model.dto.FundTransferDto;
import org.training.fundtransfer.model.entity.FundTransfer;

import java.util.Objects;

@Component
public class FundTransferMapper extends BaseMapper<FundTransfer, FundTransferDto> {

    private final Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();

    /**
     * Converts a FundTransferDto object to a FundTransfer entity.
     *
     * @param  dto   the FundTransferDto object to be converted
     * @param  args  additional arguments (not used in this function)
     * @return       the converted FundTransfer entity
     */
    @Override
    public FundTransfer convertToEntity(FundTransferDto dto, Object... args) {

        FundTransfer fundTransfer = new FundTransfer();
        if(!Objects.isNull(dto)){
            BeanUtils.copyProperties(dto, fundTransfer);
        }
        return fundTransfer;
    }

    /**
     * Converts the given FundTransfer entity to a FundTransferDto object.
     *
     * @param  entity  the FundTransfer entity to be converted
     * @param  args    additional arguments (optional)
     * @return         the converted FundTransferDto object
     */
    @Override
    public FundTransferDto convertToDto(FundTransfer entity, Object... args) {

        FundTransferDto fundTransferDto = new FundTransferDto();
        if(!Objects.isNull(entity)){
            BeanUtils.copyProperties(entity, fundTransferDto);
        }
        return fundTransferDto;
    }

    public Message toMessage(FundTransferDto dto) {
        return messageConverter.toMessage(dto, new MessageProperties());
    }

    public FundTransferDto fromMessage(Message message) {
        return (FundTransferDto) messageConverter.fromMessage(message);
    }
}