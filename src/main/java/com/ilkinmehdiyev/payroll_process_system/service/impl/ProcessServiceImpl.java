package com.ilkinmehdiyev.payroll_process_system.service.impl;

import com.ilkinmehdiyev.payroll_process_system.exception.FileNotSupportedException;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.ProcessService;
import com.ilkinmehdiyev.payroll_process_system.util.CommonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public record ProcessServiceImpl() implements ProcessService {

    public static final String COMMA_DELIMITER = ",";

    @Override
    public void processFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (Objects.isNull(originalFilename))
            throw new IllegalArgumentException();

        boolean isExtensionOk = CommonUtil.isFileExtensionOk(originalFilename);
        log.info("\nOriginalName: {}\nExtensionStatus: {}", originalFilename, isExtensionOk);

        if (!isExtensionOk)
            throw new FileNotSupportedException();

        List<List<String>> parsedData = parseFile(file);
    }

    private List<List<String>> parseFile(MultipartFile file) {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Reader reader = new InputStreamReader(inputStream);
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                data.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("File data: {}", data);
        return data;
    }
}
