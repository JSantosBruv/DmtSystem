package com.dmtSystem.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;

import com.dmtSystem.models.OrderFlow;

import org.springframework.data.domain.Sort;

public interface OrderRepository extends CrudRepository<OrderFlow, String> {

	OrderFlow findByEncCode(String encCode);
	List<OrderFlow> findAll(Sort sort);
	List<OrderFlow> findAllByState(String state);
	
	
}
