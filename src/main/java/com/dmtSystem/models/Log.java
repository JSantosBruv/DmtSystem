package com.dmtSystem.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Log {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int coId;

	private LocalDateTime date;
	private String encCode;
	private String description;
	private String name;

	public Log() {

		this.date = LocalDateTime.now();

	}
	
	public int getCoId() {
		return coId;
	}

	public void setCoId(int coId) {
		this.coId = coId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getEncCode() {
		return encCode;
	}

	public void setEncCode(String encCode) {
		this.encCode = encCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String nim) {
		this.name = nim;
	}

}
