package com.crud.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.crud.model.Estado;
import com.crud.repository.EstadoRepository;

@Stateless
public class EstadoService {

	@Inject
	private EstadoRepository estadoRepository;
	
	public List<Estado> listarTodos() {
		return estadoRepository.listarTodos();
	}
}