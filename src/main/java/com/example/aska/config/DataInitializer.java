package com.example.aska.config;

import com.example.aska.model.Role;
import com.example.aska.model.Usuario;
import com.example.aska.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByNombreUsuario("admin") == null) {
            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setEmailUsuario("admin@aska.com");
            admin.setContrasenaUsuario(passwordEncoder.encode("123456"));
            admin.setRole(Role.ROLE_ADMIN);
            usuarioRepository.save(admin);
        }

        List<Usuario> usersWithoutRole = usuarioRepository.findByRoleIsNull();
        for (Usuario user : usersWithoutRole) {
            user.setRole(Role.ROLE_USER);
            usuarioRepository.save(user);
        }
    }
}
