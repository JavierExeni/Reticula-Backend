package com.sd.reticula;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.model.Tarea;
import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.repository.ClienteRepository;
import com.sd.reticula.repository.TareaRepository;
import com.sd.reticula.repository.TrabajoTallerRepository;
import com.sd.reticula.repository.UsuarioRepository;
import com.sd.reticula.service.TareasService;
import com.sd.reticula.service.TrabajoTallerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RestReticulaApplicationTests {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TareaRepository tareaRepository;

	@Autowired
	private TareasService tareasService;

	@Autowired
	private TrabajoTallerService trabajoTallerService;

	@Autowired
	private TrabajoTallerRepository trabajoTallerRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	void contextLoads() {
	}



	@Test
	void getTallerByCliente(){
		List<Cliente> listaClientes = clienteRepository.findAll();
		List<TrabajoTaller> lista = trabajoTallerService.getAllByCliente(listaClientes.get(0).getId());
		for (TrabajoTaller obj :
				lista) {
			System.out.println(obj.toString());
		}
	}

	@Test
	void getMantenimientosByCliente(){
		List<Tarea> lista = tareasService.getMantenimientosByCliente(1);
		lista.forEach(obj -> obj.toString());
	}
}
