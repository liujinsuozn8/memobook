package com.ljs.learn.base.annotation;

import org.springframework.stereotype.Component;

@Component
public class Address {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
