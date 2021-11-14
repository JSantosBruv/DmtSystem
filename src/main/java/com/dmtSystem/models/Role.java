package com.dmtSystem.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
public class Role implements GrantedAuthority{

	private static final String ADMIN = "ROLE_ADMIN";
	private static final String PONTOVENDA = "ROLE_PONTOVENDA";
	private static final String ALFAIATARIA = "ROLE_ALFAIATARIA";

	private static final String ROLE_EMPTY = "Permissão tem de ser preenchida.";

	@Id
	@NotEmpty(message=ROLE_EMPTY)
	private String roleFunction;

	@ManyToMany
	private List<Userworker> workers;

	public String getRoleFunction() {
		return roleFunction;
	}

	public void setRoleFunction(String roleFunction) {
		this.roleFunction = roleFunction;
	}

	@Override
	public String getAuthority() {

		return this.roleFunction;
	}

	public boolean isValid() {

		return roleFunction!= null && !roleFunction.equals("") && (roleFunction.equals(ADMIN) || roleFunction.equals(PONTOVENDA) || roleFunction.equals(ALFAIATARIA));
	}

}
