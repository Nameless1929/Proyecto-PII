package com.example.billeteravirtual.model;

import java.math.BigDecimal;

public class TransaccionConComision extends TransaccionDecorator {
    private BigDecimal comision;

    public TransaccionConComision(Transaccion transaccion, BigDecimal comision) {
        super(transaccion);
        this.comision = comision;
    }

    @Override
    public BigDecimal getMonto() {
        return transaccionDecorada.getMonto().add(comision);
    }

    public BigDecimal getComision() {
        return comision;
    }
}