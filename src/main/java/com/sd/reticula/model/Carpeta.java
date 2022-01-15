package com.sd.reticula.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "carpetas", schema = "reticula")
public class Carpeta {

    @Id
    @GeneratedValue(generator = "reticula.carpetas_codigo_id_seq")
    private int codigo_id;
    private String nombre;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Pfisica cliente;
}
