package com.importservice.data_import_service.repository;

import com.importservice.data_import_service.entity.Jugador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {}
