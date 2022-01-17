package com.sd.reticula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "tarea", schema = "reticula")

public class Tarea {

    @Id
    @GeneratedValue(generator = "reticula.tarea_id_seq")
    @Column(name = "id")
    private int id;
    private String nombre;
    private String descripcion;
    private int tipo;
    private int estado;
    private Date fecha_registro;
    private Date fecha_proceso;
    private Date fecha_limite;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "tarea")
    @JsonIgnore
    private Set<Anexo> listaAnexos;

    @Override
    public String toString() {
        return "Tarea{" +
                "codigo_id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha_registro='" + fecha_registro + '\'' +
                ", fecha_limite='" + fecha_limite + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "") +
                '}';
    }
}
