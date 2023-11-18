package com.trabajoFinal.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trabajoFinal.api.model.Usuario;
import com.trabajoFinal.api.repository.UsuarioRepository;

import com.trabajoFinal.api.model.Prestamo;
import com.trabajoFinal.api.repository.PrestamoRepository;

@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping("/usuarios")
    public List<Usuario> allUsuarios(@RequestParam Optional<String> nombre, @RequestParam Optional<String> apellido){
        if(nombre.isPresent()) {
            return usuarioRepository.findByNombre(nombre.get());
        } else if (apellido.isPresent()) {
            return usuarioRepository.findByApellido(apellido.get());
        }
        return usuarioRepository.findAll();
    }

    @GetMapping("/usuario")
    public Optional<Usuario> findByDNI(@RequestParam Optional<Integer> dni) {
        return usuarioRepository.findById(dni.get());
    }

    @PostMapping("/usuario")
    public ResponseEntity<String> createUsuario(@RequestBody Usuario usuario) {
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.OK).body("El usuario fue registrado exitosamente.");
    }

    @PutMapping("/usuario")
    public ResponseEntity<String> updateUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioFound = usuarioRepository.findById(usuario.getDniUsuario());
        if(usuarioFound.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe.");
        }
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.OK).body("El usuario fue actualizado exitosamente.");
    }

    @DeleteMapping("/usuario")
    public ResponseEntity<String> deleteUsuario(@RequestParam("id") Integer dni) {
        List<Prestamo> presatmo = prestamoRepository.findByDniUsuario(dni);
        if(presatmo.isEmpty()) {
            usuarioRepository.deleteById(dni);
            return ResponseEntity.status(HttpStatus.OK).body("El usuario fue eliminado exitosamente.");
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error el usuario se encuentra registrado en uno o mas prestamos.");
        }
    }
}
