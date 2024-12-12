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

	/*@GetMapping
	public String index(Model model) {
		List<Nota> allNote = notaRepository.findAll();
		model.addAttribute("note", allNote);
		model.addAttribute("nota", new Nota());
		return "note/index";
	}

	@GetMapping("/{id}")
	public String showNota(@PathVariable Long id, Model model) {
		Nota nota = notaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Admin non trovato con id: " + id));
		model.addAttribute("nota", nota);
		return "nota/show";
	}*/
	
	@GetMapping("/create")
	public String creaNota(@RequestParam(required = false) Long ticketId, Model model) {
		Nota nuovaNota = new Nota();
		if (ticketId != null) {
			Ticket ticket = ticketRepository.findById(ticketId)
					.orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
			nuovaNota.setTicket(ticket);
		}
		model.addAttribute("nota", nuovaNota);
		model.addAttribute("ticket", ticketRepository.findAll());
		return "note/create";
	}

	@PostMapping("/create")
	public String creaNota(@Valid @ModelAttribute("nota") Nota nota, BindingResult bindingResult,
			@RequestParam(required = false) Long ticketId, @RequestParam(required = false) Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("ticket", ticketRepository.findAll());
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

