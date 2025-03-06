package com.crud.repository;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.crud.model.Estado;
import com.crud.model.Municipio;

@Stateless
public class MunicipioRepository {

	@PersistenceContext
    private EntityManager em;
	
	public List<Municipio> buscarMunicipiosPorEstado(Estado estado) {
		String jpql = "SELECT m FROM Municipio m WHERE m.estado = :estado";
        TypedQuery<Municipio> query = em.createQuery(jpql, Municipio.class);
        query.setParameter("estado", estado);
        return query.getResultList();
	}
}