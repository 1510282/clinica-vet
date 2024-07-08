package es.universidad.murcia.hospital_clinico.model;




import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Ingreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fechaAltaIngreso;
    private LocalDate fechaFinalizacionIngreso;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    private String dniPersonaRegistra;

    public Ingreso() {}

    public Ingreso(LocalDate fechaAltaIngreso, LocalDate fechaFinalizacionIngreso, Estado estado, Mascota mascota, String dniPersonaRegistra) {
        this.fechaAltaIngreso = fechaAltaIngreso;
        this.fechaFinalizacionIngreso = fechaFinalizacionIngreso;
        this.estado = estado;
        this.mascota = mascota;
        this.dniPersonaRegistra = dniPersonaRegistra;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAltaIngreso() {
        return fechaAltaIngreso;
    }

    public void setFechaAltaIngreso(LocalDate fechaAltaIngreso) {
        this.fechaAltaIngreso = fechaAltaIngreso;
    }

    public LocalDate getFechaFinalizacionIngreso() {
        return fechaFinalizacionIngreso;
    }

    public void setFechaFinalizacionIngreso(LocalDate fechaFinalizacionIngreso) {
        this.fechaFinalizacionIngreso = fechaFinalizacionIngreso;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getDniPersonaRegistra() {
        return dniPersonaRegistra;
    }

    public void setDniPersonaRegistra(String dniPersonaRegistra) {
        this.dniPersonaRegistra = dniPersonaRegistra;
    }

    @Override
    public String toString() {
        return "Ingreso{" +
                "id=" + id +
                ", fechaAltaIngreso=" + fechaAltaIngreso +
                ", fechaFinalizacionIngreso=" + fechaFinalizacionIngreso +
                ", estado='" + estado + '\'' +
                ", mascota=" + mascota +
                ", dniPersonaRegistra='" + dniPersonaRegistra + '\'' +
                '}';
    }
}
