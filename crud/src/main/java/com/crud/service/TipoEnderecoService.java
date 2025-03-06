package com.crud.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.crud.model.DominioTipoEndereco;
import com.crud.repository.TipoEnderecoRepository;
import com.crud.util.Util;

@Stateless
public class TipoEnderecoService {

	@Inject
	private TipoEnderecoRepository tipoEnderecoRepository;

	public List<DominioTipoEndereco> pesquisarPorDescricao(String descricao) {
		return tipoEnderecoRepository.pesquisarPorDescricao(descricao);
	}
	
	public List<DominioTipoEndereco> listarTodos() {
		return tipoEnderecoRepository.listarTodos();
	}

	public void salvar(DominioTipoEndereco tipoEndereco) {
		if (tipoEndereco == null || Util.isEmpty(tipoEndereco.getDescricao())) {
			throw new IllegalArgumentException("A descrição do tipo de endereço é obrigatória.");
		}

		if (tipoEndereco.getId() == null) {
			tipoEnderecoRepository.salvar(tipoEndereco);
		} else {
			tipoEnderecoRepository.atualizar(tipoEndereco);
		}
	}

	public void excluir(DominioTipoEndereco tipoEndereco) {
		tipoEnderecoRepository.excluir(tipoEndereco);
	}
}