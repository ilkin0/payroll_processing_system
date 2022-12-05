package com.ilkinmehdiyev.payroll_process_system.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface ProcessService {
    void processFile(MultipartFile file);
}
