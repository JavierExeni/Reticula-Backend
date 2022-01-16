package com.sd.reticula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "cliente", schema = "public")
public class Cliente {

    @Id
    @GeneratedValue(generator = "public.tblcliente_id_seq")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private Set<Tarea> listaTarea;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private Set<TrabajoTaller> listaTrabajoTaller;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private Set<Establecimiento> listaEstablecimiento;

//    @OneToOne(mappedBy = "cliente")
//    private Set<Anexo> listaDocumentos;

    @OneToOne(mappedBy = "cliente")
    @JsonIgnore
    private Carpeta carpeta;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    public void addTarea(Tarea tarea){
        if(listaTarea == null){
            listaTarea = new HashSet<>();
        }
        listaTarea.add(tarea);
    }

    public void addTrabajoTaller(TrabajoTaller trabajoTaller){
        if(listaTrabajoTaller == null){
            listaTrabajoTaller = new HashSet<>();
        }
        listaTrabajoTaller.add(trabajoTaller);
    }

    @Override
    public String toString() {
        return "Cliente{id=" + id + ", nombre='" + nombre +'}';
    }

}
