package com.api.pdv.dto;

import com.api.pdv.model.Product;

public record ProductDTO(String id, String name, Float preco, Integer quantidade) {

    public Product toEntity() {
        return new Product(id, name, preco, quantidade);
    }
}
