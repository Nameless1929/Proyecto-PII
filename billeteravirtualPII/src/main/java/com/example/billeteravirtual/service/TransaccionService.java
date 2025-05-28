package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransaccionService {
    private static TransaccionService instance;
    private List<Transaccion> transacciones;
    private List<TransaccionObserver> observadores;
    private int transaccionIdCounter = 1;

    private TransaccionService() {
        this.transacciones = new ArrayList<>();
        this.observadores = new ArrayList<>();
        this.observadores.add(new NotificacionService());
    }

    public static TransaccionService getInstance() {
        if (instance == null) {
            instance = new TransaccionService();
        }
        return instance;
    }

    public void registrarTransaccion(Transaccion transaccion) {
        transacciones.add(transaccion);
        notificarObservadores(transaccion);
    }

    public void revertirTransaccion(Transaccion transaccion) {
        transacciones.remove(transaccion);
        // Lógica para revertir la transacción en las cuentas
    }

    public void agregarObservador(TransaccionObserver observador) {
        observadores.add(observador);
    }

    public void removerObservador(TransaccionObserver observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores(Transaccion transaccion) {
        observadores.forEach(o -> o.actualizar(transaccion));
    }

    public Transaccion crearDeposito(Cuenta cuentaDestino, BigDecimal monto, String descripcion, Categoria categoria) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(monto));

        Transaccion transaccion = new Transaccion(
                transaccionIdCounter++,
                LocalDateTime.now(),
                TipoTransaccion.DEPOSITO,
                monto,
                descripcion,
                null,
                cuentaDestino,
                categoria,
                cuentaDestino.getUsuario()
        );

        registrarTransaccion(transaccion);
        cuentaDestino.getUsuario().agregarTransaccion(transaccion);
        return transaccion;
    }

    public Transaccion crearRetiro(Cuenta cuentaOrigen, BigDecimal monto, String descripcion, Categoria categoria) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        if (cuentaOrigen.getSaldo().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(monto));

        Transaccion transaccion = new Transaccion(
                transaccionIdCounter++,
                LocalDateTime.now(),
                TipoTransaccion.RETIRO,
                monto,
                descripcion,
                cuentaOrigen,
                null,
                categoria,
                cuentaOrigen.getUsuario()
        );

        registrarTransaccion(transaccion);
        cuentaOrigen.getUsuario().agregarTransaccion(transaccion);
        return transaccion;
    }

    public Transaccion crearTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, BigDecimal monto, String descripcion, Categoria categoria) {
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }

        if (cuentaOrigen.getSaldo().compareTo(monto) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo().subtract(monto));
        cuentaDestino.setSaldo(cuentaDestino.getSaldo().add(monto));

        Transaccion transaccion = new Transaccion(
                transaccionIdCounter++,
                LocalDateTime.now(),
                TipoTransaccion.TRANSFERENCIA,
                monto,
                descripcion,
                cuentaOrigen,
                cuentaDestino,
                categoria,
                cuentaOrigen.getUsuario()
        );

        registrarTransaccion(transaccion);
        cuentaOrigen.getUsuario().agregarTransaccion(transaccion);
        return transaccion;
    }

    public List<Transaccion> obtenerTransaccionesPorUsuario(User usuario) {
        List<Transaccion> transaccionesUsuario = new ArrayList<>();
        for (Transaccion transaccion : transacciones) {
            if (transaccion.getUsuario().getIdUsuario() == usuario.getIdUsuario()) {
                transaccionesUsuario.add(transaccion);
            }
        }
        return transaccionesUsuario;
    }

    public List<Transaccion> obtenerTodasTransacciones() {
        return new ArrayList<>(transacciones);
    }
}