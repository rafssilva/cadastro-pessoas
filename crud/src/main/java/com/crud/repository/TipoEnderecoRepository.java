package com.crud.repository;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import com.crud.model.DominioTipoEndereco;

@Stateless
public class TipoEnderecoRepository {

	@PersistenceContext
    private EntityManager em;

    public List<DominioTipoEndereco> pesquisarPorDescricao(String descricao) {
        String jpql = "SELECT dte FROM DominioTipoEndereco dte WHERE LOWER(FUNCTION('unaccent', dte.descricao)) LIKE LOWER(FUNCTION('unaccent', :descricao))";
        TypedQuery<DominioTipoEndereco> query = em.createQuery(jpql, DominioTipoEndereco.class);
        query.setParameter("descricao", "%" + descricao + "%");
        return query.getResultList();
    }
    
    public List<DominioTipoEndereco> listarTodos() {
        String jpql = "SELECT dte FROM DominioTipoEndereco dte";
        TypedQuery<DominioTipoEndereco> query = em.createQuery(jpql, DominioTipoEndereco.class);
        return query.getResultList();
    }
    
    public void salvar(DominioTipoEndereco tipoEndereco) {
        em.persist(tipoEndereco);
    }

    public void atualizar(DominioTipoEndereco tipoEndereco) {
        em.merge(tipoEndereco);
    }
    
    public void excluir(DominioTipoEndereco tipoEndereco) {
        tipoEndereco = em.merge(tipoEndereco);
        em.remove(tipoEndereco);
    }
}