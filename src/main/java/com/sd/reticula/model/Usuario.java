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
    @GeneratedValue(generator = "reticula.usuario_id_seq")
    private int codigo_id;
    private String scorreo;
    private String nombre;
    private String username;
    private String spassword;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Tarea> listaTarea;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<TrabajoTaller> listaTrabajoTaller;

//    @OneToMany(mappedBy = "usuario")
//    private Set<Notificacion> listaNotificacion;

    public void addTarea(Tarea tarea){
        if(listaTarea == null){
            listaTarea = new HashSet<>();
        }
        listaTarea.add(tarea);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + codigo_id +
                ", correo='" + scorreo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", username='" + username + '\'' +
                ", password='" + spassword + '\'' +
                '}';
    }
}
