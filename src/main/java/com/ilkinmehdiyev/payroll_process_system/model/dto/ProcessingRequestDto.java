package com.ilkinmehdiyev.payroll_process_system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ProcessingRequestDto(
        String sequenceNo,
        String empId,
        String empFName,
        String empLName,
        String designation,
        String event, // TODO refactor
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyy") LocalDate eventDate,
        String notes
) {
}