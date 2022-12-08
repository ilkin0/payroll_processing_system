package com.ilkinmehdiyev.payroll_process_system.service.interfaces;

import com.ilkinmehdiyev.payroll_process_system.exception.FileParseException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void parseFile(MultipartFile file) throws FileParseException;
}
