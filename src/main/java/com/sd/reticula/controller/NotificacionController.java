package com.sd.reticula.controller;


import com.sd.reticula.model.Notificacion;
import com.sd.reticula.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    public List<Notificacion> getAll(){
        return notificacionService.getAll();
    }

    @PostMapping("/insert")
    public void saveNotificacion(@RequestBody Notificacion objNotificacion){
        try {
            System.out.println(objNotificacion.toString());
            notificacionService.saveNotificacio(objNotificacion);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
