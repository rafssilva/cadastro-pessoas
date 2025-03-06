package com.crud.repository;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.crud.model.Endereco;
import com.crud.model.Pessoa;

@Stateless
public class EnderecoRepository {

	@PersistenceContext
    private EntityManager em;
	
	public void salvar(Endereco endereco) {
		em.persist(endereco);
	}
	
	public void atualizar(Endereco endereco) {
		em.merge(endereco);
	}
	
	public List<Endereco> buscarEnderecosPorPessoa(Pessoa pessoa) {
		String jpql = "SELECT e FROM Endereco e JOIN FETCH e.tipoEndereco JOIN FETCH e.municipio m JOIN FETCH m.estado WHERE e.pessoa = :pessoa";
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        query.setParameter("pessoa", pessoa);
        return query.getResultList();
	}
	
	public void excluir(Endereco endereco) {
		endereco = em.merge(endereco);
        em.remove(endereco);
	}
}