package com.sd.reticula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "tareas", schema = "reticula")
public class Tarea {

    @Id
    @GeneratedValue(generator = "reticula.tareas_codigo_id_seq")
    @Column(name = "codigo_id")
    private int codigoId;
    private String nombre;
    private String descripcion;
    private String tipo;
    private int estado;
    private String carpeta;
    private Date fecha_registro;
    private Date fecha_proceso;
    private String fecha_limite;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pfisicas_id")
    private Pfisica pfisica;

    @OneToMany(mappedBy = "tarea_id")
    @JsonIgnore
    private Set<Anexo> listaAnexos;

    @Override
    public String toString() {
        return "Tarea{" +
                "codigo_id=" + codigoId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ", carpeta='" + carpeta + '\'' +
                ", fecha_registro='" + fecha_registro + '\'' +
                ", fecha_limite='" + fecha_limite + '\'' +
                ", pfisica=" + pfisica.getLpersona_id() +
                '}';
    }
}
