package com.nitin.EventSphere.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequestDto {

    @NotBlank(message = "Ticket type name is required")
    private String name;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be zero or greater")
    private Double price;

    private String description;

    private Integer totalAvailable;
}// notblank notnull and all used for validation of dto , just like pydantic , these will be used with annotation @valid in controller for incoming request and that checks with dto and the current request and sees if fails or passes
// tickets and event , updated at created at etc are not here