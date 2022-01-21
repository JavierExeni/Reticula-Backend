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
    private String longitud;
    private String latitud;

    @ManyToOne
    @JoinColumn(name = "listaEstablecimiento")
    private Cliente cliente;
}
