package com.aidharbor.Entity.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContactState {
    NEW("1"),
    HOLD("2"),
    CHECK("3");
    private final String value;
}
