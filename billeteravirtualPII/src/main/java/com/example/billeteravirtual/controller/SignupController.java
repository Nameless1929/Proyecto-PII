package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.User;
import com.example.billeteravirtual.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {
    @FXML private TextField tfNombre;
    @FXML private TextField tfEmail;
    @FXML private TextField tfTelefono;
    @FXML private TextField tfDireccion;
    @FXML private PasswordField pfPassword;
    @FXML private PasswordField pfConfirmPassword;

    @FXML
    private void handleSignup() {
        String nombre = tfNombre.getText();
        String email = tfEmail.getText();
        String telefono = tfTelefono.getText();
        String direccion = tfDireccion.getText();
        String password = pfPassword.getText();
        String confirmPassword = pfConfirmPassword.getText();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Por favor complete todos los campos");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Las contraseñas no coinciden");
            return;
        }

        User nuevoUsuario = new User();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setTelefono(telefono);
        nuevoUsuario.setDireccion(direccion);
        nuevoUsuario.setPassword(password); // En una aplicación real, usaríamos hash

        if (AuthService.getInstance().register(nuevoUsuario)) {
            showAlert("Éxito", "Registro exitoso");
            goToLogin();
        } else {
            showAlert("Error", "El correo electrónico ya está registrado");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage) tfNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "No se pudo cargar la pantalla de inicio de sesión");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}