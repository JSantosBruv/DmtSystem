package com.dmtSystem.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

@Entity
public class Month {

	@Id
	private int id;

	private String month;

	private int counter;

	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy("dateLimite ASC")
	private List<OrderFlow> orders;

	public void addProd() {
		counter++;
	}

	public void remProd() {

		counter--;
		if (counter < 0)
			counter = 0;
	}

	public int getCounter() {
		return counter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<OrderFlow> getOrders() {
		return orders;
	}

	public void addOrder(OrderFlow e) {
		orders.add(e);
	}

	public void remOrder(OrderFlow e) {

		orders.remove(e);
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
