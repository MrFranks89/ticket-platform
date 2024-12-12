package it.ticket.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.ticket.platform.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
