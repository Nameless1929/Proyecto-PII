package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.TipoCuenta;
import com.example.billeteravirtual.service.AuthService;
import com.example.billeteravirtual.service.CuentaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddCuentaController {
    @FXML private TextField tfNombreBanco;
    @FXML private TextField tfNumeroCuenta;
    @FXML private ComboBox<TipoCuenta> cbTipoCuenta;

    @FXML
    public void initialize() {
        cbTipoCuenta.setItems(FXCollections.observableArrayList(TipoCuenta.values()));
    }

    @FXML
    private void handleAddCuenta() {
        String nombreBanco = tfNombreBanco.getText();
        String numeroCuenta = tfNumeroCuenta.getText();
        TipoCuenta tipoCuenta = cbTipoCuenta.getValue();

        if (nombreBanco.isEmpty() || numeroCuenta.isEmpty() || tipoCuenta == null) {
            showAlert("Error", "Por favor complete todos los campos");
            return;
        }

        CuentaService.getInstance().crearCuenta(
                nombreBanco,
                numeroCuenta,
                tipoCuenta,
                AuthService.getInstance().getCurrentUser()
        );

        showAlert("Éxito", "Cuenta agregada correctamente");
        clearFields();
    }

    private void clearFields() {
        tfNombreBanco.clear();
        tfNumeroCuenta.clear();
        cbTipoCuenta.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}