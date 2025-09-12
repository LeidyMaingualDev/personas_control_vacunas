package com.vacunas.personasvulnerables.services;

import com.vacunas.personasvulnerables.entities.users.UserRole;
import com.vacunas.personasvulnerables.entities.users.dtos.RegisterDTO;
import com.vacunas.personasvulnerables.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.vacunas.personasvulnerables.entities.users.User;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(email);
        if(user == null)
        {
            throw new UsernameNotFoundException("Usuario no encontrado con el email: " + email);
        }

        return user;
    }

    public User registerUser(RegisterDTO data)
    {
        // Validar si ya existe
        if(userRepository.findByEmail(data.getEmail()) != null)
        {
            throw new RuntimeException("El email ya está registrado");
        }

        // Encriptar la contraseña
        String encryptedPassword = passwordEncoder.encode(data.getPassword());


        // Crear usuario
        User newUser = new User();
        newUser.setEmail(data.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(UserRole.valueOf(data.getRole()));

        // Guardar en la BD
        return userRepository.save(newUser);



    }
}
