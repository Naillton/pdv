package com.api.pdv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "produtos")
public class Product {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "preco")
    private float preco;

    public Product(String id, String name, Float preco) {
        this.id = id;
        this.name = name;
        this.preco = preco;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPreco() {
        return preco;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
