package com.dmtSystem.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.dmtSystem.models.Costureira;

public interface CostureiraRepository extends CrudRepository<Costureira, Integer> {
	
	Costureira findByName(String name);

	Iterable<Costureira> findAll(Sort by);
}
