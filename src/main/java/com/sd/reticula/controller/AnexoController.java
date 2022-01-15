package com.sd.reticula.controller;

import com.sd.reticula.model.Anexo;
import com.sd.reticula.service.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/anexos")
public class AnexoController {

    @Autowired
    AnexoService anexoService;

    @GetMapping
    public List<Anexo> getAll(){
        return anexoService.getAll();
    }

    @PostMapping("/insert")
    public void saveAnexo(Anexo objAnexo){
        try {
            anexoService.saveAnexo(objAnexo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
