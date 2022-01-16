package com.sd.reticula.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "notificaciones", schema = "reticula")
public class Notificacion {

    @Id
    @GeneratedValue(generator = "reticula.notificaciones_codigo_id_seq")
    private int id;
    private int tarea_id;
    private String mensaje;
    private boolean bleido;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
}
