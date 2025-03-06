package com.crud.repository;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.crud.model.Estado;

@Stateless
public class EstadoRepository {

	@PersistenceContext
    private EntityManager em;
	
	public List<Estado> listarTodos() {
        String jpql = "SELECT e FROM Estado e";
        TypedQuery<Estado> query = em.createQuery(jpql, Estado.class);
        return query.getResultList();
    }
}