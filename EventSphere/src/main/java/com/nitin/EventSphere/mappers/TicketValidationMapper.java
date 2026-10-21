package com.nitin.EventSphere.mappers;

import com.nitin.EventSphere.domain.dto.TicketValidationResponseDto;
import com.nitin.EventSphere.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {

    @Mapping(target = "ticketId", source = "ticket.id")
    TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation ticketValidation);

}
// used to map ticketvalidatoin object to ticketvalidation response dto

//@Mapping(target = "ticketId", source = "ticket.id")check this
