package com.ilkinmehdiyev.payroll_process_system.model.dto.report;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SalaryReportDto {
    private String month;
    private BigDecimal amount = BigDecimal.ZERO;
    private int totalEmployee;
}