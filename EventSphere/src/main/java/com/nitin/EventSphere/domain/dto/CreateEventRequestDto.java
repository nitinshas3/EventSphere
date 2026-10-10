package com.nitin.EventSphere.domain.dto;

import com.nitin.EventSphere.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {

    @NotBlank(message = "Event name is required")
    private String name;

    private LocalDateTime start;

    private LocalDateTime end;

    @NotBlank(message = "Venue information is required")
    private String venue;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    @NotNull(message = "Event status must be provided")
    private EventStatusEnum status;

    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<CreateTicketTypeRequestDto> ticketTypes;
}// this is to map the request we get to create an event to the event entity , data transfer object , this will be working in the presentation layer , by convention anything ending with dto in file name will be working in presentation(controller) layer and then send it to cretae event request that is also a dto working in service layer  , this is done so like if frontend changes the way of sending data it will be easy to handle , 2 dto of same type , presentation layer will have this validation annotations like pydantic , the other wont have coz the data will travel there through here
// organizer will be there , but attenders and staff , creted at updated at and all will not be there , coz its obvious they will be added when they volunterr or purchase ticket , ticket type required like that will be there while creating the event