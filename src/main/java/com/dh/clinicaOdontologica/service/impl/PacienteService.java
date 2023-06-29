package com.dh.clinicaOdontologica.service.impl;

import com.dh.clinicaOdontologica.dto.PacienteDTO;
import com.dh.clinicaOdontologica.entity.Paciente;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.repository.PacienteRepository;
import com.dh.clinicaOdontologica.service.IPacienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    private final Logger logger = Logger.getLogger(PacienteService.class);

    private PacienteRepository pacienteRepository;
    ObjectMapper mapper;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, ObjectMapper mapper) {
        this.pacienteRepository = pacienteRepository;
        this.mapper = mapper;
    }

    @Override
    public PacienteDTO agregar(Paciente paciente) {
        logger.debug("Agregamos un nuevo odontólogo");

        Paciente pacienteAgregado = pacienteRepository.save(paciente);
        logger.info("Se ha creado un nuevo paciente");
        return mapper.convertValue(pacienteAgregado, PacienteDTO.class);

    }

    @Override
    public PacienteDTO modificar(Paciente paciente) {
        logger.debug("Modificamos un paciente existente");

        Paciente pacienteAModificar = pacienteRepository.findById(paciente.getId()).orElse(null);
        PacienteDTO pacienteDTOAModificar = null;
        if (pacienteAModificar != null) {
            pacienteAModificar = paciente;
            pacienteRepository.save(pacienteAModificar);
            pacienteDTOAModificar = mapper.convertValue(pacienteAModificar, PacienteDTO.class);
            logger.info("Paciente modificado exitosamente");
        } else {
            logger.error("No se pueden modificar los datos porque el paciente no se encuentra registrado");
        }

        return pacienteDTOAModificar;

    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {
        logger.debug("Eliminamos un paciente existente.");

        if (buscarPorId(id) != null) {
            pacienteRepository.deleteById(id);
            logger.info("Se ha eliminado al paciente con el id " + id);
        } else {
            throw new ResourceNotFoundException("No se ha encontrado al paciente con el id " + id);
        }

    }

    @Override
    public List<PacienteDTO> listarTodos() {
        logger.debug("Se van a listar todos los pacientes.");

        List<PacienteDTO> pacientes = pacienteRepository.findAll().stream()
                .map(p -> mapper.convertValue(p, PacienteDTO.class)).toList();
        ;
        logger.info("Se han listado todos los pacientes.");
        return pacientes;

    }

    @Override
    public PacienteDTO buscarPorId(Long id) {
        logger.debug("Buscamos un paciente por su id");

        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteDTO pacienteDTOBuscado = null;
        if (pacienteBuscado != null) {
            pacienteDTOBuscado = mapper.convertValue(pacienteBuscado, PacienteDTO.class);
            logger.info("Se encontró al paciente con el id " + id);
        } else {
            logger.error("El id proporcionado no se encuentra en la base de datos.");
        }
        return pacienteDTOBuscado;
    }

    @Override
    public PacienteDTO buscarPorDni(String dni) {
        logger.debug("Buscamos un paciente por su dni.");

        Paciente pacienteBuscado = pacienteRepository.findByDni(dni).orElse(null);
        PacienteDTO pacienteDTOBuscado = null;
        if (pacienteBuscado != null) {
            pacienteDTOBuscado = mapper.convertValue(pacienteBuscado, PacienteDTO.class);
            logger.info("Se encontró al paciente con el dni " + dni);
        } else {
            logger.error("El dni proporcionado no se encuentra en la base de datos");
        }
        return pacienteDTOBuscado;
    }
}
