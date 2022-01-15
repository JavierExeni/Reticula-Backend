package com.sd.reticula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tblpfisicas", schema = "public")
public class Pfisica {

    @Id
    @GeneratedValue(generator = "public.tblpersonas_lpersona_id_seq")
    private int lpersona_id;

    @Column(name = "spersona_nm")
    private String spersonanm;

    @OneToMany(mappedBy = "pfisica")
    @JsonIgnore
    private Set<Tarea> listaTareas;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private Set<TrabajoTaller> listaTrabajosTaller;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private Set<Establecimiento> listaEstablecimientos;

//    @OneToOne(mappedBy = "cliente")
//    private Set<Anexo> listaDocumentos;

    @OneToOne(mappedBy = "cliente")
    @JsonIgnore
    private Carpeta carpeta;

    public void addTarea(Tarea tarea){
        if(listaTareas == null){
            listaTareas = new HashSet<>();
        }
        listaTareas.add(tarea);
    }

    public void addTrabajoTaller(TrabajoTaller trabajoTaller){
        if(listaTrabajosTaller == null){
            listaTrabajosTaller = new HashSet<>();
        }
        listaTrabajosTaller.add(trabajoTaller);
    }

}
