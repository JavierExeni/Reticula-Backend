package com.sd.reticula.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "establecimientos", schema = "reticula")
public class Establecimiento {

    @Id
    @GeneratedValue(generator = "reticula.establecimientos_codigo_id_seq")
    private int codigo_id;
    private String direccion;
    private String telefono;
    private int longitud;
    private int latitud;

    @ManyToOne
    @JoinColumn(name = "pfisica_id")
    private Pfisica cliente;
}
