package com.dmtSystem.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.dmtSystem.models.Userworker;

public interface UserWorkerRepository  extends CrudRepository<Userworker,String> {

	Userworker findByNim(String nim);
	Userworker findByName(String name);
	List<Userworker> findAll(Sort by);
}
