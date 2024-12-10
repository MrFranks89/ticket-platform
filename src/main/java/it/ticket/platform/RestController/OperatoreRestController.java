package it.ticket.platform.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/operatori")
public class OperatoreRestController {
	@Autowired
	private OperatoreRepository operatoreRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@GetMapping("/{id}")
	public Operatore getOperatoreById(@PathVariable Long id) {
		return operatoreRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Operatore non trovato con id: " + id));
	}

	@GetMapping("/{id}/tickets")
	public List<Ticket> getTicketsByOperatore(@PathVariable Long id) {
		Operatore operatore = operatoreRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Operatore non trovato con id: " + id));

		List<Ticket> allTickets = ticketRepository.findAll();

		List<Ticket> assignedTickets = new ArrayList<>();

		for (Ticket ticket : allTickets) {
			if (ticket.getOperatore() != null && ticket.getOperatore().getId().equals(operatore.getId())) {
				assignedTickets.add(ticket);
			}
		}

		return assignedTickets;
	}

	@GetMapping("/{operatoreid}/tickets/{ticketId}")
	public Ticket getTicketById(@PathVariable Long operatoreId, @PathVariable Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new EntityNotFoundException("Ticket non trovato con id: " + ticketId));

		if (ticket.getOperatore() != null && ticket.getOperatore().getId().equals(operatoreId)) {
			return ticket;
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"Questo ticket non è assegnato a questo operatore.");
		}
	}
	
	

}
