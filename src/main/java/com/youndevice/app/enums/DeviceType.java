package com.youndevice.app.enums;

public enum DeviceType {

    TYPE_0("Type 0"),
    TYPE_1("Type 1"),
    TYPE_2("Type 2"),
    TYPE_3("Type 3"),
    TYPE_4("Type 4"),
    TYPE_5("Type 5"),
    TYPE_6("Type 6");

    private String name;

    private DeviceType(String value) {
        this.name = value;
    }
}
