package com.sd.reticula.service;

import com.sd.reticula.model.Establecimiento;
import com.sd.reticula.model.Cliente;
import com.sd.reticula.repository.EstablecimientoRepository;
import com.sd.reticula.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoService {

    @Autowired
    private EstablecimientoRepository establecimientoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Establecimiento> getAll(){
        return establecimientoRepository.findAll();
    }

    public List<Establecimiento> getRecordsByCliente(int cliente_id){
        Cliente objAux = null;
        List<Cliente> listaClientes = clienteRepository.findAll();
        for (Cliente obj : listaClientes) {
            if (obj.getId() == cliente_id) {
                objAux = obj;
                break;
            }
        }
        List<Establecimiento> listaEstablecimientos = establecimientoRepository.findAllByCliente(objAux);
        return listaEstablecimientos;
    }

    public void saveEstablecimiento(Establecimiento objEstablecimiento){
        if(objEstablecimiento.getCliente() == null){
            throw new NullPointerException("Se esta intentando insertar un objeto nulo");
        }
        if(objEstablecimiento.getDireccion().equals("")){
            new Exception("La direccion no puede estar vacia");
        }
        try {
            establecimientoRepository.saveAndFlush(objEstablecimiento);
            System.out.println("se guardo el nuevo establecimiento");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
