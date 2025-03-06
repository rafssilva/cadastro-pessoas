package com.crud.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.crud.model.Endereco;
import com.crud.model.Pessoa;
import com.crud.repository.EnderecoRepository;
import com.crud.util.Util;

@Stateless
public class EnderecoService {

	@Inject
	private EnderecoRepository enderecoRepository;
	
	public void salvar(Endereco endereco) {
		
		if (endereco == null) {
			throw new IllegalArgumentException("Endere�o n�o pode ser nulo.");
		}
		
		if (Util.isEmpty(endereco.getLogradouro())) {
			throw new IllegalArgumentException("Logradouro n�o pode ser nulo.");
		}

		if (endereco.getTipoEndereco() == null) {
			throw new IllegalArgumentException("Tipo de endere�o n�o pode ser nulo.");
		}

		if (endereco.getMunicipio() == null) {
			throw new IllegalArgumentException("Munic�pio n�o pode ser nulo.");
		}

		if (endereco.getPessoa() == null) {
			throw new IllegalArgumentException("Pessoa n�o pode ser nula.");
		}
		
		if (endereco.getId() == null) {
			enderecoRepository.salvar(endereco);
		} else {
			enderecoRepository.atualizar(endereco);
		}
	}
	
	public List<Endereco> buscarEnderecosPorPessoa(Pessoa pessoa) {
		return enderecoRepository.buscarEnderecosPorPessoa(pessoa);
	}
	
	public void excluir(Endereco endereco) {
		enderecoRepository.excluir(endereco);
	}
}