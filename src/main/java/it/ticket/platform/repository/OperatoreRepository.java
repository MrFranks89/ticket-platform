package it.ticket.platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ticket.platform.model.Operatore;

public interface OperatoreRepository extends JpaRepository<Operatore, Long> {

	Optional<Operatore> findByUsername(String username);
	Optional<Operatore> findById(Long id);
	public List<Operatore> findByDisponibile(boolean disponibile);
	
}
