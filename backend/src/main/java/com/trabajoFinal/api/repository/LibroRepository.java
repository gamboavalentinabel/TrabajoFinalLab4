package com.trabajoFinal.api.repository;

import com.trabajoFinal.api.model.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface LibroRepository extends JpaRepository<Libro, UUID> {
    List<Libro> findByTitulo(@Param("titulo") String titulo);
}
