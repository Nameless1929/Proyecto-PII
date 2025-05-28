package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.Cuenta;
import com.example.billeteravirtual.model.TipoCuenta;
import com.example.billeteravirtual.model.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CuentaService {
    private static CuentaService instance;
    private List<Cuenta> cuentas;
    private int cuentaIdCounter = 1;

    private CuentaService() {
        this.cuentas = new ArrayList<>();
    }

    public static CuentaService getInstance() {
        if (instance == null) {
            instance = new CuentaService();
        }
        return instance;
    }

    public Cuenta crearCuenta(String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta, User usuario) {
        Cuenta nuevaCuenta = new Cuenta(
                cuentaIdCounter++,
                nombreBanco,
                numeroCuenta,
                tipoCuenta,
                BigDecimal.ZERO,
                usuario
        );
        cuentas.add(nuevaCuenta);
        usuario.agregarCuenta(nuevaCuenta);
        return nuevaCuenta;
    }

    public List<Cuenta> obtenerCuentasPorUsuario(User usuario) {
        List<Cuenta> cuentasUsuario = new ArrayList<>();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                cuentasUsuario.add(cuenta);
            }
        }
        return cuentasUsuario;
    }

    public boolean eliminarCuenta(int idCuenta, User usuario) {
        Cuenta cuenta = cuentas.stream()
                .filter(c -> c.getIdCuenta() == idCuenta && c.getUsuario().getIdUsuario() == usuario.getIdUsuario())
                .findFirst()
                .orElse(null);

        if (cuenta != null) {
            cuentas.remove(cuenta);
            usuario.getCuentas().remove(cuenta);
            return true;
        }
        return false;
    }
}