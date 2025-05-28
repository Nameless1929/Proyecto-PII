package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.*;
import com.example.billeteravirtual.service.AuthService;
import com.example.billeteravirtual.service.TransaccionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class UserDashboardController {
    @FXML private Label lblWelcome;
    @FXML private Label lblSaldoTotal;
    @FXML private ListView<String> lvCuentas;
    @FXML private TableView<Transaccion> tvTransacciones;
    @FXML private PieChart pcGastosPorCategoria;

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    public void initialize() {
        refreshDashboard();
    }

    public void refreshDashboard() {
        User usuario = AuthService.getInstance().getCurrentUser();
        if (usuario != null) {
            lblWelcome.setText("Bienvenido, " + usuario.getNombre());
            actualizarSaldoTotal();
            cargarCuentas();
            cargarTransacciones();
            cargarGraficoGastos();
        }
    }

    private void actualizarSaldoTotal() {
        User usuario = AuthService.getInstance().getCurrentUser();
        if (usuario != null && usuario.getCuentas() != null) {
            BigDecimal saldoTotal = usuario.getCuentas().stream()
                    .map(Cuenta::getSaldo)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            lblSaldoTotal.setText(String.format("$%.2f", saldoTotal));
        }
    }

    private void cargarCuentas() {
        User usuario = AuthService.getInstance().getCurrentUser();
        if (usuario != null && usuario.getCuentas() != null) {
            lvCuentas.setItems(FXCollections.observableArrayList(
                    usuario.getCuentas().stream()
                            .map(c -> String.format("%s - %s: $%.2f",
                                    c.getNombreBanco(),
                                    c.getTipoCuenta(),
                                    c.getSaldo()))
                            .toList()
            ));
        }
    }

    private void cargarTransacciones() {
        User usuario = AuthService.getInstance().getCurrentUser();
        if (usuario != null) {
            tvTransacciones.setItems(FXCollections.observableArrayList(
                    TransaccionService.getInstance().obtenerTransaccionesPorUsuario(usuario)
            ));

            if (tvTransacciones.getColumns().isEmpty()) {
                TableColumn<Transaccion, LocalDateTime> fechaCol = new TableColumn<>("Fecha");
                fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));

                TableColumn<Transaccion, TipoTransaccion> tipoCol = new TableColumn<>("Tipo");
                tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));

                TableColumn<Transaccion, BigDecimal> montoCol = new TableColumn<>("Monto");
                montoCol.setCellValueFactory(new PropertyValueFactory<>("monto"));

                TableColumn<Transaccion, String> descCol = new TableColumn<>("Descripción");
                descCol.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

                tvTransacciones.getColumns().addAll(fechaCol, tipoCol, montoCol, descCol);
            }
        }
    }

    private void cargarGraficoGastos() {
        User usuario = AuthService.getInstance().getCurrentUser();
        if (usuario != null) {
            Map<String, Double> gastosPorCategoria = new HashMap<>();

            usuario.getTransacciones().stream()
                    .filter(t -> t.getTipo() == TipoTransaccion.RETIRO)
                    .forEach(t -> {
                        String categoria = t.getCategoria().getNombre();
                        double monto = t.getMonto().doubleValue();
                        gastosPorCategoria.merge(categoria, monto, Double::sum);
                    });

            pcGastosPorCategoria.getData().clear();
            gastosPorCategoria.forEach((categoria, monto) ->
                    pcGastosPorCategoria.getData().add(new PieChart.Data(categoria, monto))
            );
        }
    }

    @FXML
    private void handleLogout() {
        AuthService.getInstance().logout();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAddCuenta() {
        showFormModal("/fxml/AddCuenta.fxml", "Agregar Nueva Cuenta");
    }

    @FXML
    private void goToTransaccion() {
        showFormModal("/fxml/Transaccion.fxml", "Registrar Transacción");
    }

    @FXML
    private void goToPresupuestos() {
        showFormModal("/fxml/Presupuestos.fxml", "Administrar Presupuestos");
    }

    @FXML
    private void goToReportes() {
        showFormModal("/fxml/Reportes.fxml", "Generar Reportes");
    }

    private void showFormModal(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage formStage = new Stage();
            formStage.setTitle(title);
            formStage.initModality(Modality.WINDOW_MODAL);
            formStage.initOwner(primaryStage);
            formStage.setScene(new Scene(root));

            formStage.setOnHidden(e -> refreshDashboard());
            formStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}