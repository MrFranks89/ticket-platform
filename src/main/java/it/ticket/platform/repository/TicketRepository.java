package it.ticket.platform.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ticket.platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	

	List<Ticket> findByTitoloContaining(String keyword);
	List<Ticket> findByStato(String stato);
    List<Ticket> findByDataCreazioneBetween(LocalDateTime start, LocalDateTime end);
	List<Ticket> findByOperatoreId(Long id);

}
