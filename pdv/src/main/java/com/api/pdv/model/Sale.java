package com.api.pdv.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Table(name = "vendas")
public class Sale {

    @Id
    @Column(name = "id")
    private final UUID id;

    private Float value;

    private String payment;

    private final Instant date;

    private UUID user_id;

    public Sale() {
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

    public Sale(UUID user_id, Float value, String payment) {
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
        this.user_id = user_id;
        this.value = value;
        this.payment = payment;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUser() {
        return user_id;
    }

    public Float getValue() {
        return value;
    }

    public String getPayment() { return payment; }

    public Instant getDate() {
        return date;
    }

    public void setUser(UUID id) {
        this.user_id = id;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
