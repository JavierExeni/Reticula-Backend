package com.sd.reticula;

import com.sd.reticula.model.Pfisica;
import com.sd.reticula.model.Tarea;
import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.model.Usuario;
import com.sd.reticula.repository.PfisicaRepository;
import com.sd.reticula.repository.TareaRepository;
import com.sd.reticula.repository.TrabajoTallerRepository;
import com.sd.reticula.repository.UsuarioRepository;
import com.sd.reticula.service.TareasService;
import com.sd.reticula.service.TrabajoTallerService;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToUrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.awt.font.TransformAttribute;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
	private PfisicaRepository pfisicaRepository;

	@Test
	void contextLoads() {
	}



	@Test
	void getTallerByCliente(){
		List<Pfisica> listaClientes = pfisicaRepository.findAll();
		List<TrabajoTaller> lista = trabajoTallerService.getAllByCliente(listaClientes.get(0).getLpersona_id());
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
