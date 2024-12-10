package it.ticket.platform.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.ticket.platform.model.Operatore;
import it.ticket.platform.model.Ruoli;
import it.ticket.platform.repository.UserDetailsSource;

public class DatabaseUserDetails implements UserDetails {

	private final Long id;
	private final String username;
	private final String password;
	private final Set<GrantedAuthority> authorities;

	public DatabaseUserDetails(UserDetailsSource user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();

		this.authorities = new HashSet<>();
		for (Ruoli role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		}
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}