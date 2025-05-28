package com.example.billeteravirtual.service;

import com.example.billeteravirtual.model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    private static CategoriaService instance;
    private List<Categoria> categorias;
    private int categoriaIdCounter = 1;

    private CategoriaService() {
        this.categorias = new ArrayList<>();
        // Categorías por defecto
        categorias.add(new Categoria(categoriaIdCounter++, "Comida", "Gastos en alimentación"));
        categorias.add(new Categoria(categoriaIdCounter++, "Transporte", "Gastos en transporte"));
        categorias.add(new Categoria(categoriaIdCounter++, "Entretenimiento", "Gastos en diversión"));
        categorias.add(new Categoria(categoriaIdCounter++, "Educación", "Gastos en educación"));
        categorias.add(new Categoria(categoriaIdCounter++, "Salud", "Gastos en salud"));
    }

    public static CategoriaService getInstance() {
        if (instance == null) {
            instance = new CategoriaService();
        }
        return instance;
    }

    public Categoria crearCategoria(String nombre, String descripcion) {
        Categoria nuevaCategoria = new Categoria(
                categoriaIdCounter++,
                nombre,
                descripcion
        );
        categorias.add(nuevaCategoria);
        return nuevaCategoria;
    }

    public List<Categoria> obtenerTodasCategorias() {
        return new ArrayList<>(categorias);
    }

    public Categoria obtenerCategoriaPorId(int id) {
        return categorias.stream()
                .filter(c -> c.getIdCategoria() == id)
                .findFirst()
                .orElse(null);
    }
}