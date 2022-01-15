package com.sd.reticula.service;

import com.sd.reticula.model.Usuario;
import com.sd.reticula.repository.UsuarioRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> getAll(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(int id) throws NotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario;
    }

    @Transactional
    public void saveUsuario(Usuario objUsuario){
        if(objUsuario == null){
            throw new NullPointerException("El objeto a insertar no puede ser nulo");
        }

        try {
            System.out.println("Guarda al usuario nuevo");
            usuarioRepository.saveAndFlush(objUsuario);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void removeUsuario(int id){
        usuarioRepository.deleteById(id);
    }

    public Usuario getUsusarioLogin(String userName, String password){
        return usuarioRepository.findByUsernameAndSpassword(userName, password);
    }

}
