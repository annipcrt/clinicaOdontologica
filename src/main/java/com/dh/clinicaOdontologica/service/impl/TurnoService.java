package com.dh.clinicaOdontologica.service.impl;

import com.dh.clinicaOdontologica.dto.OdontologoDTO;
import com.dh.clinicaOdontologica.dto.PacienteDTO;
import com.dh.clinicaOdontologica.dto.TurnoDTO;
import com.dh.clinicaOdontologica.entity.Turno;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.repository.TurnoRepository;
import com.dh.clinicaOdontologica.service.IOdontologoService;
import com.dh.clinicaOdontologica.service.IPacienteService;
import com.dh.clinicaOdontologica.service.ITurnoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService implements ITurnoService {

    private final Logger logger = Logger.getLogger(TurnoService.class);

    private TurnoRepository turnoRepository;

    private IPacienteService pacienteService;

    private IOdontologoService odontologoService;

    ObjectMapper mapper;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository, IPacienteService pacienteService, IOdontologoService odontologoService, ObjectMapper mapper) {
        this.turnoRepository = turnoRepository;
        this.pacienteService = pacienteService;
        this.odontologoService = odontologoService;
        this.mapper = mapper;
    }

    @Override
    public TurnoDTO agregar(Turno turno) throws BadRequestException {
        logger.debug("Agregamos un nuevo turno");

        Turno turnoAgregado = null;
        TurnoDTO turnoDTOAgregado = null;
        PacienteDTO pacienteEncontrado = pacienteService.buscarPorId(turno.getPaciente().getId());
        OdontologoDTO odontologoEncontrado = odontologoService.buscarPorId(turno.getOdontologo().getId());

        // Se verifica que el paciente y el odontólogo realmente existan
        if (pacienteEncontrado == null || odontologoEncontrado == null) {

            if (pacienteEncontrado == null && odontologoEncontrado == null) {
                throw new BadRequestException("Ni el paciente ni el odontólogo se encuentran registrados en la base de datos.");
            } else if (pacienteEncontrado == null) {
                throw new BadRequestException("El paciente no se encuentra registrado en la base de datos");
            } else {
                throw new BadRequestException("El odontólogo no se encuentra registrado en la base de datos.");
            }

        } else {

            turnoAgregado = turnoRepository.save(turno);
            turnoDTOAgregado = mapper.convertValue(turnoAgregado, TurnoDTO.class);
            logger.info("Se ha creado un nuevo turno");

        }
        return turnoDTOAgregado;
    }

    @Override
    public TurnoDTO modificar(Turno turno) {
        logger.debug("Modificamos un turno existente.");

        Turno turnoAModificar = turnoRepository.findById(turno.getId()).orElse(null);
        TurnoDTO turnoDTOAModificar = null;

        if (turnoAModificar != null) {
            turnoAModificar = turno;
            turnoRepository.save(turnoAModificar);
            turnoDTOAModificar = mapper.convertValue(turnoAModificar, TurnoDTO.class);
            logger.info("Turno modificado exitosamente");
        } else {
            logger.error("No se puede modificar los datos porque no está registrado el turno");
        }
        return turnoDTOAModificar;
    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {
        logger.debug("Eliminamos un turno existente.");

        if (buscarPorId(id) != null) {
            turnoRepository.deleteById(id);
            logger.info("Se ha eliminado el turno con el id " + id);
        } else {
            throw new ResourceNotFoundException("No se ha encontrado el turno con el id " + id);
        }
    }

    @Override
    public List<TurnoDTO> listarTodos() {
        logger.debug("Se van a listar todos los turnos");

        List<TurnoDTO> turnos = turnoRepository.findAll().stream()
                .map(t -> mapper.convertValue(t, TurnoDTO.class)).toList();

        logger.info("Se han listado todos los turnos.");
        return turnos;

    }

    @Override
    public TurnoDTO buscarPorId(Long id) {
        logger.debug("Buscamos un turno por su id.");

        Turno turnoBuscado = turnoRepository.findById(id).orElse(null);
        TurnoDTO turnoDTOBuscado = null;

        if (turnoBuscado != null) {
            turnoDTOBuscado = mapper.convertValue(turnoBuscado, TurnoDTO.class);
            logger.info("Se encontró el turno con el id " + id);
        } else {
            logger.error("El id proporcionado no se encuentra en la base de datos.");
        }

        return turnoDTOBuscado;
    }
}
