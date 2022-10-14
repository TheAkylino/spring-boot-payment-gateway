package com.example.exceptions.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericExceptionResponse {
    private String processedAt;
    private String message;
    private String details;
}
