package com.trabajoFinal.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trabajoFinal.api.model.Libro;
import com.trabajoFinal.api.repository.LibroRepository;

import com.trabajoFinal.api.model.Prestamo;
import com.trabajoFinal.api.repository.PrestamoRepository;

@RestController
@RequestMapping("/api")
public class LibroController {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping("/libros")
    public List<Libro> getLibros(@RequestParam Optional<String> titulo){
        if(titulo.isPresent()){
            return libroRepository.findByTitulo(titulo.get());
        }
        return libroRepository.findAll();
    }

    @GetMapping("/libro")
    public Optional<Libro> getLibro(@RequestParam UUID id){
        return libroRepository.findById(id);
    }

    @PostMapping("/libro")
    public ResponseEntity<String> createLibro(@RequestBody Libro libro) {
        libroRepository.save(libro);
        return ResponseEntity.status(HttpStatus.OK).body("El libro fue agregado exitosamente.");
    }

    @PutMapping("/libro")
    public ResponseEntity<String> updateLibro(@RequestBody Libro libro) {
        Optional<Libro> libroFound = libroRepository.findById(libro.getIdLibro());
        if(libroFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El libro no existe.");
        }
        libroRepository.save(libro);
        return ResponseEntity.status(HttpStatus.OK).body("El libro fue actualizado exitosamente.");
    }

    @DeleteMapping("/libro")
    public ResponseEntity<String> deleteLibro(@RequestParam UUID id) {
        List<Prestamo> prestamos = prestamoRepository.findByIdLibro(id);
        if(prestamos.isEmpty()){
            libroRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("El libro fue eliminado con exito.");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error el libro se encuentra registrado en uno o mas prestamos.");
        }
    }
}
