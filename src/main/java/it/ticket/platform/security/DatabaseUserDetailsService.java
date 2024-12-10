package it.ticket.platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.ticket.platform.model.Operatore;
import it.ticket.platform.repository.OperatoreRepository;

public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
	private OperatoreRepository operatoreRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Operatore> operatoreByUsername = operatoreRepo.findByUsername(username);
		
		if(operatoreByUsername.isPresent()) {
			return new DatabaseUserDetails(operatoreByUsername.get());
		} else {
			throw new UsernameNotFoundException("Username not found");
		}
		
	}
}
