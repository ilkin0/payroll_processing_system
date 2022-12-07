package com.ilkinmehdiyev.payroll_process_system.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ProcessingRequestDto(
        String sequenceNo,
        String empId,
        String empFName,
        String empLName,
        String designation,
        String event,
        String value,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") LocalDate eventDate,
        String notes
) {

    /**
     * A specific constructor for Event types other than ONBOARD.
     */
    public ProcessingRequestDto(String sequenceNo, String empId, String event, String value, LocalDate eventDate, String notes) {
        this(sequenceNo, empId, null, null, null, event, value, eventDate, notes);
    }
}