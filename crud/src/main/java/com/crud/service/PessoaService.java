package com.crud.service;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.crud.enums.Sexo;
import com.crud.model.Pessoa;
import com.crud.repository.PessoaRepository;
import com.crud.util.Util;

@Stateless
public class PessoaService {

	@Inject
	private PessoaRepository pessoaRepository;
	
	public List<Pessoa> listarTodos() {
		return pessoaRepository.listarTodos();
	}
	
	public List<Pessoa> pesquisar(String nome, String cpf, Date dataNascimento, Sexo sexo) {
		return pessoaRepository.pesquisar(nome, cpf, dataNascimento, sexo);
	}
	
	public void salvar(Pessoa pessoa) {
		
		if (pessoa == null) {
	        throw new IllegalArgumentException("Pessoa n�o pode ser nula.");
	    }
		
		if (Util.isEmpty(pessoa.getNome())) {
			throw new IllegalArgumentException("O nome � obrigat�rio.");
		}
		
		if (Util.isEmpty(pessoa.getCpf())) {
			throw new IllegalArgumentException("O CPF � obrigat�rio.");
		}
		
		if (pessoa.getId() == null) {
			pessoaRepository.salvar(pessoa);
		} else {
			pessoaRepository.atualizar(pessoa);
		}
	}
	
	public void excluir(Pessoa pessoa) {
		pessoaRepository.excluir(pessoa);
	}
}