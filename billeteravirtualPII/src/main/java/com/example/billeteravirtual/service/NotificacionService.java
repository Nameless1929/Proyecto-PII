package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.Transaccion;
import com.example.billeteravirtual.model.User;

public class NotificacionService implements TransaccionObserver {
    @Override
    public void actualizar(Transaccion transaccion) {
        User usuario = transaccion.getUsuario();
        // Enviar notificación al usuario sobre la transacción
        System.out.println("Notificación enviada a " + usuario.getEmail() +
                ": Transacción de " + transaccion.getMonto() +
                " realizada con éxito.");
    }
}