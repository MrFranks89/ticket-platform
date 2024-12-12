package it.ticket.platform.repository;

import java.util.Collection;


public interface UserDetailsSource {
    Long getId();
    String getUsername();
    String getPassword();
}