package com.ilkinmehdiyev.payroll_process_system.service.impl;

import com.ilkinmehdiyev.payroll_process_system.exception.FileParseException;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.EventService;
import com.ilkinmehdiyev.payroll_process_system.service.interfaces.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;

import java.io.IOException;
import org.springframework.mock.web.MockMultipartFile;

public class FileServiceImplTest {

    private MockMultipartFile file;
    private FileService fileService;
    @Mock
    private EventService eventService;

    @BeforeEach
    public void init() {
        file = spy(new MockMultipartFile("file", new byte[]{1, 2, 3}));

        fileService = spy(new FileServiceImpl(eventService));
    }

    @Test
    @DisplayName("Parse file")
    public void parse_file_when_ok() throws FileParseException {
        doNothing().when(fileService).parseFile(file);
    }

    @Test
    @DisplayName("parse_file_when_cannot_open")
    public void parse_file_when_cannot_open() throws IOException {
        doThrow(FileParseException.class).when(file).getInputStream();

        assertThrows(FileParseException.class, () -> fileService.parseFile(file));
    }
}