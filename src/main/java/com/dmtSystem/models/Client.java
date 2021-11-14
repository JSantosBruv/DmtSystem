package com.dmtSystem.models;

import java.util.LinkedList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Client {

	private static final String NR_DIGITS_NIM = "NIM tem de ter exatamente 8 digitos";
	private static final String NR_DIGITS_PHONE = "Telefone tem de ter 9 digitos";
	private static final String NAME_SIZE = "Nome tem de ter pelo menos 2 letras";
	private static final String RANK_SIZE = "Posto tem de ter pelo menos 3 letras";
	private static final String DEP_SIZE = "Unidade tem de ter pelo menos 3 letras";
	private static final String WEAPON_SIZE = "Unidade tem de ter pelo menos 3 letras";
	private static final String EMAIL = "Email inválido";
	private static final String EMPTY_INPUT = "Todos os campos têm de ser preenchidos :)";
	
	@Id
	@Pattern(regexp = "([0-9]{8})", message = NR_DIGITS_NIM)
	private String nim;
	
	@NotEmpty(message= EMPTY_INPUT)
	@Size(min = 2, message = NAME_SIZE)
	private String name;
	@NotEmpty(message= EMPTY_INPUT)
	@Size(min = 3, message = RANK_SIZE)
	private String ranked;
	@NotEmpty(message= EMPTY_INPUT)
	@Size(min = 3, message = DEP_SIZE)
	private String department;
	@Pattern(regexp = "(^$|[0-9]{9})", message = NR_DIGITS_PHONE)
	private String cellNumber;
	@Pattern(regexp="(^$|^[^@ ]+@[^@ ]+\\.[^@ ]+$)",message=EMAIL)
	private String email;
	@NotEmpty(message= EMPTY_INPUT)
	@Size(min = 3, message = WEAPON_SIZE)
	private String weapon;

	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderFlow> encs = new LinkedList<OrderFlow>();

	public String getNim() {

		return nim;
	}

	public List<OrderFlow> getEnc() {
		return encs;
	}

	public void addEnc(OrderFlow enc) {
		encs.add(enc);
	}
	public void remEnc(OrderFlow enc) {
		encs.remove(enc);
	}

	public void setNim(String nim) {
		this.nim = nim;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRanked() {
		return ranked;
	}

	public void setRanked(String rank) {
		this.ranked = rank;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

}
