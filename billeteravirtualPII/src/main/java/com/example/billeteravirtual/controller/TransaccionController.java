package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.*;
import com.example.billeteravirtual.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class TransaccionController {
    @FXML private ComboBox<TipoTransaccion> cbTipoTransaccion;
    @FXML private ComboBox<Cuenta> cbCuentaOrigen;
    @FXML private ComboBox<Cuenta> cbCuentaDestino;
    @FXML private TextField tfMonto;
    @FXML private TextField tfDescripcion;
    @FXML private ComboBox<Categoria> cbCategoria;

    @FXML
    public void initialize() {
        User usuario = AuthService.getInstance().getCurrentUser();

        cbTipoTransaccion.setItems(FXCollections.observableArrayList(TipoTransaccion.values()));
        cbTipoTransaccion.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            updateCuentaDestinoVisibility(newVal);
        });

        cbCuentaOrigen.setItems(FXCollections.observableArrayList(
                CuentaService.getInstance().obtenerCuentasPorUsuario(usuario)
        ));

        cbCategoria.setItems(FXCollections.observableArrayList(
                CategoriaService.getInstance().obtenerTodasCategorias()
        ));
    }

    private void updateCuentaDestinoVisibility(TipoTransaccion tipo) {
        cbCuentaDestino.setVisible(tipo == TipoTransaccion.TRANSFERENCIA);
        if (tipo != TipoTransaccion.TRANSFERENCIA) {
            cbCuentaDestino.setValue(null);
        } else {
            User usuario = AuthService.getInstance().getCurrentUser();
            cbCuentaDestino.setItems(FXCollections.observableArrayList(
                    CuentaService.getInstance().obtenerCuentasPorUsuario(usuario)
                            .stream()
                            .filter(c -> c != cbCuentaOrigen.getValue())
                            .toList()
            ));
        }
    }

    @FXML
    private void handleRealizarTransaccion() {
        TipoTransaccion tipo = cbTipoTransaccion.getValue();
        Cuenta cuentaOrigen = cbCuentaOrigen.getValue();
        Cuenta cuentaDestino = cbCuentaDestino.getValue();
        String montoText = tfMonto.getText();
        String descripcion = tfDescripcion.getText();
        Categoria categoria = cbCategoria.getValue();

        if (tipo == null || cuentaOrigen == null || montoText.isEmpty() || categoria == null) {
            showAlert("Error", "Por favor complete todos los campos obligatorios");
            return;
        }

        if (tipo == TipoTransaccion.TRANSFERENCIA && cuentaDestino == null) {
            showAlert("Error", "Por favor seleccione una cuenta destino");
            return;
        }

        try {
            BigDecimal monto = new BigDecimal(montoText);
            if (monto.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert("Error", "El monto debe ser positivo");
                return;
            }

            Transaccion transaccion;
            switch (tipo) {
                case DEPOSITO:
                    transaccion = TransaccionService.getInstance()
                            .crearDeposito(cuentaOrigen, monto, descripcion, categoria);
                    break;
                case RETIRO:
                    transaccion = TransaccionService.getInstance()
                            .crearRetiro(cuentaOrigen, monto, descripcion, categoria);
                    break;
                case TRANSFERENCIA:
                    transaccion = TransaccionService.getInstance()
                            .crearTransferencia(cuentaOrigen, cuentaDestino, monto, descripcion, categoria);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de transacción no soportado");
            }

            showAlert("Éxito", "Transacción realizada correctamente");
            // Cerrar la ventana automáticamente después de guardar
            Stage stage = (Stage) cbTipoTransaccion.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            showAlert("Error", "El monto debe ser un número válido");
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
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