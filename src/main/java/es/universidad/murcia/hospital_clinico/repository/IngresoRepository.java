package es.universidad.murcia.hospital_clinico.repository;

import es.universidad.murcia.hospital_clinico.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
    List<Ingreso> findByMascotaId(Long mascotaId);
}
