package com.crud.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import com.crud.model.Estado;
import com.crud.model.Municipio;
import com.crud.repository.MunicipioRepository;

@Stateless
public class MunicipioService {

	@Inject
	private MunicipioRepository municipioRepository;
	
	public List<Municipio> buscarMunicipiosPorEstado(Estado estado) {
		return municipioRepository.buscarMunicipiosPorEstado(estado);
	}
}