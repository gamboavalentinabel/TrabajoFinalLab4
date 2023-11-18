package com.trabajoFinal.api.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "usuarios")
@EntityListeners(AuditingEntityListener.class)
public class Usuario {
    @Id
    private Integer dniUsuario;
    private String nombre;
    private String apellido;

    public Usuario() {
    }

    public Usuario(Integer dniUsuario, String nombre, String apellido) {
        this.dniUsuario = dniUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Integer getDniUsuario() {
        return dniUsuario;
    }

    public void setDniUsuario(Integer dniUsuario) {
        this.dniUsuario = dniUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Usuario [DNI=" + this.dniUsuario + ", nombre=" + this.nombre + ", apellido=" + this.apellido +"]";
    }
}
