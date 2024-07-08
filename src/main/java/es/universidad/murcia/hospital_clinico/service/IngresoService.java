package es.universidad.murcia.hospital_clinico.service;

import es.universidad.murcia.hospital_clinico.dto.IngresoDTO;
import es.universidad.murcia.hospital_clinico.dto.UpdateIngresoDTO;
import es.universidad.murcia.hospital_clinico.model.Estado;
import es.universidad.murcia.hospital_clinico.model.Ingreso;
import es.universidad.murcia.hospital_clinico.model.Mascota;
import es.universidad.murcia.hospital_clinico.repository.IngresoRepository;
import es.universidad.murcia.hospital_clinico.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngresoService {
    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Ingreso> findAll() {
        return ingresoRepository.findAll();
    }

    public Optional<Ingreso> findById(Long id) {
        return ingresoRepository.findById(id);
    }

    public Ingreso save(Ingreso ingreso) {
        return ingresoRepository.save(ingreso);
    }

    public Ingreso createIngreso(IngresoDTO ingresoDTO) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(ingresoDTO.getMascotaId());
        if (!mascotaOpt.isPresent() || !mascotaOpt.get().isActivo()) {
            throw new IllegalArgumentException("Mascota no encontrada o inactiva con ID: " + ingresoDTO.getMascotaId());
        }

        Mascota mascota = mascotaOpt.get();
        if (!mascota.getDniResponsable().equals(ingresoDTO.getDniPersonaRegistra())) {
            throw new IllegalArgumentException("La persona que registra el ingreso no es el responsable de la mascota");
        }

        Ingreso ingreso = new Ingreso();
        ingreso.setFechaAltaIngreso(ingresoDTO.getFechaAltaIngreso());
        ingreso.setEstado(Estado.ALTA);
        ingreso.setMascota(mascota);
        ingreso.setDniPersonaRegistra(ingresoDTO.getDniPersonaRegistra());
        ingreso.setFechaFinalizacionIngreso(ingresoDTO.getFechaFinalizacionIngreso());

        return ingresoRepository.save(ingreso);
    }

    public Ingreso updateIngreso(Long ingresoId, Long mascotaId, UpdateIngresoDTO updateIngresoDTO) {
        Optional<Ingreso> ingresoOpt = ingresoRepository.findById(ingresoId);
        if (!ingresoOpt.isPresent() || ingresoOpt.get().getEstado() == Estado.ANULADO) {
            throw new IllegalArgumentException("Ingreso no encontrado o anulado con ID: " + ingresoId);
        }

        Ingreso ingreso = ingresoOpt.get();
        if (!ingreso.getMascota().getId().equals(mascotaId) || !ingreso.getMascota().isActivo()) {
            throw new IllegalArgumentException("El ingreso no pertenece a una mascota activa con ID: " + mascotaId);
        }

        if (updateIngresoDTO.getEstado() == Estado.FINALIZADO && updateIngresoDTO.getFechaFinalizacionIngreso() == null) {
            throw new IllegalArgumentException("No se puede finalizar el ingreso sin una fecha de fin");
        }

        if (updateIngresoDTO.getFechaFinalizacionIngreso() != null) {
            ingreso.setFechaFinalizacionIngreso(updateIngresoDTO.getFechaFinalizacionIngreso());
        }
        if (updateIngresoDTO.getEstado() != null) {
            ingreso.setEstado(updateIngresoDTO.getEstado());
        }

        return ingresoRepository.save(ingreso);
    }

    public void anulatedById(Long id) {
        Optional<Ingreso> optionalIngreso = ingresoRepository.findById(id);
        if (optionalIngreso.isPresent()) {
            Ingreso existingIngreso = optionalIngreso.get();
            if (existingIngreso.getEstado() != Estado.ANULADO) {
                existingIngreso.setEstado(Estado.ANULADO);
                ingresoRepository.save(existingIngreso);
            } else {
                throw new RuntimeException("El ingreso ya está anulado con ID: " + id);
            }
        } else {
            throw new RuntimeException("Ingreso no encontrado " + id);
        }
    }

    public List<Ingreso> findIngresosByMascotaId(Long mascotaId) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(mascotaId);
        if (!mascotaOpt.isPresent() || !mascotaOpt.get().isActivo()) {
            throw new RuntimeException("Mascota no encontrada o inactiva con ID: " + mascotaId);
        }

        return ingresoRepository.findByMascotaId(mascotaId);
    }
}
