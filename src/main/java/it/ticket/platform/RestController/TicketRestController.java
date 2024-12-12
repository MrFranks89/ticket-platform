package it.ticket.platform.RestController;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import it.ticket.platform.model.Admin;
import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.AdminRepository;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import it.ticket.platform.service.TicketService;
import it.ticket.platform.model.TicketAssignmentRequest;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private OperatoreRepository operatoreRepository;

	private static final Logger logger = LoggerFactory.getLogger(TicketRestController.class);

	@GetMapping("/")
	public ResponseEntity<List<Ticket>> index(@RequestParam(name = "keyword", required = false) String keyword) {
		try {
			if (keyword != null && !keyword.isBlank()) {
				List<Ticket> tickets = ticketRepository.findByTitoloContaining(keyword);
				return new ResponseEntity<>(tickets, HttpStatus.OK);
			} else {
				List<Ticket> tickets = ticketRepository.findAll();
				return ResponseEntity.ok(tickets);
			}
		} catch (Exception e) {
			logger.error("Errore durante il recupero delle pizze", e);
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/tickets/assign")
	public Ticket assignTicketToOperator(@Valid @RequestBody TicketAssignmentRequest request) {

		Admin admin = adminRepository.findById(request.getAdminId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Admin non trovato con id: " + request.getAdminId()));


		Ticket ticket = ticketRepository.findById(request.getTicketId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Ticket non trovato con id: " + request.getTicketId()));


		Operatore operatore = operatoreRepository.findById(request.getOperatoreId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Operatore non trovato con id: " + request.getOperatoreId()));


		ticket.setOperatore(operatore);
		ticket.setDataModifica(LocalDateTime.now()); 

		return ticketRepository.save(ticket);
	}


	@PostMapping
	public Ticket createTicket(@RequestBody Ticket ticket) {
		return ticketService.createTicket(ticket);
	}


	@PutMapping("/{id}")
	public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket) {
		return ticketService.updateTicket(id, updatedTicket);
	}


	@PutMapping("/{id}/stato")
	public Ticket updateStato(@PathVariable Long id, @RequestParam String nuovoStato) {
		return ticketService.updateStato(id, nuovoStato);
	}

	@DeleteMapping("/{id}")
	public void deleteTicket(@PathVariable Long id) {
		ticketService.deleteTicket(id);
	}
}
