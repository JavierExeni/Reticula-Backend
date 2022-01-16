package com.sd.reticula.service;

import com.sd.reticula.model.Notificacion;
import com.sd.reticula.repository.NotificacionRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    NotificacionRepository notificacionRepository;

    public List<Notificacion> getAll(){
        return notificacionRepository.findAll();
    }

    public Notificacion saveNotification(Notificacion objNotificacion){
        if(validatenotification(objNotificacion)){
            return notificacionRepository.saveAndFlush(objNotificacion);
        }
        return null;
    }

    private boolean validatenotification(Notificacion objNotification) {
        return objNotification != null;
    }

}
