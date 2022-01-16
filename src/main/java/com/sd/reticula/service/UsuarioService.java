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

    public Optional<Usuario> getUserById(int id) throws NotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario;
    }

    @Transactional
    public Usuario saveUser(Usuario objUsuario){
        try {
            return usuarioRepository.saveAndFlush(objUsuario);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void removeUsuario(int id){
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> getLoginUser(Usuario usuario){
        return usuarioRepository.findByUsernameAndSpassword(usuario.getUsername(), usuario.getSpassword());
    }

}
