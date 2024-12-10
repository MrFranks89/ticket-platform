package it.ticket.platform.repository;

import java.util.Collection;

import it.ticket.platform.model.Roles;

public interface UserDetailsSource {
    Long getId();
    String getUsername();
    String getPassword();
    Collection<Roles> getRoles();
}