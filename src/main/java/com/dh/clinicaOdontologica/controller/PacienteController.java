package com.dh.clinicaOdontologica.controller;

import com.dh.clinicaOdontologica.dto.PacienteDTO;
import com.dh.clinicaOdontologica.entity.Paciente;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private IPacienteService pacienteService;

    // Buscar paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(pacienteService.buscarPorId(id));
    }

    // Listar todos los pacientes
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listarTodos() {
        return ResponseEntity.ok(pacienteService.listarTodos());
    }

    // Agregar un nuevo paciente
    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Paciente paciente) throws BadRequestException {
        return ResponseEntity.ok(pacienteService.agregar(paciente));
    }

    // Modificar y/o editar un paciente
    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody Paciente paciente) throws BadRequestException {
        return ResponseEntity.ok(pacienteService.modificar(paciente));
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        pacienteService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Paciente eliminado correctamente");
    }

}
