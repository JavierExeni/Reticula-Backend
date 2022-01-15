package com.sd.reticula.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "anexos", schema = "reticula")
public class Anexo {

    @Id
    @GeneratedValue(generator = "reticula.anexos_codigo_id_seq")
    private int codigo_id;
    private String titulo;
    private String extencion;
    private String mime_type;
    private String path;

    @ManyToOne
    @JoinColumn(name = "tarea_id")
    private Tarea tarea_id;

}
