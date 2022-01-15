package com.sd.reticula.service;

import com.sd.reticula.model.Pfisica;
import com.sd.reticula.repository.PfisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PfisicaService {

    @Autowired
    private PfisicaRepository pfisicaRepository;

    public List<Pfisica> getAll(){
        List<Pfisica> listaClientes = pfisicaRepository.findAll(Sort.by(Sort.Direction.ASC,"spersonanm"));
        return listaClientes;
    }

    @Transactional
    public void saveCliente(Pfisica objCliente) {
        if(objCliente == null){
            throw new NullPointerException("El objeto a insertar no puede ser nulo");
        }

        try {
            System.out.println("Guarda al cliente nuevo");
            pfisicaRepository.saveAndFlush(objCliente);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
