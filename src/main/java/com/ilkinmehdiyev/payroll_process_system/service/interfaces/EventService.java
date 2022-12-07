package com.ilkinmehdiyev.payroll_process_system.service.interfaces;

import com.ilkinmehdiyev.payroll_process_system.model.enums.EventType;

import java.util.List;

public interface EventService {
    void processEvent(EventType type, List<String> data);
}