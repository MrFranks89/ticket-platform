package it.ticket.platform.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.ticket.platform.model.Admin;
import it.ticket.platform.model.Categoria;
import it.ticket.platform.model.Nota;
import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.AdminRepository;
import it.ticket.platform.repository.CategoriaRepository;
import it.ticket.platform.repository.NotaRepository;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	private OperatoreRepository operatoreRepository;

	@Autowired
	private NotaRepository notaRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public String index(Authentication authentication, Model model,
			@RequestParam(name = "keyword", required = false) String keyword) {
		List<Ticket> allTickets = ticketRepo.findAll();

		if (keyword != null && !keyword.isBlank()) {
			allTickets = ticketRepo.findByTitoloContaining(keyword);
			model.addAttribute("keyword", keyword);
		} else {
			allTickets = ticketRepo.findAll();
		}

		if (keyword == null || keyword.isBlank() || keyword.equals("null")) {
			model.addAttribute("ticketUrl", "/tickets");
		} else {
			model.addAttribute("ticketUrl", "/tickets?keyword=" + keyword);
		}

		model.addAttribute("tickets", allTickets);

		return "tickets/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Long id, Model model) {

		Optional<Ticket> ticketOptional = ticketRepo.findById(id);

		if (ticketOptional.isPresent()) {
			model.addAttribute("ticket", ticketOptional.get());
		}

		List<Nota> note = notaRepository.findByTicketId(id);
		model.addAttribute("note", note);

		return "tickets/show";
	}

	@PostMapping("ticket/addNote")
	public String addNote(@RequestParam Long ticketId, @RequestParam Long operatoreId, @RequestParam String testo,
			Model model) {
		Ticket ticket = ticketRepo.findById(ticketId)
				.orElseThrow(() -> new EntityNotFoundException("Ticket non trovato"));

		Operatore operatore = operatoreRepository.findById(operatoreId)
				.orElseThrow(() -> new EntityNotFoundException("Operatore non trovato"));

		Nota nota = new Nota(operatore, testo, ticket);

		notaRepository.save(nota);

		model.addAttribute("successMessage", "Nota aggiunta");

		return "redirect:/admin/tickets";
	}

	@GetMapping("/create")
	public String create(Model model) {

		Ticket ticket = new Ticket();

		ticket.setStato("da fare");
		List<Operatore> operatoreDisponibile = operatoreRepository.findByDisponibile(true);

		model.addAttribute("ticket", new Ticket());
		model.addAttribute("allOperatori", operatoreDisponibile);
		return "tickets/create";
	}

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {

			bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			return "tickets/create";
		}
		
		  if (formTicket.getAdmin() == null) {
		        // Evita di impostare l'admin_id, non assegnare nulla
		        formTicket.setAdmin(null);
		    } else {
		        // Imposta l'admin solo se necessario, qui potrebbe essere il caso dell'admin loggato
		        if (formTicket.getAdmin() == null) {
		            String adminUsername = "f_franchi";  // O usare l'utente loggato
		            Admin admin = adminRepository.findByUsername(adminUsername)
		                    .orElseThrow(() -> new IllegalArgumentException("Admin non trovato"));
		            formTicket.setAdmin(admin);
		        }
		    }

		if (formTicket.getDataCreazione() == null) {
			formTicket.setDataCreazione(LocalDateTime.now());
		}

		if (formTicket.getStato() == null || formTicket.getStato().isEmpty()) {
			formTicket.setStato("da fare");
		}

		if (formTicket.getCategoria() == null) {
			bindingResult.rejectValue("categoria", "error.categoria", "La categoria non può essere vuota");
			return "tickets/create";
		}

		if (formTicket.getCategoria() != null && formTicket.getCategoria().getId() != null) {

			Categoria categoria = categoriaRepository.findById(formTicket.getCategoria().getId())
					.orElseThrow(() -> new IllegalArgumentException("Categoria non trovata"));

			formTicket.setCategoria(categoria);
		} else {
			bindingResult.rejectValue("categoria", "error.categoria", "La categoria non può essere vuota");
			return "tickets/create";
		}

		if (formTicket.getAdmin() == null) {
			String adminUsername = "f_franchi";
			Admin admin = adminRepository.findByUsername(adminUsername)
					.orElseThrow(() -> new IllegalArgumentException("Admin non trovato"));
			formTicket.setAdmin(admin);
		}

		System.out.println(formTicket);

		ticketRepo.save(formTicket);
		redirectAttributes.addFlashAttribute("successMessage", "Ticket creato con successo!");
		return "redirect:/tickets";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("ticket", ticketRepo.findById(id).get());
		model.addAttribute("allOperatori", operatoreRepository.findAll());
		//model.addAttribute("adminId", adminRepository.findById(id));
		return "tickets/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@PathVariable Long id, @Valid @ModelAttribute("ticket") Ticket formTicket,
			BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "tickets/edit";
		}

		Ticket ticket = ticketRepo.findById(id).orElseThrow();
		new RuntimeException("Ticket non trovato");
		
		if (formTicket.getAdmin() == null) {
	        formTicket.setAdmin(null); 
	    }

		if (!formTicket.getTitolo().equals(ticket.getTitolo())) {
			bindingResult.addError(new ObjectError("titolo", "Il titolo non può essere modificato"));
			return "tickets/edit";
		}

		if (!formTicket.getDescrizione().equals(ticket.getDescrizione())) {
			bindingResult.addError(new ObjectError("descrizione", "La descrizione non può essere modificato"));

		}

		if (formTicket.getDataModifica() == null) {
			formTicket.setDataModifica(LocalDateTime.now());
		}

		ticketRepo.save(formTicket);
		redirectAttributes.addFlashAttribute("successMessage", "Ticket modificato con successo!");
		return "redirect:/tickets/show/{id}";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
		ticketRepo.delete(ticket);
		redirectAttributes.addFlashAttribute("successMessage", "Ticket eliminato con successo!");
		return "redirect:/tickets";
	}

	@PostMapping("/updateTicketStatus/{id}")
	public String updateTicketStatus(@PathVariable Long id, @RequestParam String stato,
			RedirectAttributes redirectAttributes) {
		Ticket ticket = ticketRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ticket non trovato con id: " + id));

		Operatore operatoreAssociato = ticket.getOperatore();
		if (operatoreAssociato != null) {
			ticket.setStato(stato);
			ticketRepo.save(ticket);
			redirectAttributes.addFlashAttribute("successMessage", "Stato del ticket aggiornato con successo!");
			return "redirect:/operatori/" + operatoreAssociato.getId();
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Questo ticket non ha un operatore assegnato.");
			return "redirect:/tickets/show/" + id;
		}
	}
}
