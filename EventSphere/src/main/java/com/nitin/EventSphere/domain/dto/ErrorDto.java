package com.nitin.EventSphere.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

    private String error;
} // this dto just used to throw error message while handling exception in exception handling controller