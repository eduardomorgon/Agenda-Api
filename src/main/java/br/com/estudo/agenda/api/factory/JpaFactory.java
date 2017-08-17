/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estudo.agenda.api.factory;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author eduardo
 */
@ApplicationScoped
public class JpaFactory {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("agendaPU");
    
    @Produces
    public EntityManager create() {
        EntityManager em = emf.createEntityManager();
        return em;
    }
    
    public void closeEm(@Disposes EntityManager em) {
        System.out.println("em");
        if(em.isOpen()) {
            em.close();
        }
    }
    
    @PreDestroy
    public void closeEmf() {
        if(emf.isOpen()) {
            System.out.println("emf");
            emf.close();
        }
    }
    
}
