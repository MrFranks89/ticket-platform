package it.ticket.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ticket.platform.model.Nota;
import it.ticket.platform.model.Ticket;

public interface NotaRepository extends JpaRepository<Nota, Long> {

	List<Nota> findByTicketId(Long ticketId);
}
