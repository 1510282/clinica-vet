package es.universidad.murcia.hospital_clinico.dto;

import java.time.LocalDate;

public class IngresoDTO {
    private Long mascotaId;
    private LocalDate fechaAltaIngreso;
    private String dniPersonaRegistra;
    private LocalDate fechaFinalizacionIngreso;

    public String getDniPersonaRegistra() {
        return dniPersonaRegistra;
    }

    public void setDniPersonaRegistra(String dniPersonaRegistra) {
        this.dniPersonaRegistra = dniPersonaRegistra;
    }

    public LocalDate getFechaFinalizacionIngreso() {
        return fechaFinalizacionIngreso;
    }

    public void setFechaFinalizacionIngreso(LocalDate fechaFinalizacionIngreso) {
        this.fechaFinalizacionIngreso = fechaFinalizacionIngreso;
    }

    // Getters y Setters
    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public LocalDate getFechaAltaIngreso() {
        return fechaAltaIngreso;
    }

    public void setFechaAltaIngreso(LocalDate fechaAltaIngreso) {
        this.fechaAltaIngreso = fechaAltaIngreso;
    }
}
