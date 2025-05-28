package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.Transaccion;
import com.example.billeteravirtual.model.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.math.BigDecimal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class ReporteService {
    protected User usuario;
    protected LocalDate fechaInicio;
    protected LocalDate fechaFin;
    protected static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ReporteService(User usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public abstract File generarReporte() throws IOException;

    protected List<Transaccion> filtrarTransacciones() {
        return TransaccionService.getInstance()
                .obtenerTransaccionesPorUsuario(usuario)
                .stream()
                .filter(t -> !t.getFecha().toLocalDate().isBefore(fechaInicio))
                .filter(t -> !t.getFecha().toLocalDate().isAfter(fechaFin))
                .toList();
    }

    public static ReporteService crearReporte(String tipo, User usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        switch (tipo) {
            case "CSV":
                return new ReporteCSV(usuario, fechaInicio, fechaFin);
            case "PDF":
                return new ReportePDF(usuario, fechaInicio, fechaFin);
            default:
                throw new IllegalArgumentException("Tipo de reporte no soportado");
        }
    }
}

class ReporteCSV extends ReporteService {
    public ReporteCSV(User usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        super(usuario, fechaInicio, fechaFin);
    }

    @Override
    public File generarReporte() throws IOException {
        List<Transaccion> transacciones = filtrarTransacciones();
        File file = File.createTempFile("reporte_", ".csv");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Fecha,Tipo,Monto,Descripción,Categoría\n");

            for (Transaccion t : transacciones) {
                writer.write(String.format("%s,%s,%.2f,%s,%s\n",
                        t.getFecha().format(DATE_FORMATTER),
                        t.getTipo(),
                        t.getMonto(),
                        t.getDescripcion(),
                        t.getCategoria().getNombre()
                ));
            }
        }

        return file;
    }
}

class ReportePDF extends ReporteService {
    public ReportePDF(User usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        super(usuario, fechaInicio, fechaFin);
    }

    @Override
    public File generarReporte() throws IOException {
        List<Transaccion> transacciones = filtrarTransacciones();
        File file = File.createTempFile("reporte_", ".pdf");

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Configuración inicial
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);

                // Encabezado
                contentStream.showText("Reporte de Transacciones - Billetera Virtual");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Usuario: " + usuario.getNombre());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Período: " + fechaInicio.format(DATE_FORMATTER) + " a " + fechaFin.format(DATE_FORMATTER));
                contentStream.newLineAtOffset(0, -30);

                // Tabla de transacciones
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.showText("Fecha          Tipo          Monto       Descripción");
                contentStream.newLineAtOffset(0, -15);
                contentStream.setFont(PDType1Font.HELVETICA, 10);

                for (Transaccion t : transacciones) {
                    String line = String.format("%s   %s   $%.2f   %s",
                            t.getFecha().format(DATE_FORMATTER),
                            t.getTipo(),
                            t.getMonto(),
                            t.getDescripcion());
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -15);
                }

                // Total
                BigDecimal total = transacciones.stream()
                        .map(Transaccion::getMonto)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.showText("Total: $" + total);

                contentStream.endText();
            }

            document.save(file);
        }

        return file;
    }
}