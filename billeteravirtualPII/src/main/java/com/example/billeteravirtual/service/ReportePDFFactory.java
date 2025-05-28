package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.User;
import java.time.LocalDate;

public class ReportePDFFactory implements ReporteFactory {
    @Override
    public ReporteService crearReporte(User usuario, LocalDate fechaInicio, LocalDate fechaFin) {
        return new ReportePDF(usuario, fechaInicio, fechaFin);
    }
}