package com.vacunas.personasvulnerables.entities.users.dtos;

import com.vacunas.personasvulnerables.entities.users.UserRole;

public record RegisterDTO(String name, String email, String password, UserRole role) {
}
