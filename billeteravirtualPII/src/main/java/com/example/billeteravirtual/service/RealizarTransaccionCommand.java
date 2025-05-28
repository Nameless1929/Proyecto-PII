package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.Transaccion;
import com.example.billeteravirtual.service.TransaccionService;

public class RealizarTransaccionCommand implements OperacionCommand {
    private Transaccion transaccion;
    private TransaccionService transaccionService;

    public RealizarTransaccionCommand(Transaccion transaccion, TransaccionService transaccionService) {
        this.transaccion = transaccion;
        this.transaccionService = transaccionService;
    }

    @Override
    public void ejecutar() {
        transaccionService.registrarTransaccion(transaccion);
    }

    @Override
    public void deshacer() {
        transaccionService.revertirTransaccion(transaccion);
    }
}