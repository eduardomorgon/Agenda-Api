/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estudo.agenda.api.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author eduardo
 */
@Path("/")
public class Index {
    
    @GET
    public String home() {
        return "Agenda-Api";
    }
}
