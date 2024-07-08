package es.universidad.murcia.hospital_clinico.controller;

import es.universidad.murcia.hospital_clinico.dto.IngresoDTO;
import es.universidad.murcia.hospital_clinico.dto.UpdateIngresoDTO;
import es.universidad.murcia.hospital_clinico.model.Ingreso;
import es.universidad.murcia.hospital_clinico.service.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingreso")
public class IngresoController {
    @Autowired
    private IngresoService ingresoService;

    @GetMapping
    public List<Ingreso> getAllIngresos() {
        return ingresoService.findAll();
    }

    @PostMapping
    public ResponseEntity<Ingreso> createIngreso(@RequestBody IngresoDTO ingresoDTO) {
        try {
            Ingreso savedIngreso = ingresoService.createIngreso(ingresoDTO);
            return ResponseEntity.ok(savedIngreso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{ingresoId}/mascota/{mascotaId}")
    public ResponseEntity<Ingreso> updateIngreso(
            @PathVariable Long ingresoId,
            @PathVariable Long mascotaId,
            @RequestBody UpdateIngresoDTO updateIngresoDTO) {
        try {
            Ingreso updatedIngreso = ingresoService.updateIngreso(ingresoId, mascotaId, updateIngresoDTO);
            return ResponseEntity.ok(updatedIngreso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngreso(@PathVariable Long id) {
        ingresoService.anulatedById(id);
        return ResponseEntity.noContent().build();
    }
}
