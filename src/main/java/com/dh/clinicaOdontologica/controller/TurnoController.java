package com.dh.clinicaOdontologica.controller;

import com.dh.clinicaOdontologica.dto.TurnoDTO;
import com.dh.clinicaOdontologica.entity.Turno;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.service.ITurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    // Buscar turno por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(turnoService.buscarPorId(id));
    }

    // Listar todos los turnos
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<?>> listarTodos() {
        return ResponseEntity.ok(turnoService.listarTodos());
    }

    // Agregar un nuevo turno
    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Turno turno) throws BadRequestException {
        return ResponseEntity.ok(turnoService.agregar(turno));

    }

    // Modificar y/o editar un turno
    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody Turno turno) throws BadRequestException {
        return ResponseEntity.ok(turnoService.modificar(turno));
    }

    // Eliminar un turno
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        turnoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Turno eliminado correctamente");
    }
}
