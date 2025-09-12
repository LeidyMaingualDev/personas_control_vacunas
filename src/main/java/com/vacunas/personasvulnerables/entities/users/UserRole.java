package com.vacunas.personasvulnerables.entities.users;

public enum UserRole {
    DOCTOR("doctor"),
    ASISTENTE("asistente");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
