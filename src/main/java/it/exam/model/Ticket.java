package it.exam.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tickets")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Il titolo non può essere nullo")
	@NotBlank(message = "Il titolo non può essere vuoto")
	private String titolo;

	@NotNull(message = "La descrizione non può essere nulla")
	@NotBlank(message = "La descrizione non può essere vuota")
	private String descrizione;

	private String stato;

	@NotNull(message = "La data di creazione ticket non può essere vuota")
	private LocalDateTime dataCreazione;

	private LocalDateTime dataModifica;

	@ManyToOne
	@JoinColumn(name = "operatore_id")
	private Operatore operatore;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	public Ticket() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	@Override
	public String toString() {
		return "Ticket{" + "id=" + id + ", titolo='" + titolo + '\'' + ", descrizione='" + descrizione + '\''
				+ ", stato=" + stato + ", dataCreazione=" + dataCreazione + ", dataModifica=" + dataModifica
				+ ", operatore=" + (operatore != null ? operatore.getId() : null) + ", categoria="
				+ (categoria != null ? categoria.getId() : null) + '}';
	}

}
