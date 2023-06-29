package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.dto.TurnoDTO;
import com.dh.clinicaOdontologica.entity.Turno;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITurnoService {
    TurnoDTO agregar(Turno turno) throws BadRequestException;
    TurnoDTO modificar(Turno turno);
    void eliminar(Long id) throws ResourceNotFoundException;
    List<TurnoDTO> listarTodos();
    TurnoDTO buscarPorId(Long id);

}
