package com.trabajoFinal.api.model;

import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "libros")
@EntityListeners(AuditingEntityListener.class)
public class Libro {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID idLibro;
    private String titulo;
    private String autor;
    private String genero;
    @Column(name = "num_paginas")
    private Integer numPaginas;
    private String sinopsis;

    public Libro() {
    }

    public Libro(UUID idLibro, String titulo, String autor, String genero, Integer numPaginas, String sinopsis) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.numPaginas = numPaginas;
        this.sinopsis = sinopsis;
    }

    public UUID getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(UUID idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(Integer numPaginas) {
        this.numPaginas = numPaginas;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    @Override
    public String toString() {
        return "Libro [id=" + this.idLibro + ", titulo=" + this.titulo + ", autor=" + this.autor + ", genero=" + this.genero + ", num_paginas= " + this.numPaginas + ", sinopsis=" + this.sinopsis +"]";
    }
}
