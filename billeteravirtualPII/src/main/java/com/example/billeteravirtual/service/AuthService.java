package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.User;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class AuthService {
    private static AuthService instance;
    private List<User> users;
    private User currentUser;

    private AuthService() {
        this.users = new ArrayList<>();
        // Usuario admin por defecto
        User admin = new User();
        admin.setIdUsuario(1);
        admin.setNombre("Admin");
        admin.setEmail("admin");
        admin.setTelefono("1234567890");
        admin.setDireccion("Admin Address");
        admin.setPassword("admin");
        admin.setSaldoTotal(BigDecimal.ZERO);
        admin.setAdmin(true);
        users.add(admin);
    }

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean login(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                // En una aplicación real, usaríamos hash de contraseñas
                if (user.getPassword().equals(password)) {
                    currentUser = user;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean register(User newUser) {
        if (users.stream().anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
            return false;
        }
        newUser.setIdUsuario(users.size() + 1);
        users.add(newUser);
        currentUser = newUser;
        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}