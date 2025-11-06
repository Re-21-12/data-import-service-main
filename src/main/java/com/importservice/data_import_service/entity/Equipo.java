package com.importservice.data_import_service.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "equipo")
public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo")
    @JsonProperty("id_Equipo")
    private Long idEquipo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(name = "id_localidad", nullable = false)
    @JsonProperty("id_Localidad")
    private Long idLocalidad;

    @Column(name = "url_imagen", length = 500)
    @JsonProperty("url_imagen")
    private String urlImagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_localidad", insertable = false, updatable = false)
    private Localidad localidad;

    public Equipo() {}

    public Equipo(String nombre, Long idLocalidad, String urlImagen) {
        this.nombre = nombre;
        this.idLocalidad = idLocalidad;
        this.urlImagen = urlImagen;
    }

    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long idEquipo) { this.idEquipo = idEquipo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public Localidad getLocalidad() { return localidad; }
    public void setLocalidad(Localidad localidad) { this.localidad = localidad; }
}