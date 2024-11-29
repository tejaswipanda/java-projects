package com.jts.multi.datasource.db3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jts.multi.datasource.db3.model.Order;

//@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {

}
