package com.importservice.data_import_service.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "localidad")
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localidad")
    @JsonProperty("id_Localidad")
    private Long idLocalidad;

    @Column(nullable = false, length = 100)
    private String nombre;

    public Localidad() {}

    public Localidad(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdLocalidad() { return idLocalidad; }
    public void setIdLocalidad(Long idLocalidad) { this.idLocalidad = idLocalidad; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}