package it.exam.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.exam.model.Ticket;
import it.exam.repository.TicketRepository;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        validateStato(ticket.getStato());
        ticket.setDataCreazione(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }

    private void validateStato(String stato) {
        if (!StatoTicket.DA_FARE.equals(stato) &&
            !StatoTicket.IN_CORSO.equals(stato) &&
            !StatoTicket.COMPLETATO.equals(stato)) {
            throw new IllegalArgumentException("Stato non valido: " + stato);
        }
    }
}
