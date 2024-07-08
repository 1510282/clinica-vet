package es.universidad.murcia.hospital_clinico.model;


import javax.persistence.*;

@Entity
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String especie;
    private String raza;
    private Integer edad;
    private String codigoIdentificacion;
    private String dniResponsable;
    private boolean activo = true; // Por defecto, la mascota está activa

    // Constructor vacío para JPA
    public Mascota() {}

    // Constructor con parámetros
    public Mascota(String especie, String raza, Integer edad, String codigoIdentificacion, String dniResponsable) {
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.codigoIdentificacion = codigoIdentificacion;
        this.dniResponsable = dniResponsable;
        this.activo = true; // Por defecto, la mascota está activa
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCodigoIdentificacion() {
        return codigoIdentificacion;
    }

    public void setCodigoIdentificacion(String codigoIdentificacion) {
        this.codigoIdentificacion = codigoIdentificacion;
    }

    public String getDniResponsable() {
        return dniResponsable;
    }

    public void setDniResponsable(String dniResponsable) {
        this.dniResponsable = dniResponsable;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", codigoIdentificacion='" + codigoIdentificacion + '\'' +
                ", dniResponsable='" + dniResponsable + '\'' +
                ", activo=" + activo +
                '}';
    }
}
