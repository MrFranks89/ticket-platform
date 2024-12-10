package it.ticket.platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import it.ticket.platform.model.Admin;
import it.ticket.platform.model.Operatore;
import it.ticket.platform.repository.AdminRepository;
import it.ticket.platform.repository.OperatoreRepository;

public class DatabaseUserDetailsService implements UserDetailsService {

	@Autowired
    private AdminRepository adminRepository;
	
	@Autowired
	private OperatoreRepository operatoreRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return new DatabaseUserDetails(admin.get());
        }

        Optional<Operatore> operatore = operatoreRepository.findByUsername(username);
        if (operatore.isPresent()) {
            return new DatabaseUserDetails(operatore.get());
        }

        throw new UsernameNotFoundException("Utente non trovato con username: " + username);
    }
}
