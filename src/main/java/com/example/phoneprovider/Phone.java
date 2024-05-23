package com.example.phoneprovider;

import jakarta.persistence.Entity;

public class Phone {
    private int code;
    private int numfirst;
    private int numlast;
    private String provider;

    public Phone() {
    }

    public Phone(int code, int numfirst, int numlast, String provider) {
        this.code = code;
        this.numfirst = numfirst;
        this.numlast = numlast;
        this.provider = provider;
    }
    public int getCode() {
        return code;
    }

    public int getNumfirst() {
        return numfirst;
    }

    public int getNumlast() {
        return numlast;
    }

    public String getProvider() {
        return provider;
    }

}