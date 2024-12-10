package it.ticket.platform.model;

import jakarta.persistence.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

import it.ticket.platform.repository.OperatoreRepository;
import it.ticket.platform.repository.UserDetailsSource;

@Entity
public class Operatore implements UserDetailsSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    private String cognome;
    
    private String username;

    private String email;

    private String password;

    private boolean disponibile;

    @OneToMany(mappedBy = "operatore", fetch = FetchType.LAZY)
    private List<Ticket> tickets;
    
  @ManyToMany(fetch = FetchType.EAGER)
	private List<Roles> roles;
    
  private String ruolo;

    public Operatore() {}

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
    

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Operatore(Long id, String nome, String cognome, String username, String email, String password,
			boolean disponibile, List<Ticket> tickets, List<Roles> roles, String ruolo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.username = username;
		this.email = email;
		this.password = password;
		this.disponibile = disponibile;
		this.tickets = tickets;
		this.roles = roles;
		this.ruolo = ruolo;
	}
 

	
    
    
}
