package com.sd.reticula.service;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAll(){
        List<Cliente> listaClientes = clienteRepository.findAll(Sort.by(Sort.Direction.ASC,"nombre"));
        return listaClientes;
    }

    @Transactional
    public Cliente saveClient(Cliente objCliente) {
        try {
            return clienteRepository.saveAndFlush(objCliente);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void removeClient(int id){
        clienteRepository.deleteById(id);
    }
}
