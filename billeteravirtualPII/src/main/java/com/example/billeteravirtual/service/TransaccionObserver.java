package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.Transaccion;

public interface TransaccionObserver {
    void actualizar(Transaccion transaccion);
}