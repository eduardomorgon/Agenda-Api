/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estudo.agenda.api.resource;

import br.com.estudo.agenda.api.App;
import br.com.estudo.agenda.api.model.Contato;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author eduardo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContatoResourceTest {

    private Client client;
    private WebTarget target;
    private static String urlTesteCriado;
    private static final Contato CONTATO = new Contato("Jose", "(11)1234-1234");

    public ContatoResourceTest() {
    }
    
    @Before
    public void setUp() {
        ClientConfig config = new ClientConfig();
        config.register(LoggingFilter.class);
        this.client = ClientBuilder.newClient(config);
        this.target = client.target("http://localhost:3000/agenda/api/contatos");
    }
    
    /**
     * Test of salvar method, of class ContatoResource.
     */
    @Test
    public void test1Salvar() {

        Entity<Contato> entity = Entity.entity(CONTATO, MediaType.APPLICATION_JSON);
        Response response = target.request().post(entity);
        urlTesteCriado = response.getLocation().toString();
        assertEquals(201, response.getStatus());
    }

    /**
     * Test of buscarPor method, of class ContatoResource.
     */
    @Test
    public void test2BuscarPor() {
        
        Contato contatoInserido = client.target(urlTesteCriado).request().get(Contato.class);
        assertEquals(contatoInserido.getNome(), CONTATO.getNome());
    }

    /**
     * Test of editar method, of class ContatoResource.
     */
    @Test
    public void test3Editar() {

        Contato contatoParaEditar = client.target(urlTesteCriado).request().get(Contato.class);
        contatoParaEditar.setNome("Antonio");
        contatoParaEditar.setTelefone("(11)99999-1234");

        Entity<Contato> entity = Entity.entity(contatoParaEditar, MediaType.APPLICATION_JSON);
        Response response = target.request().put(entity);
        assertEquals(200, response.getStatus());

        Contato contatoAlterado = client.target(urlTesteCriado).request().get(Contato.class);
        assertEquals(contatoAlterado.getNome(), contatoParaEditar.getNome());
    }

    /**
     * Test of todos method, of class ContatoResource.
     */
    @Test
    public void test4Todos() {
        
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        List<Contato> contatos = response.readEntity(new GenericType<List<Contato>>() {});
        assertTrue(contatos.size() > 0);
    }
    
    @Test
    public void test5SalvarSemSeguirRegrasDeValidacao() {
        Contato contato = new Contato("", "1111111111");
        Entity<Contato> entity = Entity.entity(contato, MediaType.APPLICATION_JSON);
        Response response = target.request().post(entity);
        assertEquals(402, response.getStatus());
    }

    /**
     * Test of deletarPor method, of class ContatoResource.
     */
    @Test
    public void test6DeletarPor() {

        WebTarget alvoParaExcluir = client.target(urlTesteCriado);
        Response response = alvoParaExcluir.request().delete();
        assertEquals(200, response.getStatus());
    }

}
