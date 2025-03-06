package com.crud.enums;

import java.util.Arrays;
import java.util.List;

public enum Sexo {

	M("Masculino"),
	F("Feminino");
	
	private final String descricao;
	
	private Sexo(String descricao) {
		this.descricao = descricao;
	}
	
	public static List<Sexo> getListaSexo() {
	    return Arrays.asList(Sexo.values());
	}

	public String getDescricao() {
		return descricao;
	}
}