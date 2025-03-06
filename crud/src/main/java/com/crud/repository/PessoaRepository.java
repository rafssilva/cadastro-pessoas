package com.crud.repository;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.crud.enums.Sexo;
import com.crud.model.Pessoa;
import com.crud.util.Util;

@Stateless
public class PessoaRepository {

	@PersistenceContext
    private EntityManager em;
	
	public List<Pessoa> listarTodos() {
        String jpql = "SELECT p FROM Pessoa p";
        TypedQuery<Pessoa> query = em.createQuery(jpql, Pessoa.class);
        return query.getResultList();
    }
	
	public List<Pessoa> pesquisar(String nome, String cpf, Date dataNascimento, Sexo sexo) {
		
		String jpql = " SELECT p FROM Pessoa p WHERE 1 = 1 ";
		
		if (Util.isNotEmpty(nome)) {
			jpql += " AND LOWER(FUNCTION('unaccent', p.nome)) LIKE LOWER(FUNCTION('unaccent', :nome)) ";
		}
		
		if (Util.isNotEmpty(cpf)) {
			jpql += " AND p.cpf = :cpf ";
		}
		
		if (dataNascimento != null) {
			jpql += " AND p.dataNascimento = :dataNascimento ";
		}
		
		if (sexo != null) {
			jpql += " AND p.sexo = :sexo ";
		}
		
        TypedQuery<Pessoa> query = em.createQuery(jpql, Pessoa.class);
        
        if (Util.isNotEmpty(nome)) {
        	query.setParameter("nome", "%" + nome + "%");
        }
        
        if (Util.isNotEmpty(cpf)) {
        	query.setParameter("cpf", cpf);
        }
        
        if (dataNascimento != null) {
        	query.setParameter("dataNascimento", dataNascimento);
		}
        
        if (sexo != null) {
        	query.setParameter("sexo", sexo);
		}
        
        return query.getResultList();
	}
	
	public void salvar(Pessoa pessoa) {
		em.persist(pessoa);
	}
	
	public void atualizar(Pessoa pessoa) {
		em.merge(pessoa);
	}
	
	public void excluir(Pessoa pessoa) {
		pessoa = em.merge(pessoa);
        em.remove(pessoa);
    }
}