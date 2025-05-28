package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.Categoria;
import com.example.billeteravirtual.model.Transaccion;
import com.example.billeteravirtual.model.User;
import com.example.billeteravirtual.service.AuthService;
import com.example.billeteravirtual.service.TransaccionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstadisticasController {

    @FXML private PieChart pieChart;
    @FXML private BarChart<String, Number> transaccionesChart;
    @FXML private BarChart<String, Number> saldoChart;
    @FXML private Button btnVolver;

    @FXML
    public void initialize() {
        cargarGastosPorCategoria();
        cargarUsuariosConMasTransacciones();
        cargarSaldoPromedioPorUsuario();
    }

    private void cargarGastosPorCategoria() {
        Map<String, BigDecimal> gastosPorCategoria = new HashMap<>();

        for (Transaccion t : TransaccionService.getInstance().obtenerTodasTransacciones()) {
            if (t.getTipo().toString().equalsIgnoreCase("EGRESO")) {
                Categoria categoria = t.getCategoria();
                gastosPorCategoria.merge(String.valueOf(categoria), t.getMonto(), BigDecimal::add);
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, BigDecimal> entry : gastosPorCategoria.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue().doubleValue()));
        }

        pieChart.setData(pieChartData);
    }

    private void cargarUsuariosConMasTransacciones() {
        Map<String, Integer> transaccionesPorUsuario = new HashMap<>();

        for (Transaccion t : TransaccionService.getInstance().obtenerTodasTransacciones()) {
            String nombre = t.getUsuario().getNombre();
            transaccionesPorUsuario.merge(nombre, 1, Integer::sum);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Transacciones");

        for (Map.Entry<String, Integer> entry : transaccionesPorUsuario.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        transaccionesChart.getData().clear();
        transaccionesChart.getData().add(series);
    }

    private void cargarSaldoPromedioPorUsuario() {
        List<User> usuarios = AuthService.getInstance().getAllUsers();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Saldo");

        for (User user : usuarios) {
            BigDecimal saldo = user.getSaldoTotal();
            if (saldo == null) {
                saldo = BigDecimal.ZERO;
            }
            series.getData().add(new XYChart.Data<>(user.getNombre(), saldo.doubleValue()));
        }

        saldoChart.getData().clear();
        saldoChart.getData().add(series);
    }


    @FXML
    private void volverADashboard(ActionEvent event) {
        try {
            URL resource = getClass().getResource("/fxml/AdminDashboard.fxml");
            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
