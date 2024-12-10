package it.ticket.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ticket.platform.model.Admin;
import it.ticket.platform.model.Ticket;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByUsername(String username);
	

}
