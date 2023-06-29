package com.dh.clinicaOdontologica.service;

import com.dh.clinicaOdontologica.dto.PacienteDTO;
import com.dh.clinicaOdontologica.entity.Paciente;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPacienteService {
    PacienteDTO agregar(Paciente paciente);
    PacienteDTO modificar(Paciente paciente);
    void eliminar(Long id) throws ResourceNotFoundException;
    List<PacienteDTO> listarTodos();
    PacienteDTO buscarPorId(Long id);
    PacienteDTO buscarPorDni(String dni);

}
