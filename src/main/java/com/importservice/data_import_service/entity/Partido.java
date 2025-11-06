package com.importservice.data_import_service.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Entity
@Table(name = "partido")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partido")
    @JsonProperty("id_Partido")
    private Long idPartido;

    @Column(name = "fecha_hora", nullable = false)
    @JsonProperty("FechaHora")
    private LocalDateTime fechaHora;

    @Column(name = "id_localidad", nullable = false)
    @JsonProperty("id_Localidad")
    private Long idLocalidad;

    @Column(name = "id_local", nullable = false)
    @JsonProperty("id_Local")
    private Long idLocal;

    @Column(name = "id_visitante", nullable = false)
    @JsonProperty("id_Visitante")
    private Long idVisitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localidad", insertable = false, updatable = false)
    private Localidad localidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_local", insertable = false, updatable = false)
    private Equipo local;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitante", insertable = false, updatable = false)
    private Equipo visitante;

    public Partido() {}

    public Partido(LocalDateTime fechaHora, Long idLocalidad, Long idLocal, Long idVisitante) {
        this.fechaHora = fechaHora;
        this.idLocalidad = idLocalidad;
        this.idLocal = idLocal;
        this.idVisitante = idVisitante;
    }

    public Long getIdPartido() { return idPartido; }
    public void setIdPartido(Long idPartido) { this.idPartido = idPartido; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }
    public Long getIdLocal() { return idLocal; }
    public void setIdLocal(Long idLocal) { this.idLocal = idLocal; }
    public Long getIdVisitante() { return idVisitante; }
    public void setIdVisitante(Long idVisitante) { this.idVisitante = idVisitante; }
    public Localidad getLocalidad() { return localidad; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
    public Equipo getLocal() { return local; }
    public void setLocal(Equipo local) { this.local = local; }
    public Equipo getVisitante() { return visitante; }
    public void setVisitante(Equipo visitante) { this.visitante = visitante; }
}