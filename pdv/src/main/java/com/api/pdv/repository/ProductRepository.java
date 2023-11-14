package com.api.pdv.repository;

import com.api.pdv.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE produtos SET id = ?, name = ?, preco = ?, quantidade = ? WHERE id = ?", nativeQuery = true)
    void updateProduct(String id, String name, Float preco, Integer quantidade, String oldId);
}