package com.example.billeteravirtual.model;

import java.util.ArrayList;
import java.util.List;

public class CategoriaComposite extends Categoria {
    private List<Categoria> subcategorias;

    public CategoriaComposite(int idCategoria, String nombre, String descripcion) {
        super(idCategoria, nombre, descripcion);
        this.subcategorias = new ArrayList<>();
    }

    public void agregarSubcategoria(Categoria categoria) {
        subcategorias.add(categoria);
    }

    public void removerSubcategoria(Categoria categoria) {
        subcategorias.remove(categoria);
    }

    public List<Categoria> getSubcategorias() {
        return subcategorias;
    }
}