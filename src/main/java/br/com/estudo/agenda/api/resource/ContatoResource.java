/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estudo.agenda.api.resource;

import br.com.estudo.agenda.api.dao.ContatoDao;
import br.com.estudo.agenda.api.model.Contato;
import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author eduardo
 */
@Path("/contatos")
public class ContatoResource {
    
//    curl -i -H "Content-Type: application/json" -X POST -d '{"nome": "Teste 1", "telefone": "123456"}' http://localhost:3000/agenda/api/contatos
//    curl -i -H "Content-Type: application/json" -X PUT -d '{"id": 1, "nome": "Teste 1222", "telefone": "123456"}' http://localhost:3000/agenda/api/contatos
//    curl -i -X DELETE http://localhost:3000/agenda/api/contatos/2  
    
    @Inject
    private ContatoDao dao;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contato> todos() {
        return dao.listAll();
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Contato buscarPor(@PathParam("id") int id) {
        return dao.findById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response salvar(Contato contato) {
        dao.save(contato);
        URI uri = URI.create("contatos/"+contato.getId());
        return Response.created(uri).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editar(Contato contato) {
        dao.edit(contato);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("{id}")
    public Response deletarPor(@PathParam("id") int id) {
        Contato contato = dao.findById(id);
        dao.delete(contato);
        return Response.ok().build();
    }
    
}
