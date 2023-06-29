package com.dh.clinicaOdontologica.controller;

import com.dh.clinicaOdontologica.entity.Odontologo;
import com.dh.clinicaOdontologica.exceptions.BadRequestException;
import com.dh.clinicaOdontologica.exceptions.ResourceNotFoundException;
import com.dh.clinicaOdontologica.service.IOdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequestMapping("/odontologos")
public class OdontologoController {

    @Autowired
    private IOdontologoService odontologoService;

    // Buscar odontólogo por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) throws BadRequestException {
        return ResponseEntity.ok(odontologoService.buscarPorId(id));
    }

    // Listar todos los odontólogos
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok(odontologoService.listarTodos());
    }

    // Agregar un odontólogo
    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody Odontologo odontologo) throws BadRequestException {
        return ResponseEntity.ok(odontologoService.agregar(odontologo));
    }

    // Modificar y/o editar un odontólogo
    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody Odontologo odontologo) throws BadRequestException {
        return ResponseEntity.ok(odontologoService.modificar(odontologo));
    }

    // Borrar un odontólogo
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        odontologoService.eliminar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Odontólogo eliminado correctamente");
    }



}
