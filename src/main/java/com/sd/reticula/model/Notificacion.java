package com.sd.reticula.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notificaciones", schema = "reticula")
public class Notificacion {

    @Id
    @GeneratedValue(generator = "reticula.notificaciones_codigo_id_seq")
    private int codigo_id;
    private int tarea_id;
    private String mensaje;
    private boolean bleido;

    @ManyToOne
    private Usuario usuario;
}
