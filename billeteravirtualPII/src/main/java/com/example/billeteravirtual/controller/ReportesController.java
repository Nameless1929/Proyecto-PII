package com.example.billeteravirtual.controller;

import com.example.billeteravirtual.model.User;
import com.example.billeteravirtual.service.AuthService;
import com.example.billeteravirtual.service.ReporteService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;

public class ReportesController {
    @FXML private DatePicker dpFechaInicio;
    @FXML private DatePicker dpFechaFin;
    @FXML private ComboBox<String> cbTipoReporte;

    @FXML
    public void initialize() {
        cbTipoReporte.getItems().addAll("PDF", "CSV");
    }

    @FXML
    private void handleGenerarReporte() {
        LocalDate fechaInicio = dpFechaInicio.getValue();
        LocalDate fechaFin = dpFechaFin.getValue();
        String tipoReporte = cbTipoReporte.getValue();

        if (fechaInicio == null || fechaFin == null || tipoReporte == null) {
            showAlert("Error", "Por favor complete todos los campos");
            return;
        }

        if (fechaInicio.isAfter(fechaFin)) {
            showAlert("Error", "La fecha de inicio debe ser anterior a la fecha fin");
            return;
        }

        User usuario = AuthService.getInstance().getCurrentUser();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccione donde guardar el reporte");
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            try {
                ReporteService reporte = ReporteService.crearReporte(
                        tipoReporte,
                        usuario,
                        fechaInicio,
                        fechaFin
                );

                File reporteFile = reporte.generarReporte();
                File destino = new File(selectedDirectory, reporteFile.getName());

                if (reporteFile.renameTo(destino)) {
                    showAlert("Éxito", "Reporte generado correctamente en: " + destino.getAbsolutePath());
                    // Cerrar la ventana automáticamente después de generar el reporte
                    Stage stage = (Stage) dpFechaInicio.getScene().getWindow();
                    stage.close();
                } else {
                    showAlert("Error", "No se pudo mover el archivo al directorio seleccionado");
                }
            } catch (Exception e) {
                showAlert("Error", "No se pudo generar el reporte: " + e.getMessage());
                e.printStackTrace();
            }
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