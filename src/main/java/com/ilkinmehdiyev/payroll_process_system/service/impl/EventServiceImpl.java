package com.ilkinmehdiyev.payroll_process_system.service.impl;

import com.ilkinmehdiyev.payroll_process_system.model.dto.ProcessingRequestDto;
import com.ilkinmehdiyev.payroll_process_system.model.dto.report.EmployeeReportDto;
import com.ilkinmehdiyev.payroll_process_system.model.dto.report.GeneralReportDto;
import com.ilkinmehdiyev.payroll_process_system.model.dto.report.SalaryReportDto;
import com.ilkinmehdiyev.payroll_process_system.model.dto.report.YearlyReportDto;
import com.ilkinmehdiyev.payroll_process_system.model.enums.EventType;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.EventService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record EventServiceImpl() implements EventService {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static int totalEmployee;
    public static List<ProcessingRequestDto> totalJoined = new ArrayList<>();
    public static List<ProcessingRequestDto> totalExit = new ArrayList<>();
    public static List<SalaryReportDto> salaryReport = new ArrayList<>();
    public static List<GeneralReportDto> generalReport = new ArrayList<>();
    public static List<YearlyReportDto> yearlyReport = new ArrayList<>();
    public static List<EmployeeReportDto> employeeReport = new ArrayList<>();

    @Override
    public void processEvent(EventType type, List<String> data) {

        ProcessingRequestDto requestDto;
        switch (type) {
            case ONBOARD -> {
                requestDto = new ProcessingRequestDto(
                        data.get(0), data.get(1), data.get(2), data.get(3),
                        data.get(4), data.get(5), data.get(6),
                        LocalDate.parse(data.get(7), DATE_TIME_FORMATTER), data.get(8));

                totalJoined.add(requestDto);
                updateYearlyReport(type, requestDto);

                totalEmployee++;
            }
            case BONUS, REIMBURSEMENT -> {
                requestDto = new ProcessingRequestDto(
                        data.get(0), data.get(1), data.get(2), data.get(3), LocalDate.parse(data.get(4), DATE_TIME_FORMATTER), data.get(5));

                updateGeneralReport(requestDto);
                updateYearlyReport(type, requestDto);
                updateEmployeeReport(requestDto);
            }
            case EXIT -> {
                requestDto = new ProcessingRequestDto(
                        data.get(0), data.get(1), data.get(2), data.get(3), LocalDate.parse(data.get(4), DATE_TIME_FORMATTER), data.get(5));
                totalExit.add(requestDto);

                totalEmployee--;
                updateYearlyReport(type, requestDto);
            }
            case SALARY -> {
                requestDto = new ProcessingRequestDto(
                        data.get(0), data.get(1), data.get(2), data.get(3), LocalDate.parse(data.get(4), DATE_TIME_FORMATTER), data.get(5));

                updateSalaryReport(requestDto);
                updateGeneralReport(requestDto);
                updateYearlyReport(type, requestDto);
                updateEmployeeReport(requestDto);
            }
            default -> throw new IllegalArgumentException("Wrong Event type: " + type.name());
        }
    }

    private static void updateSalaryReport(ProcessingRequestDto requestDto) {
        Optional<SalaryReportDto> reportOptional = salaryReport.stream()
                .filter(sr -> sr.getMonth().equals(requestDto.eventDate().getMonth().toString()))
                .findAny();

        reportOptional.ifPresentOrElse((report) -> {
            report.setAmount(report.getAmount().add(new BigDecimal(requestDto.value())));
            report.setTotalEmployee(totalEmployee);

            salaryReport.set(salaryReport.lastIndexOf(report), report);
            log.info("Salary report has been updated: {}", report);
        }, () -> {
            SalaryReportDto newReport = new SalaryReportDto();
            newReport.setMonth(requestDto.eventDate().getMonth().toString());
            newReport.setAmount(newReport.getAmount().add(new BigDecimal(requestDto.value())));
            newReport.setTotalEmployee(totalEmployee);

            salaryReport.add(newReport);
            log.info("New Salary report has been added: {}", newReport);
        });
    }

    private static void updateGeneralReport(ProcessingRequestDto requestDto) {
        Optional<GeneralReportDto> generalReportOptional = generalReport.stream()
                .filter(sr -> sr.getMonth().equals(requestDto.eventDate().getMonth().toString()))
                .findAny();

        GeneralReportDto report = generalReportOptional.orElseGet(GeneralReportDto::new);
        report.setMonth(requestDto.eventDate().getMonth().toString());
        report.setTotalAmount(report.getTotalAmount().add(new BigDecimal(requestDto.value())));
        report.setTotalEmployee(totalEmployee);

        generalReport.add(report);
        log.info("New General report has been added: {}", report);
    }

    private static void updateYearlyReport(EventType type, ProcessingRequestDto requestDto) {
        YearlyReportDto report = new YearlyReportDto(type, requestDto.empId(), requestDto.eventDate(), requestDto.value());

        yearlyReport.add(report);
        log.info("New General report has been added: {}", report);
    }

    private static void updateEmployeeReport(ProcessingRequestDto requestDto) {
        Optional<EmployeeReportDto> employeeReportOptional = employeeReport.stream()
                .filter(e -> e.getEmpId().equals(requestDto.empId()))
                .findAny();

        employeeReportOptional.ifPresentOrElse((eReport) -> {
            eReport.setTotalAmount(eReport.getTotalAmount().add(new BigDecimal(requestDto.value())));

            employeeReport.set(employeeReport.lastIndexOf(eReport), eReport);
            log.info("Employee report has been updated: {}", eReport);
        }, () -> {
            EmployeeReportDto report = new EmployeeReportDto();
            report.setEmpId(requestDto.empId());
            report.setLastName(requestDto.empLName());
            report.setFirstName(requestDto.empFName());
            report.setTotalAmount(report.getTotalAmount().add(new BigDecimal(requestDto.value())));

            employeeReport.add(report);
            log.info("New Employee report has been added: {}", report);
        });
    }
}