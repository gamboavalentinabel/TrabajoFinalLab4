package com.trabajoFinal.api.repository;

import com.trabajoFinal.api.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByNombre(@Param("nombre") String nombre);
    List<Usuario> findByApellido(@Param("apellido") String apellido);
}
