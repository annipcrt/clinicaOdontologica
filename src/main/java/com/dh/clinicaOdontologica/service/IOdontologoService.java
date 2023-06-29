package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.dto.OdontologoDTO;
import com.dh.clinicaOdontologica.entity.Odontologo;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOdontologoService {
    OdontologoDTO agregar(Odontologo odontologo);
    OdontologoDTO modificar(Odontologo odontologo);
    void eliminar(Long id) throws ResourceNotFoundException;
    List<OdontologoDTO> listarTodos();
    OdontologoDTO buscarPorId(Long id);
    OdontologoDTO buscarPorMatricula(String matricula);

}
