package com.ilkinmehdiyev.payroll_process_system.model.dto.report;

import com.ilkinmehdiyev.payroll_process_system.model.enums.EventType;

import java.time.LocalDate;

public record YearlyReportDto(EventType eventType, String empId, LocalDate eventDate, String eventValue) {
}


