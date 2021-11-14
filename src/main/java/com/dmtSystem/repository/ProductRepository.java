package com.dmtSystem.repository;

import java.util.List;

import com.dmtSystem.models.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;


public interface ProductRepository extends CrudRepository<Product, String> {

	Product findByDescription(String description);

	List<Product> findAll(Sort by);

}
