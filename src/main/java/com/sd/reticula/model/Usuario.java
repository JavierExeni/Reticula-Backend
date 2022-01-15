package com.sd.reticula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="usuarios", schema = "reticula")
public class Usuario {

    @Id
    @GeneratedValue(generator = "reticula.usuarios_codigo_id_seq")
    private int codigo_id;
    private String scorreo;
    private String nombre;
    private String username;
    private String spassword;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Tarea> listaTareas;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<TrabajoTaller> listaTrabajosTaller;

    @OneToMany(mappedBy = "usuario")
    private Set<Notificacion> listaNotificaciones;

    public void addTarea(Tarea tarea){
        if(listaTareas == null){
            listaTareas = new HashSet<>();
        }
        listaTareas.add(tarea);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "codigo_id=" + codigo_id +
                ", scorreo='" + scorreo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", username='" + username + '\'' +
                ", password='" + spassword + '\'' +
                '}';
    }
}
