package com.api.pdv.controller;

import com.api.pdv.dto.SaleDTO;
import com.api.pdv.model.Sale;
import com.api.pdv.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping(
            value = "/inserir",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> insertSale(@RequestBody SaleDTO saleDTO) {
        try {
            this.saleService.insertSale(saleDTO.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body("Product/Inserted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/sales")
    public ResponseEntity<List<Sale>> getSales() {
        try {
            List<Sale> sales = this.saleService.getSales();
            return ResponseEntity.status(HttpStatus.CREATED).body(sales);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable UUID id) {
        try {
            this.saleService.deleteSale(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sale/Deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteAllSale() {
        try {
            this.saleService.deleteAllSale();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sales/Deleted");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
