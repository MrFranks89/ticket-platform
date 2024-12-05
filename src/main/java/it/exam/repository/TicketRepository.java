package it.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.exam.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

	public List<Ticket> findByTitoloContaining(String titolo);
}
