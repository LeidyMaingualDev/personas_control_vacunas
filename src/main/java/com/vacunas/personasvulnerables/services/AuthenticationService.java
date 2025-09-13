package com.vacunas.personasvulnerables.services;

import com.vacunas.personasvulnerables.entities.users.User;
import com.vacunas.personasvulnerables.entities.users.dtos.RegisterDTO;
import com.vacunas.personasvulnerables.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Cargar usuario por email (Spring Security)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (User) userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }
        return user;
    }

    // Registro de usuario
    public User registerUser(RegisterDTO data) {
        // Validar si ya existe
        if (userRepository.findByEmail(data.email()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encriptar la contraseña
        String encryptedPassword = passwordEncoder.encode(data.password());

        // Crear usuario
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(data.role());

        // Guardar en la BD
        return userRepository.save(newUser);
    }
}
