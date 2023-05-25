package com.dev.exchange_rate.domain;

public enum Currency {
    IRR("rial"),
    USD("dollar"),
    EUR("euro");

    private final String name;
    Currency(String name) {
        this.name = name;
    }

    private String toName(){
        return name;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                '}';
    }
}
