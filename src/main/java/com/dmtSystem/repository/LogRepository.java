package com.dmtSystem.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.dmtSystem.models.Log;

public interface LogRepository extends CrudRepository<Log, String>{
	
	List<Log> findAllByEncCode(String encCode,Sort sort);
}
