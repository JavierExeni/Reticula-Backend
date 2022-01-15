package com.sd.reticula.service;

import com.sd.reticula.model.Anexo;
import com.sd.reticula.repository.AnexoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnexoService {

    @Autowired
    AnexoRepository anexoRepository;

    public List<Anexo> getAll(){
        return anexoRepository.findAll();
    }

    public void saveAnexo(Anexo objAnexo) throws Exception {
        if(objAnexo == null){
            throw new NullPointerException("El objeto a guardar no puede ser nulo");
        }
        if(objAnexo.getTitulo().equals("")){
            throw new Exception("El titulo del anexo no puede estar en blanco");
        }
        try{
            anexoRepository.saveAndFlush(objAnexo);
            System.out.println("Se guardo el anexo!!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
