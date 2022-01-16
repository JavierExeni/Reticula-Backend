package com.sd.reticula.service;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.repository.ClienteRepository;
import com.sd.reticula.repository.TrabajoTallerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TrabajoTallerService {

    @Autowired
    TrabajoTallerRepository trabajoTallerRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<TrabajoTaller> getAll() {
        List<TrabajoTaller> lista = trabajoTallerRepository.findAllByOrderByEstado();
        lista.removeIf(obj -> obj.getEstado() == 3);
        return lista;
    }

    public List<TrabajoTaller> getAllByCliente(int cliente_id) {
        Cliente objAux = null;
        List<Cliente> listaClientes = clienteRepository.findAll();
        for (Cliente obj : listaClientes) {
            if (obj.getId() == cliente_id) {
                objAux = obj;
                break;
            }
        }
        List<TrabajoTaller> listaTrabajos = trabajoTallerRepository.findAllByClienteOrderByEstado(objAux);
        listaTrabajos.removeIf(obj -> obj.getEstado() == 3);
        return listaTrabajos;
    }

    public List<TrabajoTaller> getAllByEstado(int estado) {
        return trabajoTallerRepository.findAllByEstado(estado);
    }

    public Optional<TrabajoTaller> getTrabajoTallerById(int id) {
        Optional<TrabajoTaller> obj = trabajoTallerRepository.findById(id);
        if (obj == null) {
            throw new NullPointerException("Esta tratando de obtener un objeto nulo");
        }
        return obj;
    }

    @Transactional
    public void saveTrabajoTaller(TrabajoTaller obj) throws Exception {
        if (obj == null) {
            throw new NullPointerException("El objeto a guardar es nulo");
        }

        if (obj.getCliente() == null) {
            throw new NullPointerException("El cliente no puede ser null");
        }

        if (obj.getReferencia().isEmpty()) {
            throw new Exception("La persona de referencia no puede esta vacia");
        }

        System.out.println(obj.toString());

        try {
            if (obj.getCodigo_id() == 0) {
                obj.setFecha(Calendar.getInstance().getTime());
            } else {
                obj.setDtproceso(Calendar.getInstance().getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        trabajoTallerRepository.saveAndFlush(obj);
    }
}
