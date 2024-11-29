package com.jts.multi.datasource.db2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jts.multi.datasource.db2.model.Product;

//@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
