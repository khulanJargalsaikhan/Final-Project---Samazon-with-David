package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByName(String name);
}
