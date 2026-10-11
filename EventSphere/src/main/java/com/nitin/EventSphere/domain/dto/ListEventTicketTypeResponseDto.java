package com.nitin.EventSphere.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListEventTicketTypeResponseDto {

    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;

}
// this dto will be used in listeventresponsedto , in that we will need to return ticket types in the response of listing events , so in that this dto will be used
