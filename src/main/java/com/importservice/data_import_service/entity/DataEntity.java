package com.importservice.data_import_service.entity;
 
import jakarta.persistence.*; 
import java.time.LocalDate; 
 
@Entity 
@Table(name = "imported_data") 
public class DataEntity { 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
 
    @Column(nullable = false) 
    private String name; 
 
    @Column(nullable = false, unique = true) 
    private String email; 
 
    @Column(nullable = false) 
    private Integer age; 
 
    @Column(nullable = false) 
    private Double salary; 
 
    @Column(name = "birth_date") 
    private LocalDate birthDate; 
 
    public DataEntity() {} 
 
    public DataEntity(String name, String email, Integer age, Double salary, LocalDate birthDate) { 
        this.name = name; 
        this.email = email; 
        this.age = age; 
        this.salary = salary; 
        this.birthDate = birthDate; 
    } 
 
    // Getters y Setters 
    public Long getId() { return id; } 
    public void setId(Long id) { this.id = id; } 
    public String getName() { return name; } 
    public void setName(String name) { this.name = name; } 
    public String getEmail() { return email; } 
    public void setEmail(String email) { this.email = email; } 
    public Integer getAge() { return age; } 
    public void setAge(Integer age) { this.age = age; } 
    public Double getSalary() { return salary; } 
    public void setSalary(Double salary) { this.salary = salary; } 
    public LocalDate getBirthDate() { return birthDate; } 
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; } 
} 
