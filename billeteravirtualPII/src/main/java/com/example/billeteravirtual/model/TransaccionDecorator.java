package com.example.billeteravirtual.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class TransaccionDecorator extends Transaccion {
    protected Transaccion transaccionDecorada;

    public TransaccionDecorator(Transaccion transaccion) {
        super(transaccion.getIdTransaccion(), transaccion.getFecha(), transaccion.getTipo(),
                transaccion.getMonto(), transaccion.getDescripcion(),
                transaccion.getCuentaOrigen(), transaccion.getCuentaDestino(),
                transaccion.getCategoria(), transaccion.getUsuario());
        this.transaccionDecorada = transaccion;
    }
}