package com.sd.reticula.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "tbltrabajos", schema = "tesseract")
public class TrabajoTaller {

    @Id
    @GeneratedValue(generator = "tesseract.tbltrabajos_codigo_id_seq")
    private int codigo_id;
    private String referencia;
    private String equipo;
    private String problema;
    private String solucion;
    private int estado;
    private boolean bdeleted;
    @Column(updatable = false)
    private Date fecha;
    private Date dtproceso;
    private String avance;
    private String todo;
    private double costo;
    private int tipo;
    private String fireBaseToken;
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @Override
    public String toString() {
        return "TrabajoTaller{" +
                "codigo_id=" + codigo_id +
                ", referencia='" + referencia + '\'' +
                ", equipo='" + equipo + '\'' +
                ", problema='" + problema + '\'' +
                ", solucion='" + solucion + '\'' +
                ", estado=" + estado +
                ", bdeleted=" + bdeleted +
                ", fecha=" + fecha +
                ", dtproceso=" + dtproceso +
                ", avance='" + avance + '\'' +
                ", todo='" + todo + '\'' +
                ", costo=" + costo +
                ", tipo='" + tipo + '\'' +
                ", fireBaseToken='" + fireBaseToken + '\'' +
                ", cliente=" + cliente.getNombre() +
                ", usuario=" + usuario.getUsername() +
                '}';
    }

//    @Transient
//    public String getPhotosImagePath() {
//        if (imagen == null || id == 0) return null;
//
//        return "files/taller/" + id + "/" + imagen;
//    }
}
