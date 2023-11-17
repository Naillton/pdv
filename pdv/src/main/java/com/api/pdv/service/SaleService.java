package com.api.pdv.service;

import com.api.pdv.model.Sale;
import com.api.pdv.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public void insertSale(Sale sale) {
        this.saleRepository.save(sale);
    }

    public List<Sale> getSales() {
        return this.saleRepository.findAll();
    }

    public void deleteSale(UUID id) {
        this.saleRepository.deleteById(id);
    }

    public void deleteAllSale() {
        this.saleRepository.deleteAll();
    }
}
