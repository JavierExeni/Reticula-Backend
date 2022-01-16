package com.sd.reticula.service;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.repository.ClienteRepository;
import com.sd.reticula.repository.TrabajoTallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TrabajoTallerService {

    // Type work
    @Transient
    public final int SERVIDOR = 0;
    @Transient
    public final int PC = 1;
    @Transient
    public final int PORTATIL = 2;
    @Transient
    public final int DISPOSITIVO = 3;
    @Transient
    public final int OTRO = 4;

    // State work
    @Transient
    public final int PENDIENTE = 0;
    @Transient
    public final int ANALISIS = 1;
    @Transient
    public final int FINALIZADO = 2;
    @Transient
    public final int ENTREGADO = 3;
    @Transient
    public final int RETRASADO = -1;

    @Autowired
    TrabajoTallerRepository trabajoTallerRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<TrabajoTaller> getAll() {
        List<TrabajoTaller> lista = trabajoTallerRepository.findAllByOrderByEstado();
        lista.removeIf(obj -> obj.getEstado() == ENTREGADO);
        return lista;
    }

    public List<TrabajoTaller> getAllByCliente(int cliente_id) {
        Optional<Cliente> objCliente = clienteRepository.findById(cliente_id);
        if (objCliente.isPresent()) {
            Cliente objAux = objCliente.get();
            List<TrabajoTaller> listaTrabajos = trabajoTallerRepository.findAllByClienteOrderByEstado(objAux);
            listaTrabajos.removeIf(obj -> obj.getEstado() == ENTREGADO);
            return listaTrabajos;
        }
        return null;
    }

    public TrabajoTaller getById(int id) {
        Optional<TrabajoTaller> objTrabajo = trabajoTallerRepository.findById(id);
        if (objTrabajo.isPresent()) {
            return objTrabajo.get();
        }
        return null;
    }

    public List<TrabajoTaller> getAllByEstado(String estado) {
        if (validateStateWork(estado)) {
            return trabajoTallerRepository.findAllByEstado(getValueStateWork(estado));
        }
        return null;
    }

    public List<TrabajoTaller> getAllByType(String type) {
        if (validateTypeWork(type)) {
            return trabajoTallerRepository.findAllByTipo(getValueTypeWork(type));
        }
        return null;
    }

    private int getValueTypeWork(String state) {
        switch (state) {
            case "servidor":
                return SERVIDOR;
            case "pc":
                return PC;
            case "portatil":
                return PORTATIL;
            case "dispositivo":
                return DISPOSITIVO;
            case "otro":
                return OTRO;
            default:
                return 0;
        }
    }

    private boolean validateTypeWork(String state) {
        if (state.toLowerCase().equals("servidor") || state.toLowerCase().equals("pc") ||
                state.toLowerCase().equals("portatil") || state.toLowerCase().equals("dispositivo") ||
                state.toLowerCase().equals("otro")) {
            return true;
        }
        return false;
    }

    private int getValueStateWork(String state) {
        switch (state) {
            case "pendiente":
                return PENDIENTE;
            case "analisis":
                return ANALISIS;
            case "finalizado":
                return FINALIZADO;
            case "entregado":
                return ENTREGADO;
            case "retrasado":
                return RETRASADO;
            default:
                return 0;
        }
    }

    private boolean validateStateWork(String state) {
        if (state.toLowerCase().equals("pendiente") || state.toLowerCase().equals("analisis") ||
                state.toLowerCase().equals("finalizado") || state.toLowerCase().equals("entregado") ||
                state.toLowerCase().equals("retrasado")) {
            return true;
        }
        return false;
    }

    public Optional<TrabajoTaller> getTrabajoTallerById(int id) {
        Optional<TrabajoTaller> obj = trabajoTallerRepository.findById(id);
        if (obj.isPresent()) {
            return obj;
        }
        return null;
    }

    @Transactional
    public TrabajoTaller saveTrabajoTaller(TrabajoTaller obj) {
        if (validateWork(obj)) {
            if (obj.getId() == 0) {
                obj.setFecha(Calendar.getInstance().getTime());
            } else {
                obj.setDtproceso(Calendar.getInstance().getTime());
            }
            return trabajoTallerRepository.saveAndFlush(obj);
        } else {
            return null;
        }
    }

    private boolean validateWork(TrabajoTaller objTrabajo) {
        return objTrabajo != null && objTrabajo.getCliente() != null && !objTrabajo.getReferencia().isEmpty();
    }
}
