package es.universidad.murcia.hospital_clinico.dto;

import es.universidad.murcia.hospital_clinico.model.Estado;

import java.time.LocalDate;

public class UpdateIngresoDTO {
    private LocalDate fechaFinalizacionIngreso;
    private Estado estado;




    // Getters y Setters


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
}
