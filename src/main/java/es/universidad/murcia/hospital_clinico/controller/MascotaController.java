package es.universidad.murcia.hospital_clinico.controller;

import es.universidad.murcia.hospital_clinico.model.Ingreso;
import es.universidad.murcia.hospital_clinico.service.IngresoService;
import es.universidad.murcia.hospital_clinico.model.Mascota;
import es.universidad.murcia.hospital_clinico.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mascota")
public class MascotaController {
    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private IngresoService ingresoService;

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.findById(id);
        if (mascota.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mascota.get());
    }

    @GetMapping("/{idMascota}/ingreso")
    public ResponseEntity<List<Ingreso>> getIngresosByMascotaId(@PathVariable Long idMascota) {
        List<Ingreso> ingresos = ingresoService.findIngresosByMascotaId(idMascota);
        return ResponseEntity.ok(ingresos);
    }

    @PostMapping
    public ResponseEntity<Mascota> createMascota(@RequestBody Mascota mascota) {
        Mascota savedMascota = mascotaService.save(mascota);
        return ResponseEntity.ok(savedMascota);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateMascota(@PathVariable Long id) {
        mascotaService.deactivateMascota(id);
        return ResponseEntity.noContent().build();
    }
}
