package com.importservice.data_import_service.repository;

import com.importservice.data_import_service.entity.Partido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoRepository extends JpaRepository<Partido, Long> {}
