package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.User;
import java.time.LocalDate;

public interface ReporteFactory {
    ReporteService crearReporte(User usuario, LocalDate fechaInicio, LocalDate fechaFin);
}

