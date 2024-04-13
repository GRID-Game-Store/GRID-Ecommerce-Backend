package com.khomsi.backend.main.game.model.entity;

public enum PermitAge {
    AGE_0("0"),
    AGE_3("3"),
    AGE_7("7"),
    AGE_12("12"),
    AGE_16("16"),
    AGE_18("18"),
    AGE_EXCLAMATION("!");

    private final String value;

    PermitAge(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
