package com.api.pdv.service;

import com.api.pdv.model.Product;
import com.api.pdv.repository.ProductRepository;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void insertProduct(Product product) {
        this.productRepository.save(product);
    }

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public Product findProductById(String id) {
        return this.productRepository.findById(id).stream().findFirst().orElse(null);
    }

    public void updateProduct(Product product, String id) {
        this.productRepository.updateProduct(
                product.getId(),
                product.getName(),
                product.getPreco(),
                product.getQuantidade(),
                id
        );
    }

    public void deleteProduct(String id) {
        this.productRepository.deleteById(id);
    }
}
