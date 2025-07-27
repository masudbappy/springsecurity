package com.masudbappy.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Holiday {

    private final String day;
    private final String reason;
    private final Type type;

    public Holiday(String day, String reason, Type type) {
        this.day = day;
        this.reason = reason;
        this.type = type;
    }

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
