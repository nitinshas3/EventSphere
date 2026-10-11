package com.nitin.EventSphere.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEventDetailsTicketTypesResponseDto {

    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
// this will be used in geteventfororganizer method() like getting a single event , full details of single event and this will be used in geteventdetails response dto , coz that will have ticket types so this dto will be used there