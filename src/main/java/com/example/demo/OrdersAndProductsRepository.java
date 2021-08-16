package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface OrdersAndProductsRepository extends CrudRepository<OrdersAndProducts, Long> {
}
