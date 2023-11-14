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
    private Float preco;

    @Column(name = "quantidade")
    private Integer quantidade;

    public Product() {}

    public Product(String id, String name, Float preco, Integer quantidade) {
        this.id = id;
        this.name = name;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Float getPreco() {
        return preco;
    }

    public Integer getQuantidade() { return quantidade; }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}
