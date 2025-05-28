package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.Transaccion;
import com.example.billeteravirtual.model.User;
import com.example.billeteravirtual.service.AuthService;
import com.example.billeteravirtual.service.TransaccionService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty; // Import añadido
import com.example.billeteravirtual.model.TipoTransaccion; // Import añadido
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class AdminDashboardController {
    @FXML private Label lblWelcome;
    @FXML private TableView<User> tvUsuarios;
    @FXML private TableView<Transaccion> tvTransacciones;

    @FXML
    public void initialize() {
        lblWelcome.setText("Panel de Administración - Bienvenido, " +
                AuthService.getInstance().getCurrentUser().getNombre());

        cargarUsuarios();
        cargarTodasTransacciones();
    }

    private void cargarUsuarios() {
        tvUsuarios.setItems(FXCollections.observableArrayList(
                AuthService.getInstance().getAllUsers()
        ));

        if (tvUsuarios.getColumns().isEmpty()) {
            TableColumn<User, String> nombreCol = new TableColumn<>("Nombre");
            nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));

            TableColumn<User, String> emailCol = new TableColumn<>("Email");
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

            TableColumn<User, BigDecimal> saldoCol = new TableColumn<>("Saldo Total");
            saldoCol.setCellValueFactory(new PropertyValueFactory<>("saldoTotal"));

            tvUsuarios.getColumns().addAll(nombreCol, emailCol, saldoCol);
        }
    }

    private void cargarTodasTransacciones() {
        tvTransacciones.setItems(FXCollections.observableArrayList(
                TransaccionService.getInstance().obtenerTodasTransacciones()
        ));

        if (tvTransacciones.getColumns().isEmpty()) {
            TableColumn<Transaccion, LocalDateTime> fechaCol = new TableColumn<>("Fecha");
            fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));

            TableColumn<Transaccion, String> usuarioCol = new TableColumn<>("Usuario");
            usuarioCol.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getUsuario().getNombre()));

            TableColumn<Transaccion, TipoTransaccion> tipoCol = new TableColumn<>("Tipo");
            tipoCol.setCellValueFactory(new PropertyValueFactory<>("tipo"));

            TableColumn<Transaccion, BigDecimal> montoCol = new TableColumn<>("Monto");
            montoCol.setCellValueFactory(new PropertyValueFactory<>("monto"));

            tvTransacciones.getColumns().addAll(fechaCol, usuarioCol, tipoCol, montoCol);
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
    private void goToEstadisticas() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Estadisticas.fxml"));
            Stage stage = (Stage) lblWelcome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}