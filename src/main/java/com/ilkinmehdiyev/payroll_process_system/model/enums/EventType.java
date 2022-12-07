package com.ilkinmehdiyev.payroll_process_system.model.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum EventType {
    ONBOARD,
    SALARY,
    BONUS,
    EXIT,
    REIMBURSEMENT;


    public static List<String> stringValues() {
        return Stream.of(EventType.values())
                .map(EventType::name)
                .collect(Collectors.toList());
    }

    public static EventType of(String name) {
        return Stream.of(EventType.values())
                .filter(t -> t.name().equals(name))
                .findFirst()
                .orElseThrow(() -> {
                    log.info("Cannot find EventType with {} value", name);
                    throw new IllegalArgumentException();
                });
    }
}
