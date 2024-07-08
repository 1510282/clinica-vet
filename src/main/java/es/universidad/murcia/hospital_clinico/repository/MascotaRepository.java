package es.universidad.murcia.hospital_clinico.repository;

import es.universidad.murcia.hospital_clinico.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
}
