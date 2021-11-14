package com.dmtSystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.dmtSystem.models.Client;

public interface ClientRepository extends CrudRepository<Client, String> {
	Client findByNim(String nim);
}
