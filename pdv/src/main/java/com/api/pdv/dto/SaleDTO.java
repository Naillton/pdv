package com.api.pdv.dto;

import com.api.pdv.model.Sale;

import java.util.UUID;

public record SaleDTO(UUID user_id, Float value, String payment) {

    public Sale toEntity() {
        return new Sale(user_id, value, payment);
    }
}
