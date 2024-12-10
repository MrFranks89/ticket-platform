package it.ticket.platform.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ticket.platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	 List<Ticket> findByOperatoreId(Long operatoreId);

	List<Ticket> findByTitoloContaining(String keyword);

}
