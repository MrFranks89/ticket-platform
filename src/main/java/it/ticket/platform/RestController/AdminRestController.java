package it.ticket.platform.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private OperatoreRepository operatoreRepository;

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin non trovato con id: " + id));
    }

    @GetMapping("/{id}/tickets")
    public List<Ticket> getTicketsByAdminId(@PathVariable Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin non trovato con id: " + id));
        return admin.getTickets();
    }

    @PostMapping("/{id}/tickets")
    public Ticket createTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin non trovato con id: " + id));
        ticket.setAdmin(admin);
        return ticketRepository.save(ticket);
    }


    @PutMapping("/{adminId}/tickets/{ticketId}/assign")
    public Ticket assignTicketToOperator(@PathVariable Long adminId, @PathVariable Long ticketId,
                                         @RequestParam Long operatoreId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket non trovato con id: " + ticketId));
        Operatore operatore = operatoreRepository.findById(operatoreId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Operatore non trovato con id: " + operatoreId));
        ticket.setOperatore(operatore);
        return ticketRepository.save(ticket);
    }
}