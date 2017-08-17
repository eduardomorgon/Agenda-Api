/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.estudo.agenda.api.dao;

import br.com.estudo.agenda.api.model.Contato;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author eduardo
 */
@RequestScoped
public class ContatoDao {
    
    private EntityManager em;

    public ContatoDao() {
    }
    
    @Inject
    public ContatoDao(EntityManager em) {
        this.em = em;
    }
    
    public List<Contato> listAll() {
        return em.createQuery("SELECT c FROM Contato c ORDER BY c.nome").getResultList();
    }

    public Contato findById(int id) {
        return em.find(Contato.class, id);
    }

    public void save(Contato contato) {
        em.getTransaction().begin();
        em.persist(contato);
        em.getTransaction().commit();
    }

    public void edit(Contato contato) {
        em.getTransaction().begin();
        em.merge(contato);
        em.getTransaction().commit();
    }

    public void delete(Contato contato) {
        em.getTransaction().begin();
        em.remove(contato);
        em.getTransaction().commit();
    }
    
    
}
