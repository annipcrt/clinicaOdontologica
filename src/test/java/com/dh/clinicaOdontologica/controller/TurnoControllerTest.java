package com.dh.clinicaOdontologica.controller;

import com.dh.clinicaOdontologica.dto.TurnoDTO;
import com.dh.clinicaOdontologica.entity.Odontologo;
import com.dh.clinicaOdontologica.entity.Paciente;
import com.dh.clinicaOdontologica.entity.Turno;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.service.impl.OdontologoService;
import com.dh.clinicaOdontologica.service.impl.PacienteService;
import com.dh.clinicaOdontologica.service.impl.TurnoService;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import util.Jsons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoControllerTest {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @Order(1)
    void agregarUnTurno() throws Exception{
        Paciente pacientePrueba = new Paciente("Pepe","Perez","1232456","Calle 2", LocalDate.of(2022,05,01));
        pacienteService.agregar(pacientePrueba);

        Odontologo odontoPrueba = new Odontologo("Juan","Lopez","12354");
        odontologoService.agregar(odontoPrueba);

        Turno turnoPrueba = new Turno();
        turnoPrueba.setFechaHora(LocalDateTime.of(2023,1,25,10,30));
        turnoPrueba.setPaciente(pacientePrueba);
        turnoPrueba.setOdontologo(odontoPrueba);
        turnoService.agregar(turnoPrueba);

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.post("/turnos/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Jsons.asJsonString(turnoPrueba)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }

    @Test
    @Order(2)
    void modificarUnTurno() throws Exception{
        Paciente pacienteTest = new Paciente("Coco","Gonzalez","23452","Calle 2", LocalDate.of(2022,9,01));
        pacienteService.agregar(pacienteTest);

        Odontologo odontoTest = new Odontologo("Juan","Sanchez","fdf56+");
        odontologoService.agregar(odontoTest);

        Turno turnoTest = new Turno();
        TurnoDTO turnoTestDTO= turnoService.buscarPorId(Long.valueOf(1));
        turnoTest.setId(turnoTestDTO.getId());
        turnoTest.setFechaHora(LocalDateTime.of(2023,1,25,10,30));
        turnoTest.setPaciente(pacienteTest);
        turnoTest.setOdontologo(odontoTest);
        turnoService.modificar(turnoTest);

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.put("/turnos/modificar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Jsons.asJsonString(turnoTest)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(respuesta.getResponse().getContentAsString().isEmpty());


    }

    @Test
    @Order(3)
    void buscarTurnoPorId() throws Exception {

        TurnoDTO turnoTestB =turnoService.buscarPorId(1L);

        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Jsons.asJsonString(turnoTestB)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(respuesta.getResponse().getContentAsString().isEmpty());

    }

    @Test
    @Order(4)
    void listarTodosLosTurnos() throws Exception{

        List<TurnoDTO> turnosTest= turnoService.listarTodos();
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Jsons.asJsonString(turnosTest)))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        Assert.assertFalse(respuesta.getResponse().getContentAsString().isEmpty());

    }

    @Test
    @Order(5)
    void eliminar() throws Exception {

//        turnoService.eliminar(1L);
//
//
//        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.delete("/turnos/borrar/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andDo(MockMvcResultHandlers.print())
//                        .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();
//        Assert.assertFalse(respuesta.getResponse().getContentAsString().isEmpty());

    }
}