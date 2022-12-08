package com.ilkinmehdiyev.payroll_process_system.model.dto.report;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeReportDto {
    private String empId;
    private String firstName;
    private String lastName;
    private BigDecimal totalAmount = BigDecimal.ZERO;
}
