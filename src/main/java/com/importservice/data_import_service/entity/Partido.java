package com.importservice.data_import_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partido")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHora;

    @Column(name = "id_local")
    private Long idLocal;

    @Column(name = "id_visitante")
    private Long idVisitante;

    public Partido() {}

    public Partido(LocalDateTime fechaHora, Long idLocal, Long idVisitante) {
        this.fechaHora = fechaHora;
        this.idLocal = idLocal;
        this.idVisitante = idVisitante;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public Long getIdLocal() { return idLocal; }
    public void setIdLocal(Long idLocal) { this.idLocal = idLocal; }
    public Long getIdVisitante() { return idVisitante; }
    public void setIdVisitante(Long idVisitante) { this.idVisitante = idVisitante; }
}
