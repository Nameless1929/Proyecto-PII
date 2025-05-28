package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.*;
import com.example.billeteravirtual.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

public class PresupuestosController {
    @FXML private TableView<Presupuesto> tvPresupuestos;
    @FXML private TextField tfNombre;
    @FXML private TextField tfMonto;
    @FXML private ComboBox<Categoria> cbCategoria;

    @FXML
    public void initialize() {
        cargarPresupuestos();
        cargarCategorias();
    }

    private void cargarPresupuestos() {
        User usuario = AuthService.getInstance().getCurrentUser();
        tvPresupuestos.setItems(FXCollections.observableArrayList(
                PresupuestoService.getInstance().obtenerPresupuestosPorUsuario(usuario)
        ));
    }

    private void cargarCategorias() {
        cbCategoria.setItems(FXCollections.observableArrayList(
                CategoriaService.getInstance().obtenerTodasCategorias()
        ));
    }

    @FXML
    private void handleAgregarPresupuesto() {
        String nombre = tfNombre.getText();
        String montoText = tfMonto.getText();
        Categoria categoria = cbCategoria.getValue();

        if (nombre.isEmpty() || montoText.isEmpty() || categoria == null) {
            showAlert("Error", "Por favor complete todos los campos");
            return;
        }

        try {
            BigDecimal monto = new BigDecimal(montoText);
            if (monto.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert("Error", "El monto debe ser positivo");
                return;
            }

            PresupuestoService.getInstance().crearPresupuesto(
                    nombre,
                    monto,
                    categoria,
                    AuthService.getInstance().getCurrentUser()
            );

            showAlert("Éxito", "Presupuesto creado correctamente");
            cargarPresupuestos();
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error", "El monto debe ser un número válido");
        }
    }

    @FXML
    private void handleEliminarPresupuesto() {
        Presupuesto seleccionado = tvPresupuestos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            showAlert("Error", "Por favor seleccione un presupuesto");
            return;
        }

        if (PresupuestoService.getInstance().eliminarPresupuesto(
                seleccionado.getIdPresupuesto(),
                AuthService.getInstance().getCurrentUser()
        )) {
            showAlert("Éxito", "Presupuesto eliminado correctamente");
            cargarPresupuestos();
        } else {
            showAlert("Error", "No se pudo eliminar el presupuesto");
        }
    }

    private void clearFields() {
        tfNombre.clear();
        tfMonto.clear();
        cbCategoria.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
  }