package com.dmtSystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.dmtSystem.models.Month;

public interface MonthRepository extends CrudRepository<Month, Integer> {
	Month findById(int id);

}
