package com.sd.reticula.service;

import com.sd.reticula.model.Carpeta;
import com.sd.reticula.repository.CarpetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpetaService {

    @Autowired
    private CarpetaRepository carpetaRepository;

    public List<Carpeta> getAll(){
        return carpetaRepository.findAll();
    }

    public void createCarpeta(Carpeta carpeta) throws Exception {
        if(carpeta.getNombre().equals("")){
            throw new Exception("El nombre de la carpeta no puede estar en blanco");
        }

        if(carpeta.getCliente() == null){
            throw new NullPointerException("El cliente para el cual se creara la carpeta no puede ser nulo");
        }

        try{
            carpetaRepository.saveAndFlush(carpeta);
            System.out.println("Se creo!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
