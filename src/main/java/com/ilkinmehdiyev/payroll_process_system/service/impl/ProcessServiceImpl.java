package com.ilkinmehdiyev.payroll_process_system.service.impl;

import com.ilkinmehdiyev.payroll_process_system.exception.FileNotSupportedException;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.FileService;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.ProcessService;
import com.ilkinmehdiyev.payroll_process_system.util.CommonUtil;
import static com.ilkinmehdiyev.payroll_process_system.service.impl.EventServiceImpl.employeeReport;
import static com.ilkinmehdiyev.payroll_process_system.service.impl.EventServiceImpl.generalReport;
import static com.ilkinmehdiyev.payroll_process_system.service.impl.EventServiceImpl.salaryReport;
import static com.ilkinmehdiyev.payroll_process_system.service.impl.EventServiceImpl.totalExit;
import static com.ilkinmehdiyev.payroll_process_system.service.impl.EventServiceImpl.totalJoined;
import static com.ilkinmehdiyev.payroll_process_system.service.impl.EventServiceImpl.yearlyReport;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public record ProcessServiceImpl(FileService fileService) implements ProcessService {

    @Override
    public void processFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (Objects.isNull(originalFilename))
            throw new IllegalStateException("File original name is not found");

        boolean isExtensionOk = CommonUtil.isFileExtensionOk(originalFilename);

        if (!isExtensionOk)
            throw new FileNotSupportedException("File type is not supported!");

        fileService.parseFile(file);

        log.info("Total Joined Report: {}", totalJoined);
        log.info("Total Exit Report: {}", totalExit);
        log.info("Total Salary Report: {}", salaryReport);
        log.info("General Report: {}", generalReport);
        log.info("Yearly Report: {}", yearlyReport);
        log.info("Employee Report: {}", employeeReport);
    }
}