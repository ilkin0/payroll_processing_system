package com.ilkinmehdiyev.payroll_process_system.controller;

import com.ilkinmehdiyev.payroll_process_system.service.interfaces.ProcessService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/process")
public class ProcessingController {
    private final ProcessService processService;

    @PostMapping
    public ResponseEntity<Void> processFile(@RequestParam(value = "file") MultipartFile file) {
        processService.processFile(file);
        return ResponseEntity.ok().build();
    }
}