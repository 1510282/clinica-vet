package es.universidad.murcia.hospital_clinico.service;

import es.universidad.murcia.hospital_clinico.model.Mascota;
import es.universidad.murcia.hospital_clinico.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {
    @Autowired
    private MascotaRepository mascotaRepository;


    public Optional<Mascota> findById(Long id) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);
        if (!mascotaOpt.isPresent()) {
            return Optional.empty();
        }
        return mascotaOpt;
    }

    public Mascota save(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public void deactivateMascota(Long id) {
        Optional<Mascota> mascotaOpt = mascotaRepository.findById(id);
        if (mascotaOpt.isPresent()) {
            Mascota mascota = mascotaOpt.get();
            mascota.setActivo(false);
            mascotaRepository.save(mascota);
        } else {
            throw new RuntimeException("Mascota no encontrada con ID: " + id);
        }
    }
}
