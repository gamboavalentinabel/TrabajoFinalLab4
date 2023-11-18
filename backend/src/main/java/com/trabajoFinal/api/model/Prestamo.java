package com.trabajoFinal.api.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "prestamos")
@EntityListeners(AuditingEntityListener.class)
public class Prestamo {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID idPrestamo;
    @Column(name = "dni_usuario")
    private Integer dniUsuario;
    @Column(name = "id_libro")
    private UUID idLibro;

    public Prestamo() {
    }

    public Prestamo(UUID idPrestamo, Integer dniUsuario, UUID idLibro) {
        this.idPrestamo = idPrestamo;
        this.dniUsuario = dniUsuario;
        this.idLibro = idLibro;
    }

    public UUID getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(UUID idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public Integer getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(Integer dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public UUID getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(UUID idLibro) {
        this.idLibro = idLibro;
    }

    @Override
    public String toString() {
        return "Prestamo [id=" + this.idPrestamo + ", dniUsuario=" + this.dniUsuario + ", id_libro=" + this.idLibro + "]";
    }
}
