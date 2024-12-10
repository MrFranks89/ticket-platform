package it.ticket.platform.RestController;

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

import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.TicketRepository;
import it.ticket.platform.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("/api/tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private TicketRepository ticketRepository;
    
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

    // API per creare un nuovo ticket
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    // API per aggiornare un ticket
    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket updatedTicket) {
        return ticketService.updateTicket(id, updatedTicket);
    }

    // API per aggiornare solo lo stato di un ticket
    @PutMapping("/{id}/stato")
    public Ticket updateStato(@PathVariable Long id, @RequestParam String nuovoStato) {
        return ticketService.updateStato(id, nuovoStato);
    }

    // API per eliminare un ticket
    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
    }
}
