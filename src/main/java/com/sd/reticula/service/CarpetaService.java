package com.sd.reticula.service;

import com.sd.reticula.model.Carpeta;
import com.sd.reticula.repository.CarpetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpetaService {

    @Autowired
    private CarpetaRepository carpetaRepository;

    public List<Carpeta> getAll(){
        return carpetaRepository.findAll();
    }

    public Carpeta createCarpeta(Carpeta carpeta) {
        if (validateFolder(carpeta)) {
            return carpetaRepository.saveAndFlush(carpeta);
        } else {
            return null;
        }
    }

    private boolean validateFolder(Carpeta carpeta) {
        return !carpeta.getNombre().equals("") && carpeta.getCliente() != null;
    }
}
