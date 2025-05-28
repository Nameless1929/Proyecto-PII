package com.example.billeteravirtual.model;

import java.math.BigDecimal;

public class Cuenta {
    private int idCuenta;
    private String nombreBanco;
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private BigDecimal saldo;
    private User usuario;

    public Cuenta(int idCuenta, String nombreBanco, String numeroCuenta, TipoCuenta tipoCuenta, BigDecimal saldo, User usuario) {
        this.idCuenta = idCuenta;
        this.nombreBanco = nombreBanco;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    // Getters y Setters
    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }
    public String getNombreBanco() { return nombreBanco; }
    public void setNombreBanco(String nombreBanco) { this.nombreBanco = nombreBanco; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public TipoCuenta getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(TipoCuenta tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }

    @Override
    public String toString() {
        return numeroCuenta;
    }

}