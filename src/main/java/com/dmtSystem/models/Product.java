package com.dmtSystem.models;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Product {

	private static final String NNA_EMPTY = "NNA não pode ser vazio.";
	private static final String NOME_EMPTY = "Nome não pode ser vazio.";

	@Id
	@NotEmpty(message = NNA_EMPTY)
	private String nna;
	@NotEmpty(message = NOME_EMPTY)
	private String description;

	public String getNna() {
		return nna;
	}

	public void setNna(String nna) {
		this.nna = nna;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
