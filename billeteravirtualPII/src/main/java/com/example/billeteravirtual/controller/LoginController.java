package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.service.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPassword;

    @FXML
    private void handleLogin() {
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Por favor complete todos los campos");
            return;
        }

        if (AuthService.getInstance().login(email, password)) {
            try {
                Parent root;
                if (AuthService.getInstance().getCurrentUser().isAdmin()) {
                    root = FXMLLoader.load(getClass().getResource("/fxml/AdminDashboard.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("/fxml/UserDashboard.fxml"));
                }

                Stage stage = (Stage) tfEmail.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                showAlert("Error", "No se pudo cargar el dashboard");
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Credenciales incorrectas");
        }
    }

    @FXML
    private void goToSignup() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Signup.fxml"));
            Stage stage = (Stage) tfEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showAlert("Error", "No se pudo cargar la pantalla de registro");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}