package it.ticket.platform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.ticket.platform.model.Admin;
import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.AdminRepository;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private OperatoreRepository operatoreRepository;

	@GetMapping
	public String index(Model model) {
		List<Admin> allAdmins = adminRepository.findAll();
		model.addAttribute("admin", allAdmins);
		return "admin/index";
	}

	@GetMapping("/{id}")
	public String showAdmin(@PathVariable Long id, Model model) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admin non trovato con id: " + id));
		model.addAttribute("admin", admin);
		return "admin/show";
	}

	@GetMapping("/{id}/tickets")
	public String showTicketsByAdmin(@PathVariable Long id, Model model) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admin non trovato con id: " + id));
		model.addAttribute("tickets", admin.getTickets());
		model.addAttribute("adminId", id);
		return "admin/tickets";
	}


	@GetMapping("/{id}/tickets/create")
	public String createTicket(@PathVariable Long id, Model model) {
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admin non trovato con id: " + id));
		Ticket newTicket = new Ticket();
		newTicket.setAdmin(admin);
		model.addAttribute("ticket", newTicket);
		return "admin/create_ticket";
	}


	@PostMapping("/{id}/tickets/create")
	public String storeTicket(@PathVariable Long id, @Valid @ModelAttribute("ticket") Ticket ticket,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/create_ticket";
		}
		Admin admin = adminRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admin non trovato con id: " + id));
		ticket.setAdmin(admin);
		ticketRepository.save(ticket);
		redirectAttributes.addFlashAttribute("successMessage", "Ticket creato con successo!");
		return "redirect:/admin/" + id + "/tickets";
	}


	@GetMapping("/{adminId}/tickets/{ticketId}/assign")
	public String assignTicketForm(@PathVariable Long adminId, @PathVariable Long ticketId, Model model) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new IllegalArgumentException("Ticket non trovato con id: " + ticketId));
		List<Operatore> allOperators = operatoreRepository.findAll();
		model.addAttribute("ticket", ticket);
		model.addAttribute("operators", allOperators);
		return "admin/assign_ticket";
	}


	@PostMapping("/{adminId}/tickets/{ticketId}/assign")
	public String assignTicket(@PathVariable Long adminId, @PathVariable Long ticketId, @RequestParam Long operatoreId,
			RedirectAttributes redirectAttributes) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new IllegalArgumentException("Ticket non trovato con id: " + ticketId));
		Operatore operatore = operatoreRepository.findById(operatoreId)
				.orElseThrow(() -> new IllegalArgumentException("Operatore non trovato con id: " + operatoreId));
		ticket.setOperatore(operatore);
		ticketRepository.save(ticket);
		redirectAttributes.addFlashAttribute("successMessage", "Ticket assegnato con successo!");
		return "redirect:/admin/" + adminId + "/tickets";
	}
	
	
}