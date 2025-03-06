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
			throw new IllegalArgumentException("Endereço não pode ser nulo.");
		}
		
		if (Util.isEmpty(endereco.getLogradouro())) {
			throw new IllegalArgumentException("Logradouro não pode ser nulo.");
		}

		if (endereco.getTipoEndereco() == null) {
			throw new IllegalArgumentException("Tipo de endereço não pode ser nulo.");
		}

		if (endereco.getMunicipio() == null) {
			throw new IllegalArgumentException("Município não pode ser nulo.");
		}

		if (endereco.getPessoa() == null) {
			throw new IllegalArgumentException("Pessoa não pode ser nula.");
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