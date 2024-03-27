package com.aidharbor.Entity.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventCategory {
    NOTICE("1"),
    NEWS("2");
    private final String value;
}
