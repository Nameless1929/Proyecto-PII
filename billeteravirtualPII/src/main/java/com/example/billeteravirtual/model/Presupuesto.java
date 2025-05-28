package com.example.billeteravirtual.model;

import java.math.BigDecimal;

public class Presupuesto {
    private int idPresupuesto;
    private String nombre;
    private BigDecimal montoTotal;
    private BigDecimal montoGastado;
    private Categoria categoria;
    private User usuario;

    public Presupuesto(int idPresupuesto, String nombre, BigDecimal montoTotal,
                       BigDecimal montoGastado, Categoria categoria, User usuario) {
        this.idPresupuesto = idPresupuesto;
        this.nombre = nombre;
        this.montoTotal = montoTotal;
        this.montoGastado = montoGastado;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    // Getters y Setters
    public int getIdPresupuesto() { return idPresupuesto; }
    public void setIdPresupuesto(int idPresupuesto) { this.idPresupuesto = idPresupuesto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }
    public BigDecimal getMontoGastado() { return montoGastado; }
    public void setMontoGastado(BigDecimal montoGastado) { this.montoGastado = montoGastado; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
}