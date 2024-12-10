package it.ticket.platform.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Nota {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String testo;

    private LocalDateTime dataCreazione;

    @ManyToOne
    @JoinColumn(name = "autore_id")
    private Operatore autore;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Nota() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public LocalDateTime getDataCreazione() {
        return dataCreazione;
    }

    public void setDataCreazione(LocalDateTime dataCreazione) {
        this.dataCreazione = dataCreazione;
    }

    public Operatore getAutore() {
        return autore;
    }

    public void setAutore(Operatore autore) {
        this.autore = autore;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", testo='" + testo + '\'' +
                ", dataCreazione=" + dataCreazione +
                ", autore=" + (autore != null ? autore.getId() : null) +
                ", ticket=" + (ticket != null ? ticket.getId() : null) +
                '}';
    }
}
