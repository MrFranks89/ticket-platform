package it.ticket.platform.controller;

import java.util.List;

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

import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.TicketRepository;
import it.ticket.platform.service.TicketService;
import jakarta.validation.Valid;
@Controller
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepo;
    
    @Autowired
    private OperatoreRepository operatoreRepository;

    @GetMapping
    public String index(Authentication authentication,  Model model,
    		@RequestParam(name = "keyword", required = false) String keyword) {
        List<Ticket> allTickets = ticketRepo.findAll();

        if (keyword != null && !keyword.isBlank()) {
            allTickets = ticketRepo.findByTitoloContaining(keyword);
            model.addAttribute("keyword", keyword);
        }

        model.addAttribute("tickets", allTickets);
        model.addAttribute("ticketUrl", keyword == null ? "/tickets" : "/tickets?keyword=" + keyword);

        return "tickets/index";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Long id, Model model) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
        Operatore operatore = ticket.getOperatore();
        model.addAttribute("ticket", ticket);
        model.addAttribute("operatore", operatore);
        return "tickets/show";
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "tickets/create";
    }


    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("ticket") Ticket formTicket, 
    		BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tickets/create";
        }

        ticketRepo.save(formTicket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket creato con successo!");
        return "redirect:/tickets";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
        model.addAttribute("ticket", ticket);
        return "tickets/edit";
    }


    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("ticket") Ticket formTicket, 
    		BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "tickets/edit";
        }

        Ticket existingTicket = ticketRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));

        if (!formTicket.getTitolo().equals(existingTicket.getTitolo())) {
            bindingResult.addError(new ObjectError("titolo", "Il titolo non può essere modificato"));
            return "tickets/edit";
        }

        ticketRepo.save(formTicket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket modificato con successo!");
        return "redirect:/tickets/show/" + id;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Ticket ticket = ticketRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
        ticketRepo.delete(ticket);
        redirectAttributes.addFlashAttribute("successMessage", "Ticket eliminato con successo!");
        return "redirect:/tickets";
    }
}
