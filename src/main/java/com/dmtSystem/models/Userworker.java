package com.dmtSystem.models;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.JoinColumn;
@SuppressWarnings("serial")
@Entity
public class Userworker implements UserDetails {
	
	private static final String NIM_EMPTY = "UserId não pode ser vazio.";
	private static final String NAME_EMPTY = "Nome não pode ser vazio.";
	private static final String PASS_EMPTY = "Password não pode ser vazio.";
	@Id
	@NotEmpty(message=NIM_EMPTY)
	private String nim;
	@NotEmpty(message=NAME_EMPTY)
	private String name;
	@NotEmpty(message=PASS_EMPTY)
	private String pass;

	@ManyToMany
	@JoinTable( name = "user_roles", joinColumns = @JoinColumn(
	            name = "userworker_id", referencedColumnName = "nim"), 
	            inverseJoinColumns = @JoinColumn(
	            name = "role_id", referencedColumnName = "roleFunction"))
	
	private List<Role> roles = new ArrayList<Role>(1);

	public List<Role> getRoles() {
		return roles;
	}
	
	public String getRole() {
		return roles.get(0).getRoleFunction().replaceAll("ROLE_", "");
		
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getNim() {
		return nim;
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

	public String getPass() {
		
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
	
		return this.pass;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.nim;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
