package it.ticket.platform.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.ticket.platform.model.Nota;
import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.NotaRepository;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class NotaService {

	@Autowired
	private NotaRepository notaRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private OperatoreRepository operatoreRepository;

	public Nota addNotaToTicket(Long ticketId, String testo) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new EntityNotFoundException("Ticket non trovato con id: " + ticketId));

		Operatore autore = (Operatore) ticket.getOperatore();
		
		  if (autore == null) {
	            throw new EntityNotFoundException("Operatore non trovato per il ticket con id: " + ticketId);
	        }

		Nota nota = new Nota();
		nota.setTesto(testo);
		nota.setAutore(autore);
		nota.setDataCreazione(LocalDateTime.now());
		nota.setTicket(ticket);

		return notaRepository.save(nota);
	}

}