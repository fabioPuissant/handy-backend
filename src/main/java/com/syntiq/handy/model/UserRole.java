package com.syntiq.handy.model;



public enum UserRole {
    ADMINISTRATOR("Administrator"), CUSTOMER("Customer"), PROFESSIONAL("Professional");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
