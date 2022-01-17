package com.sd.reticula.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "anexos", schema = "reticula")
public class Anexo {

    @Id
    @GeneratedValue(generator = "reticula.anexos_codigo_id_seq")
    private int id;
    private String titulo;
    private String path;

//    @ManyToMany
//    @JoinColumn(name = "tarea")
//    private Tarea tarea;

//    @ManyToMany
//    @JoinColumn(name = "anexolist")
//    private Carpeta carpeta;

    @ManyToOne
    @JoinColumn(name="tarea_id")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name="carpeta_id")
    private Carpeta carpeta;
}
