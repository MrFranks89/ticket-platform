package it.ticket.platform.service;

import it.ticket.platform.model.Ticket;
import it.ticket.platform.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        validateStato(ticket.getStato());
        ticket.setDataCreazione(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }
    
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new EntityNotFoundException("Ticket non trovato con id: " + id);
        }
        ticketRepository.deleteById(id);
    }

    
    public Ticket getTicketById(Long id) {
    	return ticketRepository.findById(id)
    			.orElseThrow(() -> new EntityNotFoundException("Ticket non trovato con id: " + id));
    }

    public Ticket updateStato(Long ticketId, String newStato) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));
        validateStato(newStato);
        ticket.setStato(newStato);
        ticket.setDataModifica(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }
    
    private void validateStato(String stato) {
        if (!isValidStato(stato)) {
            throw new IllegalArgumentException("Stato non valido: " + stato);
        }
    }

    private boolean isValidStato(String stato) {
        return "DA_FARE".equalsIgnoreCase(stato) || 
               "IN_CORSO".equalsIgnoreCase(stato) || 
               "COMPLETATO".equalsIgnoreCase(stato);
    }

	public Ticket updateTicket(Long id, Ticket updatedTicket) {
		Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket non trovato"));

        existingTicket.setTitolo(updatedTicket.getTitolo());
        existingTicket.setDescrizione(updatedTicket.getDescrizione());
        existingTicket.setStato(updatedTicket.getStato());
        existingTicket.setOperatore(updatedTicket.getOperatore());
        existingTicket.setCategoria(updatedTicket.getCategoria());
        existingTicket.setDataModifica(LocalDateTime.now());

        return ticketRepository.save(existingTicket);
    }
	
	

	public List<Ticket> getAllTickets() {
	    return ticketRepository.findAll();
	}


}