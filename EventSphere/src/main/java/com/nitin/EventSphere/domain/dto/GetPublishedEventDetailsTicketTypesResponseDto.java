package com.nitin.EventSphere.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPublishedEventDetailsTicketTypesResponseDto {

    private UUID id;
    private String name;
    private Double price;
    private String description;
}
// used in getpublishedevent() to get details about a singile event which is published to purchase tickets etc used with getpublishedeventdetailsrespoendto
