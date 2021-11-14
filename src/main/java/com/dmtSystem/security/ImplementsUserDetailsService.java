package com.dmtSystem.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.dmtSystem.models.Userworker;
import com.dmtSystem.repository.UserWorkerRepository;

@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserWorkerRepository uvr;
	
	@Override
	public UserDetails loadUserByUsername(String nim) throws UsernameNotFoundException {
		
		Userworker uv = uvr.findByNim(nim);
		
		if(uv == null)
			throw new UsernameNotFoundException("Dados incorretos.");
		
		return new User(uv.getUsername(),uv.getPassword(),true,true,true,true,uv.getAuthorities());
	}

}
