package com.example.billeteravirtual.model;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class User {
    private int idUsuario;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String password;
    private BigDecimal saldoTotal;
    private List<Cuenta> cuentas;
    private List<Transaccion> transacciones;
    private List<Presupuesto> presupuestos;
    private boolean isAdmin;

    public User() {
        this.cuentas = new ArrayList<>();
        this.transacciones = new ArrayList<>();
        this.presupuestos = new ArrayList<>();
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public BigDecimal getSaldoTotal() { return saldoTotal; }
    public void setSaldoTotal(BigDecimal saldoTotal) { this.saldoTotal = saldoTotal; }
    public List<Cuenta> getCuentas() { return cuentas; }
    public void setCuentas(List<Cuenta> cuentas) { this.cuentas = cuentas; }
    public List<Transaccion> getTransacciones() { return transacciones; }
    public void setTransacciones(List<Transaccion> transacciones) { this.transacciones = transacciones; }
    public List<Presupuesto> getPresupuestos() { return presupuestos; }
    public void setPresupuestos(List<Presupuesto> presupuestos) { this.presupuestos = presupuestos; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public void agregarCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
    }

    public void agregarTransaccion(Transaccion transaccion) {
        this.transacciones.add(transaccion);
    }

    public void agregarPresupuesto(Presupuesto presupuesto) {
        this.presupuestos.add(presupuesto);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}