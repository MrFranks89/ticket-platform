package it.ticket.platform.controller;

import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/operatori")
public class OperatoreController {

	@Autowired
	private OperatoreRepository operatoreRepository;

	@Autowired
	private TicketRepository ticketRepository;

	@GetMapping
	public String index(Model model) {
		List<Operatore> allOperatori = operatoreRepository.findAll();
		model.addAttribute("operatori", allOperatori);
		model.addAttribute("operatore", new Operatore());
		return "operatori/index";
	}

	@GetMapping("/{id}")
	public String showOperatore(@PathVariable Long id, Model model) {
		Operatore operatore = operatoreRepository.findById(id)
	            .orElseThrow(() -> new EntityNotFoundException("Operatore non trovato con id: " + id));
		model.addAttribute("operatore", operatore);
		
		
		List<Ticket> assignedTickets = ticketRepository.findByOperatoreId(id);
		System.out.println("Assigned tickets: " + assignedTickets);
        model.addAttribute("assignedTickets", assignedTickets);
        

		return "operatori/show";
	}
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute(name = "operatore") Operatore operatore,
			BindingResult bindingresult, Model model) {
		
		if(bindingresult.hasErrors()) {

			List<Operatore> allOperatori= operatoreRepository.findAll();
			
			model.addAttribute("operatori", allOperatori);
			model.addAttribute("operatore", new Operatore());
			
			
			return "/operatori/index";
		}
		
		operatoreRepository.save(operatore);
		
		return "redirect:/operatori";
	}

	 @PostMapping("/updateStatus/{id}")
	    public String updateStatus(@PathVariable Long id, @RequestParam boolean disponibile, RedirectAttributes redirectAttributes) {
	        Operatore operatore = operatoreRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("Operatore non trovato con id: " + id));

	        operatore.setDisponibile(disponibile);
	        operatoreRepository.save(operatore);

	        redirectAttributes.addFlashAttribute("successMessage", "Stato dell'operatore aggiornato con successo!");
	        return "redirect:/operatori/{id}";
	    }
	
}
