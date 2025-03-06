package com.crud.bean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MenuPrincipalBean {
    
    private static final String TIPO_ENDERECO_PAGE = "/pages/tipo-endereco.xhtml";
    private static final String PESSOA_PAGE = "/pages/gestao-pessoas.xhtml";
    
    public String navegarParaTipoEndereco() {
        return TIPO_ENDERECO_PAGE;
    }
    
    public String navegarParaPessoa() {
        return PESSOA_PAGE;
    }
}