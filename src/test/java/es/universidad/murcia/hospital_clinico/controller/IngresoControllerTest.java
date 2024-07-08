package es.universidad.murcia.hospital_clinico.controller;

import es.universidad.murcia.hospital_clinico.dto.IngresoDTO;
import es.universidad.murcia.hospital_clinico.dto.UpdateIngresoDTO;
import es.universidad.murcia.hospital_clinico.model.Estado;
import es.universidad.murcia.hospital_clinico.model.Ingreso;
import es.universidad.murcia.hospital_clinico.model.Mascota;
import es.universidad.murcia.hospital_clinico.service.IngresoService;
import es.universidad.murcia.hospital_clinico.service.MascotaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IngresoController.class)
public class IngresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngresoService ingresoService;

    @MockBean
    private MascotaService mascotaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllIngresos() throws Exception {
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setEstado(Estado.ALTA);
        Mockito.when(ingresoService.findAll()).thenReturn(Arrays.asList(ingreso));

        mockMvc.perform(get("/ingreso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testCreateIngreso() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);
        IngresoDTO ingresoDTO = new IngresoDTO();
        ingresoDTO.setMascotaId(1L);
        ingresoDTO.setFechaAltaIngreso(LocalDate.now());
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);

        Mockito.when(mascotaService.findById(1L)).thenReturn(Optional.of(mascota));
        Mockito.when(ingresoService.createIngreso(Mockito.any(IngresoDTO.class))).thenReturn(ingreso);

        mockMvc.perform(post("/ingreso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateIngreso() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setMascota(mascota);
        UpdateIngresoDTO updateIngresoDTO = new UpdateIngresoDTO();
        updateIngresoDTO.setEstado(Estado.FINALIZADO);

        Mockito.when(mascotaService.findById(1L)).thenReturn(Optional.of(mascota));
        Mockito.when(ingresoService.updateIngreso(Mockito.eq(1L), Mockito.eq(1L), Mockito.any(UpdateIngresoDTO.class))).thenReturn(ingreso);

        mockMvc.perform(put("/ingreso/1/mascota/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateIngresoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testAnulateIngreso() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setMascota(mascota);
        ingreso.setEstado(Estado.ALTA);

        Mockito.when(ingresoService.findById(1L)).thenReturn(Optional.of(ingreso));

        Mockito.doNothing().when(ingresoService).anulatedById(1L);

        mockMvc.perform(delete("/ingreso/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(ingresoService, Mockito.times(1)).anulatedById(1L);
    }

    @Test
    public void testCreateIngresoMascotaInactiva() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);
        mascota.setActivo(false);
        IngresoDTO ingresoDTO = new IngresoDTO();
        ingresoDTO.setMascotaId(1L);
        ingresoDTO.setFechaAltaIngreso(LocalDate.now());

        Mockito.when(mascotaService.findById(1L)).thenReturn(Optional.of(mascota));

        Mockito.when(ingresoService.createIngreso(Mockito.any(IngresoDTO.class)))
                .thenThrow(new IllegalArgumentException("Mascota no encontrada o inactiva con ID: 1"));

        mockMvc.perform(post("/ingreso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingresoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateIngresoAnulado() throws Exception {
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setEstado(Estado.ANULADO);

        Mockito.when(ingresoService.updateIngreso(Mockito.eq(1L), Mockito.eq(1L), Mockito.any(UpdateIngresoDTO.class)))
                .thenThrow(new IllegalArgumentException("Ingreso no encontrado o anulado con ID: 1"));

        UpdateIngresoDTO updateIngresoDTO = new UpdateIngresoDTO();
        updateIngresoDTO.setEstado(Estado.FINALIZADO);

        mockMvc.perform(put("/ingreso/1/mascota/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateIngresoDTO)))
                .andExpect(status().isBadRequest());
    }

    //update ingreso no anulado

    @Test
    public void testUpdateIngresoNoAnulado() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setMascota(mascota);
        UpdateIngresoDTO updateIngresoDTO = new UpdateIngresoDTO();
        updateIngresoDTO.setEstado(Estado.FINALIZADO);

        Mockito.when(mascotaService.findById(1L)).thenReturn(Optional.of(mascota));
        Mockito.when(ingresoService.updateIngreso(Mockito.eq(1L), Mockito.eq(1L), Mockito.any(UpdateIngresoDTO.class))).thenReturn(ingreso);

        mockMvc.perform(put("/ingreso/1/mascota/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateIngresoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
