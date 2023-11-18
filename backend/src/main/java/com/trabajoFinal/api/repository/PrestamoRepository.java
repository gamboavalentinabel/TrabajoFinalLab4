package com.trabajoFinal.api.repository;

import com.trabajoFinal.api.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, UUID> {
    List<Prestamo> findByIdPrestamo(@Param("idPrestamo") UUID idPrestamo);
    List<Prestamo> findByIdLibro(@Param("idLibro") UUID idLibro);
    List<Prestamo> findByDniUsuario(@Param("idUsuario") Integer dniUsuario);
}
