package com.sd.reticula.controller;


import com.sd.reticula.model.Establecimiento;
import com.sd.reticula.model.Notificacion;
import com.sd.reticula.service.NotificacionService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public List<Notificacion> getAll(){
        return notificacionService.getAll();
    }

    @PostMapping("/insert")
    public Object saveNotification(@RequestBody Notificacion notification){
        System.out.println(notification.toString());
        JSONObject obje = new JSONObject();
        try {
            Notificacion newNotification = notificacionService.saveNotification(notification);
            if (newNotification != null) {
                obje.put("res", "success");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            }else {
                obje.put("res", "error");
                obje.put("data", "Ocurrio un error al registrar los datos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
