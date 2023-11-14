package com.api.pdv.controller;

import com.api.pdv.model.Product;
import com.api.pdv.service.ProductService;
import com.api.pdv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(
            value = "/inserir",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> insertProduct(@RequestBody Product product) {
        try {
            Product heExist = this.productService.findProductById(product.getId());
            if (heExist != null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Product/Exist");
            } else {
                this.productService.insertProduct(product);
                return ResponseEntity.status(HttpStatus.CREATED).body("Product/Inserted");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(
            value = "/list"
    )
    public ResponseEntity<?> getProducts() {
        try {
            List<Product> products = this.productService.getProducts();
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(products);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(
            value = "/{id}"
    )
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        try {
            Product product = this.productService.findProductById(id);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product/Not/Found");
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(
            value = "/update/{id}"
    )
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody Product product) {
        try {
            Product productExist = this.productService.findProductById(id);
            if (productExist == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product/Not/Found");
            }
            this.productService.updateProduct(product, id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product/Updated");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping( value = "/delete/{id}" )
    public ResponseEntity<String> deleteProductBy(@PathVariable String id) {
        try {
            Product product = this.productService.findProductById(id);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("ProductOrUser/Not/Exist");
            } else {
                this.productService.deleteProduct(id);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product/Deleted");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
