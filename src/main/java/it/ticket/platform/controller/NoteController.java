package it.ticket.platform.controller;

import java.time.LocalDateTime;
import java.util.List;

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
import it.ticket.platform.model.Nota;
import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.NotaRepository;
import it.ticket.platform.repository.TicketRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NotaRepository notaRepository;

	@Autowired
	private TicketRepository ticketRepository;
	
	@GetMapping("/create")
	public String creaNota(@RequestParam(required = false) Long ticketId, Model model) {
	    Ticket ticket = ticketRepository.findById(ticketId)
	            .orElseThrow(() -> new IllegalArgumentException("Ticket non trovato con id: " + ticketId));

	    model.addAttribute("ticket", ticket);
	    model.addAttribute("nota", new Nota());
	    return "note/create";
	}
	
	@PostMapping("/tickets/{id}/note/add")
	public String aggiungiNota(@PathVariable Long id, @RequestParam String testo) {
	    // Recupera il ticket dal repository
	    Ticket ticket = ticketRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Ticket non trovato con id: " + id));

	    // Recupera l'operatore associato (adatta secondo la tua logica)
	    Operatore operatore = ticket.getOperatore();
	    if (operatore == null) {
	        throw new IllegalArgumentException("Operatore non trovato per il ticket con id: " + id);
	    }

	    // Crea e salva la nota
	    Nota nota = new Nota(operatore, testo, ticket);
	    notaRepository.save(nota);

	    // Reindirizza alla stessa pagina del ticket
	    return "redirect:/tickets/show/" + id;
	}

	@PostMapping("/create")
	public String creaNota(@Valid @ModelAttribute("nota") Nota nota, BindingResult bindingResult,
	        @RequestParam(required = false) Long ticketId, Model model) {

	    if (bindingResult.hasErrors()) {
	        if (ticketId != null) {
	            Ticket ticket = ticketRepository.findById(ticketId)
	                    .orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
	            model.addAttribute("ticket", ticket); // Ripristina il ticket nel modello
	        }
	        return "note/create";
	    }

	    if (ticketId != null) {
	        Ticket ticket = ticketRepository.findById(ticketId)
	                .orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
	        nota.setTicket(ticket);
	    }

	    notaRepository.save(nota);
	    return "redirect:/ticket/show/" + nota.getTicket().getId();
	}

	@GetMapping("/edit/{id}")
	public String editNota(@PathVariable Long id, Model model) {
		Nota nota = notaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Nota non trovata"));

		model.addAttribute("nota", nota);
		model.addAttribute("ticket", ticketRepository.findAll());
		return "offerte/edit";
	}

	@PostMapping("/edit/{id}")
	public String aggiornaNota(@PathVariable Long id, @Valid @ModelAttribute("nota") Nota nota,
			BindingResult bindingResult, @RequestParam(required = false) Long ticketId,
			@RequestParam(required = false) Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("ticket", ticketRepository.findAll());
			return "note/edit";
		}

		if (ticketId != null) {
			Ticket ticket = ticketRepository.findById(ticketId)
					.orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
			nota.setTicket(ticket);
		}
		
		notaRepository.save(nota);

		return "redirect:/ticket/show/" + nota.getTicket().getId();
	}

	@PostMapping("/delete/{id}")
	public String deleteNota(@PathVariable Long id) {
		Nota nota = notaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Nota non trovata"));
		notaRepository.delete(nota);
		return "redirect:/ticket/show/" + nota.getTicket().getId();
	}
}

