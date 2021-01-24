package com.devheon.netty.common.constant;

public enum ModeType {
    RUN("run"),
    DEBUG("debug"),
    UNKNOWN("unknown");

    private String value;
    private ModeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
