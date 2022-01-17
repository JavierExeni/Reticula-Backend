package com.sd.reticula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "carpetas", schema = "reticula")
public class Carpeta {

    @Id
    @GeneratedValue(generator = "reticula.carpetas_codigo_id_seq")
    private int id;
    private String nombre;

    @OneToOne
    @JoinColumn(name = "carpeta")
    private Cliente cliente;

    @OneToMany(mappedBy = "carpeta")
    @JsonIgnore
    private Set<Anexo> listaAnexos;
}
