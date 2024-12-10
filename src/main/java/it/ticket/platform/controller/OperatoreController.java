package it.ticket.platform.controller;

import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/operatori")
public class OperatoreController {

	@Autowired
	private OperatoreRepository operatoreRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@GetMapping
	public String index(Model model) {
		List<Operatore> operatori = operatoreRepository.findAll();
		model.addAttribute("operatori", operatori);
		model.addAttribute("operatore", new Operatore());
		return "operatore/index";
	}

	@GetMapping("/{id}")
	public String showOperatore(@PathVariable Long id, Model model) {
		Operatore operatore = operatoreRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Operatore non trovato con id: " + id));
		model.addAttribute("operatore", operatore);
		
		List<Ticket> assignedTickets = ticketRepository.findByOperatoreId(id);
        model.addAttribute("assignedTickets", assignedTickets);

		return "operatori/show";
	}
	
	
}
