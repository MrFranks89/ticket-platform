package it.ticket.platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Nota {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String testo;

    @NotNull(message = "La data di creazione non può essere vuota")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
    private LocalDateTime dataCreazione;
    
    @NotNull(message = "La data di modifica non può essere vuota")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent
    private LocalDateTime dataModifica;

    @ManyToOne
    @JoinColumn(name = "operatore_id")
    private Operatore operatore;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonBackReference
    private Ticket ticket;
    
    public Nota() {
    	
    }

    public Nota(Operatore operatore, String testo, Ticket ticket) {
    	this.operatore = operatore;
    	this.testo = testo;
    	this.ticket = ticket;
    	 this.dataCreazione = LocalDateTime.now();
    }

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

    public LocalDateTime getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(LocalDateTime dataModifica) {
		this.dataModifica = dataModifica;
	}

    public Operatore getOperatore() {
		return operatore;
	}

	public void setOperatore(Operatore operatore) {
		this.operatore = operatore;
	}

	public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

	@Override
	public String toString() {
		return "Nota [id=" + id + ", testo=" + testo + ", dataCreazione=" + dataCreazione + ", dataModifica="
				+ dataModifica + ", operatore=" + operatore + ", ticket=" + ticket + "]";
	}

 
}
