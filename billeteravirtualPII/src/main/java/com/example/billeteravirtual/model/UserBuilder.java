package com.example.billeteravirtual.model;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        this.user = new User();
    }

    public UserBuilder withId(int id) {
        user.setIdUsuario(id);
        return this;
    }

    public UserBuilder withNombre(String nombre) {
        user.setNombre(nombre);
        return this;
    }

    public UserBuilder withEmail(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder withTelefono(String telefono) {
        user.setTelefono(telefono);
        return this;
    }

    public UserBuilder withDireccion(String direccion) {
        user.setDireccion(direccion);
        return this;
    }

    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserBuilder asAdmin(boolean isAdmin) {
        user.setAdmin(isAdmin);
        return this;
    }

    public User build() {
        return user;
    }
}