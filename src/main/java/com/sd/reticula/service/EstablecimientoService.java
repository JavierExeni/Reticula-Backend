package com.sd.reticula.service;

import com.sd.reticula.model.Establecimiento;
import com.sd.reticula.model.Cliente;
import com.sd.reticula.repository.EstablecimientoRepository;
import com.sd.reticula.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establishmentRepository;

    @Autowired
    private ClienteRepository clientRepository;

    public List<Establecimiento> getAll(){
        return establishmentRepository.findAll();
    }

    public Establecimiento getById(int id){
        Optional<Establecimiento> establishment = establishmentRepository.findById(id);
        if (establishment.isPresent()) {
            return establishment.get();
        }
        return null;
    }

    public List<Establecimiento> getRecordsByCliente(int cliente_id){
        Optional<Cliente> clientObj = clientRepository.findById(cliente_id);
        if (clientObj.isPresent()) {
            Cliente client = clientObj.get();
            List<Establecimiento> EstablishmentList = establishmentRepository.findAllByCliente(client);
            return EstablishmentList;
        }
        return null;
    }

    public Establecimiento saveEstablishment(Establecimiento objEstablishment){
        if (validateEstablishment(objEstablishment)) {
            return establishmentRepository.saveAndFlush(objEstablishment);
        }
        return null;
    }

    public boolean validateEstablishment(Establecimiento objEstablishment) {
        return objEstablishment.getCliente() != null && !objEstablishment.getDireccion().equals("") &&
                !objEstablishment.getLatitud().equals("") && !objEstablishment.getLongitud().equals("");
    }
}
