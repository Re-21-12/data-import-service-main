package com.importservice.data_import_service.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugador")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    @JsonProperty("id_Jugador")
    private Long idJugador;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(name = "numero_jugador", nullable = false)
    @JsonProperty("Numero_jugador")
    private Integer numeroJugador;

    @Column(nullable = false, length = 50)
    private String posicion;

    @Column(nullable = false)
    private Double estatura;

    @Column(length = 50)
    private String nacionalidad;

    @Column(nullable = false)
    private Integer edad;

    @Column(name = "id_equipo", nullable = false)
    @JsonProperty("id_Equipo")
    private Long idEquipo;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonProperty("CreatedBy")
    private Integer createdBy;

    @Column(name = "updated_at")
    @JsonProperty("UpdatedAt")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    @JsonProperty("UpdatedBy")
    private Integer updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo", insertable = false, updatable = false)
    private Equipo equipo;

    public Jugador() {
        this.createdAt = LocalDateTime.now();
        this.createdBy = 1; // Usuario sistema por defecto
    }

    public Long getIdJugador() { return idJugador; }
    public void setIdJugador(Long idJugador) { this.idJugador = idJugador; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Integer getNumeroJugador() { return numeroJugador; }
    public void setNumeroJugador(Integer numeroJugador) { this.numeroJugador = numeroJugador; }
    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }
    public Double getEstatura() { return estatura; }
    public void setEstatura(Double estatura) { this.estatura = estatura; }
    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long idEquipo) { this.idEquipo = idEquipo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public Integer getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(Integer updatedBy) { this.updatedBy = updatedBy; }
    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }
}