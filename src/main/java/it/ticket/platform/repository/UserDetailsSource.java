package it.ticket.platform.repository;

import java.util.Collection;

import it.ticket.platform.model.Ruoli;

public interface UserDetailsSource {
    Long getId();
    String getUsername();
    String getPassword();
    Collection<Ruoli> getRoles();
}

