package com.ilkinmehdiyev.payroll_process_system.service.impl;

import com.ilkinmehdiyev.payroll_process_system.exception.FileParseException;
import com.ilkinmehdiyev.payroll_process_system.model.enums.EventType;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.EventService;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final EventService eventService;
    public static final String COMMA_DELIMITER = ",";
    public static final List<String> eventTypes = EventType.stringValues();

    @Override
    public void parseFile(MultipartFile file) throws FileParseException {
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.error("Cannot open the file");
            throw new FileParseException(e.getMessage());
        }

        Reader reader = new InputStreamReader(inputStream);
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);

                processEvent(values);
            }
        } catch (IOException e) {
            log.error("Cannot read the file");
            throw new FileParseException("Cannot read the file");
        }
    }

    private void processEvent(String[] values) {
        List<String> stringList = Stream.of(values)
                .map(String::strip)
                .collect(Collectors.toCollection(LinkedList::new));

        eventTypes.stream()
                .filter(stringList::contains)
                .forEach(type -> eventService.processEvent(EventType.of(type), stringList));
    }
}
