package com.example.billeteravirtual.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {
    private int idTransaccion;
    private LocalDateTime fecha;
    private TipoTransaccion tipo;
    private BigDecimal monto;
    private String descripcion;
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private Categoria categoria;
    private User usuario;

    public Transaccion(int idTransaccion, LocalDateTime fecha, TipoTransaccion tipo, BigDecimal monto,
                       String descripcion, Cuenta cuentaOrigen, Cuenta cuentaDestino,
                       Categoria categoria, User usuario) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    // Getters y Setters
    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    // Método auxiliar útil para las estadísticas y vistas
    public String getNombreCategoria() {
        return (categoria != null) ? categoria.getNombre() : "Sin Categoría";
    }
}
