package com.dev.exchange_rate.domain;

public enum Rate {
    IRR("rial"),
    USD("dollar"),
    EURO("euro");

    private final String name;
    Rate(String name) {
        this.name = name;
    }

    private String toName(){
        return name;
    }
}
