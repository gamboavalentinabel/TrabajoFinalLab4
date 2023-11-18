package com.trabajoFinal.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trabajoFinal.api.model.Prestamo;
import com.trabajoFinal.api.repository.PrestamoRepository;

@RestController
@RequestMapping("/api")
public class PrestamoController {
    @Autowired
    private PrestamoRepository repository;


    @GetMapping("/prestamos")
    public List<Prestamo> allPresatmo(@RequestParam Optional<UUID> idLibro, @RequestParam Optional<Integer> dniUsuario){
        if(idLibro.isPresent()) {
            return repository.findByIdLibro(idLibro.get());
        } else if (dniUsuario.isPresent()) {
            return repository.findByDniUsuario(dniUsuario.get());
        }
        return repository.findAll();
    }

    @GetMapping("/prestamo")
    public Optional<Prestamo> findByIdPrestamo(@RequestParam Optional<UUID> id) {
        return repository.findById(id.get());
    }

    @PostMapping("/prestamo")
    public ResponseEntity<String> createPrestamo(@RequestBody Prestamo prestamo) {
        repository.save(prestamo);

        return ResponseEntity.status(HttpStatus.OK).body("El prestamo fue registrado exitosamente.");
    }

    @PutMapping("/prestamo")
    public ResponseEntity<String> updatePrestamo(@RequestBody Prestamo prestamo) {
        Optional<Prestamo> prestamoFound = repository.findById(prestamo.getIdPrestamo());
        if(prestamoFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El prestamo no existe.");
        }
        repository.save(prestamo);
        return ResponseEntity.status(HttpStatus.OK).body("El prestamo fue actualizado exitosamente.");
    }

    @DeleteMapping("/prestamo")
    public ResponseEntity<String> deletePrestamo(@RequestParam UUID id) {
        repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("El prestamo fue eliminado exitosamente.");
    }
}
