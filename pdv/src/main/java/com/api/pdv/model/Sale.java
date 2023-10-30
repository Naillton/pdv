package com.api.pdv.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "vendas")
public class Sale {

    @Id
    @Column(name = "id")
    private final UUID id;

    public Sale() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
