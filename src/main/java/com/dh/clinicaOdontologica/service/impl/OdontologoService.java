package com.dh.clinicaOdontologica.service.impl;


import com.dh.clinicaOdontologica.dto.OdontologoDTO;
import com.dh.clinicaOdontologica.entity.Odontologo;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.repository.OdontologoRepository;
import com.dh.clinicaOdontologica.service.IOdontologoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OdontologoService implements IOdontologoService {

    private final Logger logger = Logger.getLogger(OdontologoService.class);

    private OdontologoRepository odontologoRepository;
    private ObjectMapper mapper;

    @Autowired
    public OdontologoService(OdontologoRepository odontologoRepository, ObjectMapper mapper) {
        this.odontologoRepository = odontologoRepository;
        this.mapper = mapper;
    }

    @Override
    public OdontologoDTO agregar(Odontologo odontologo) {
        logger.debug("Agregamos un nuevo odontólogo.");

        Odontologo odontoloGuardado = odontologoRepository.save(odontologo);
        logger.info("Se ha creado un nuevo odontólogo");
        return mapper.convertValue(odontoloGuardado, OdontologoDTO.class);

    }

    @Override
    public OdontologoDTO modificar(Odontologo odontologo) {
        logger.debug("Modificamos un odontólogo existente.");

        Odontologo odontologoAModificar = odontologoRepository.findById(odontologo.getId()).orElse(null);
        OdontologoDTO odontologoDTOModificado = null;
        if (odontologoAModificar != null) {
            odontologoAModificar = odontologo;
            odontologoRepository.save(odontologoAModificar);
            odontologoDTOModificado = mapper.convertValue(odontologoAModificar, OdontologoDTO.class);
            logger.info("Odontólogo modificado exitosamente.");
        } else {
            logger.error("No se pueden modificar los datos porque el odontólogo no se encuentra registrado");
        }
        return odontologoDTOModificado;

    }

    @Override
    public void eliminar(Long id) throws ResourceNotFoundException {
        logger.debug("Eliminamos un odontólogo existente");

        if (buscarPorId(id) != null) {
            odontologoRepository.deleteById(id);
            logger.info("Se ha eliminado al odontólogo con id: " + id);
        } else {
            throw new ResourceNotFoundException("No se ha encontrado el odontólogo con id " + id);
        }

    }

    @Override
    public List<OdontologoDTO> listarTodos() {
        logger.debug("Se van a listar todos los odontólogos");

        List<OdontologoDTO> odontologos = odontologoRepository.findAll().stream()
                .map(o -> mapper.convertValue(o, OdontologoDTO.class)).toList();
        ;
        logger.info("Se han listado todos los odontólogos");
        return odontologos;

    }

    @Override
    public OdontologoDTO buscarPorId(Long id) {
        logger.debug("Se va a buscar un odontólogo por su id");

        Odontologo odontologoBuscado = odontologoRepository.findById(id).orElse(null);
        OdontologoDTO odontologoDTO = null;
        if (odontologoBuscado != null) {
            odontologoDTO = mapper.convertValue(odontologoBuscado, OdontologoDTO.class);
            logger.info("Se encontró al odontólogo con el id " + id);
        } else {
            logger.error("El id proporcionado no está registrado en la base de datos");
        }
        return odontologoDTO;

    }

    @Override
    public OdontologoDTO buscarPorMatricula(String matricula) {
        logger.debug("Se va a buscar un odontólogo por su matrícula");

        Odontologo odontologoBuscado = odontologoRepository.findByMatricula(matricula).orElse(null);
        OdontologoDTO odontologoDTO = null;
        if (odontologoBuscado != null) {
            odontologoDTO = mapper.convertValue(odontologoBuscado, OdontologoDTO.class);
            logger.info("Se encontró al odontólogo con la matrícula " + matricula);
        } else {
            logger.error("La matrícula proporcionada no está registrada en la base de datos");
        }

        return odontologoDTO;
    }


}
