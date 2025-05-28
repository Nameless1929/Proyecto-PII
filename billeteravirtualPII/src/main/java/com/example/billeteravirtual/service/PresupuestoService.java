package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PresupuestoService {
    private static PresupuestoService instance;
    private List<Presupuesto> presupuestos;
    private int presupuestoIdCounter = 1;

    private PresupuestoService() {
        this.presupuestos = new ArrayList<>();
    }

    public static PresupuestoService getInstance() {
        if (instance == null) {
            instance = new PresupuestoService();
        }
        return instance;
    }

    public Presupuesto crearPresupuesto(String nombre, BigDecimal montoTotal, Categoria categoria, User usuario) {
        Presupuesto nuevoPresupuesto = new Presupuesto(
                presupuestoIdCounter++,
                nombre,
                montoTotal,
                BigDecimal.ZERO,
                categoria,
                usuario
        );

        presupuestos.add(nuevoPresupuesto);
        usuario.agregarPresupuesto(nuevoPresupuesto);
        return nuevoPresupuesto;
    }

    public boolean actualizarPresupuesto(int idPresupuesto, String nombre, BigDecimal montoTotal, User usuario) {
        Presupuesto presupuesto = presupuestos.stream()
                .filter(p -> p.getIdPresupuesto() == idPresupuesto && p.getUsuario().getIdUsuario() == usuario.getIdUsuario())
                .findFirst()
                .orElse(null);

        if (presupuesto != null) {
            presupuesto.setNombre(nombre);
            presupuesto.setMontoTotal(montoTotal);
            return true;
        }
        return false;
    }

    public boolean eliminarPresupuesto(int idPresupuesto, User usuario) {
        Presupuesto presupuesto = presupuestos.stream()
                .filter(p -> p.getIdPresupuesto() == idPresupuesto && p.getUsuario().getIdUsuario() == usuario.getIdUsuario())
                .findFirst()
                .orElse(null);

        if (presupuesto != null) {
            presupuestos.remove(presupuesto);
            usuario.getPresupuestos().remove(presupuesto);
            return true;
        }
        return false;
    }

    public List<Presupuesto> obtenerPresupuestosPorUsuario(User usuario) {
        List<Presupuesto> presupuestosUsuario = new ArrayList<>();
        for (Presupuesto presupuesto : presupuestos) {
            if (presupuesto.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                presupuestosUsuario.add(presupuesto);
            }
        }
        return presupuestosUsuario;
    }
}