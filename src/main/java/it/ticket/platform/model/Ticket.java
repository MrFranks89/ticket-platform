package it.ticket.platform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import it.ticket.platform.model.Categoria;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Il titolo non può essere nullo")
	@NotBlank(message = "Il titolo non può essere vuoto")
	private String titolo;

	@NotNull(message = "La descrizione non può essere nulla")
	@NotBlank(message = "La descrizione non può essere vuota")
	private String descrizione;

	private String stato;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataCreazione;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataModifica;

	@ManyToOne
	@JoinColumn(name = "operatore_id", nullable = false)
	private Operatore operatore;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;
	

	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
	private List<Nota> note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id", nullable = true)
	private Admin admin;

	public Ticket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Nota> getNote() {
		return note;
	}

	public void setNote(List<Nota> note) {
		this.note = note;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		 return "Ticket [id=" + id + ", titolo=" + titolo + ", descrizione=" + descrizione + ", stato=" + stato
		            + ", dataCreazione=" + dataCreazione + ", dataModifica=" + dataModifica
		            + ", operatoreId=" + (operatore != null ? operatore.getId() : "null") 
		            + ", categoria=" + (categoria != null ? categoria.getId() : "null") 
		            + ", note=" + (note != null ? note.size() : 0) 
		            + ", adminId=" + (admin != null ? admin.getId() : "null") + "]"; 
	}

	
}
