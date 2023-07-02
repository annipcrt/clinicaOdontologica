package com.dh.clinicaOdontologica.service.impl;


import com.dh.clinicaOdontologica.dto.TurnoDTO;
import com.dh.clinicaOdontologica.entity.Odontologo;
import com.dh.clinicaOdontologica.entity.Paciente;
import com.dh.clinicaOdontologica.entity.Turno;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;

    @Test
    void agregar() throws BadRequestException {

        Paciente pacientePrueba = new Paciente("Pepe", "Perez", "1232456", "Calle 2", LocalDate.of(2022, 05, 01));

        pacienteService.agregar(pacientePrueba);

        Odontologo odontoPrueba = new Odontologo("Juan", "Lopez", "12354");
        odontologoService.agregar(odontoPrueba);

        Turno turnoPrueba = new Turno();
        turnoPrueba.setFechaHora(LocalDateTime.of(2023, 1, 25, 10, 30));
        turnoPrueba.setPaciente(pacientePrueba);
        turnoPrueba.setOdontologo(odontoPrueba);
        TurnoDTO resultado = turnoService.agregar(turnoPrueba);

        Assertions.assertEquals(turnoPrueba.getId(), resultado.getId());

    }

    @Test
    void eliminar() throws ResourceNotFoundException {
        turnoService.buscarPorId(1L);
        turnoService.eliminar(1L);
        TurnoDTO turnoDel = turnoService.buscarPorId(1L);
        Assertions.assertEquals(null, turnoDel);
    }

}