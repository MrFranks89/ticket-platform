package it.ticket.platform.model;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import it.ticket.platform.repository.UserDetailsSource;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Admin implements UserDetailsSource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String cognome;

	private String username;

	private String email;

	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Ruoli> roles;
	
	@OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<Ticket> tickets;
	
	private String ruolo;
	
	public Admin() {}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	

	public List<Ruoli> getRoles() {
		return roles;
	}


	public void setRoles(List<Ruoli> roles) {
		this.roles = roles;
	}


	public String getRuolo() {
		return ruolo;
	}


	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}


	public Admin(Long id, String nome, String cognome, String username, String email, String password,
			List<Ruoli> roles, List<Ticket> tickets, String ruolo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.tickets = tickets;
		this.ruolo = ruolo;
	}


}
