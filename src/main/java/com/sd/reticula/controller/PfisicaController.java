package com.sd.reticula.controller;

import com.sd.reticula.model.Pfisica;
import com.sd.reticula.service.PfisicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/clientes")
public class PfisicaController {

    @Autowired
    private PfisicaService pfisicaService;

    @GetMapping
    public List<Pfisica> getAll(){
        return pfisicaService.getAll();
    }

    @PostMapping("/insert")
    public void saveCliente(@RequestBody Pfisica objPfisica){
        System.out.println(objPfisica.toString());
        try {
            pfisicaService.saveCliente(objPfisica);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @GetMapping(params = { "page", "size" })
    public List<Pfisica> findPaginated(@RequestParam("page") int page,
        @RequestParam("size") int size,
           UriComponentsBuilder uriBuilder,
               HttpServletResponse response) {
        Page<Pfisica> resultPage = pfisicaService.findPaginated(page, size);
        if (page > resultPage.getTotalPages()) {
            throw new MyResourceNotFoundException();
        }
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Foo>(
                Foo.class, uriBuilder, response, page, resultPage.getTotalPages(), size));

        return resultPage.getContent();
    }

}
