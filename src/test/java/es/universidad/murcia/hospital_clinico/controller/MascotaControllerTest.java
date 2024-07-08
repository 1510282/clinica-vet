package es.universidad.murcia.hospital_clinico.controller;

import es.universidad.murcia.hospital_clinico.controller.MascotaController;
import es.universidad.murcia.hospital_clinico.model.Estado;
import es.universidad.murcia.hospital_clinico.model.Mascota;
import es.universidad.murcia.hospital_clinico.model.Ingreso;
import es.universidad.murcia.hospital_clinico.service.MascotaService;
import es.universidad.murcia.hospital_clinico.service.IngresoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
public class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaService mascotaService;

    @MockBean
    private IngresoService ingresoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetMascotaById() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);

        Mockito.when(mascotaService.findById(1L)).thenReturn(Optional.of(mascota));

        mockMvc.perform(get("/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.especie").value("Perro"));
    }

    @Test
    public void testGetMascotaByIdInactiva() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);
        mascota.setActivo(false);

        Mockito.when(mascotaService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/mascota/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetMascotaByIdActiva() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);
        mascota.setActivo(true);

        Mockito.when(mascotaService.findById(1L)).thenReturn(Optional.of(mascota));

        mockMvc.perform(get("/mascota/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.especie").value("Perro"));
    }

    @Test
    public void testGetIngresosByMascotaId() throws Exception {
        Ingreso ingreso = new Ingreso();
        ingreso.setId(1L);
        ingreso.setEstado(Estado.ALTA);
        ingreso.setMascota(new Mascota());

        Mockito.when(ingresoService.findIngresosByMascotaId(1L)).thenReturn(Arrays.asList(ingreso));

        mockMvc.perform(get("/mascota/1/ingreso"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    public void testCreateMascota() throws Exception {
        Mascota mascota = new Mascota("Perro", "Labrador", 3, "12345", "12345678A");
        mascota.setId(1L);

        Mockito.when(mascotaService.save(Mockito.any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(post("/mascota")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }



    @Test
    public void testDeactivateMascota() throws Exception {
        Mockito.doNothing().when(mascotaService).deactivateMascota(1L);

        mockMvc.perform(delete("/mascota/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(mascotaService, Mockito.times(1)).deactivateMascota(1L);
    }
}
