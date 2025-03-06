package com.crud.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.crud.model.DominioTipoEndereco;
import com.crud.service.TipoEnderecoService;

@Named
@ViewScoped
public class TipoEnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String MENU_PRINCIPAL_PAGE = "/index.xhtml?faces-redirect=true";
	
	@EJB
    private TipoEnderecoService tipoEnderecoService;
	
	private String filtroDescricao;
	
	private List<DominioTipoEndereco> listaTipoEndereco;
	
	private DominioTipoEndereco tipoEnderecoParaCadastrarOuEditar;
	
	@PostConstruct
    public void init() {
		tipoEnderecoParaCadastrarOuEditar = new DominioTipoEndereco();
		filtroDescricao = new String();
		listaTipoEndereco = new ArrayList<>();
    }
	
	public void pesquisar() {
		
		listaTipoEndereco = tipoEnderecoService.pesquisarPorDescricao(filtroDescricao);
		
		if (listaTipoEndereco.isEmpty()) {
			exibirMensagem(FacesMessage.SEVERITY_INFO, "Sua busca não retornou resultados.");
		}
	}
	
	public void novo() {
		tipoEnderecoParaCadastrarOuEditar = new DominioTipoEndereco();
	}
	
	public void editar(DominioTipoEndereco tipoEndereco) {
		tipoEnderecoParaCadastrarOuEditar = tipoEndereco;
	}
	
	public void salvar(DominioTipoEndereco tipoEndereco) {
		try {
			tipoEnderecoService.salvar(tipoEndereco);
			exibirMensagem(FacesMessage.SEVERITY_INFO, "Tipo de endereço salvo com sucesso.");
		} catch (Exception e) {
			exibirMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao salvar o tipo de endereço.");
		}
		
		if (!listaTipoEndereco.isEmpty()) {
			pesquisar();
		}
	}

	
	public void excluir(DominioTipoEndereco tipoEndereco) {
		try {
	        tipoEnderecoService.excluir(tipoEndereco);
	        exibirMensagem(FacesMessage.SEVERITY_INFO, tipoEndereco.getDescricao() + " excluído com sucesso.");
	    } catch (Exception e) {
	        exibirMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao excluir " + tipoEndereco.getDescricao());
	    }
		
		pesquisar();
	}

	public String voltarParaMenuPrincipal() {
		return MENU_PRINCIPAL_PAGE;
	}
	
	private void exibirMensagem(Severity severity, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, ""));
	}

	public String getFiltroDescricao() {
		return filtroDescricao;
	}

	public void setFiltroDescricao(String filtroDescricao) {
		this.filtroDescricao = filtroDescricao;
	}

	public List<DominioTipoEndereco> getListaTipoEndereco() {
		return listaTipoEndereco;
	}

	public void setListaTipoEndereco(List<DominioTipoEndereco> listaTipoEndereco) {
		this.listaTipoEndereco = listaTipoEndereco;
	}

	public DominioTipoEndereco getTipoEnderecoParaCadastrarOuEditar() {
		return tipoEnderecoParaCadastrarOuEditar;
	}

	public void setTipoEnderecoParaCadastrarOuEditar(DominioTipoEndereco tipoEnderecoParaCadastrarOuEditar) {
		this.tipoEnderecoParaCadastrarOuEditar = tipoEnderecoParaCadastrarOuEditar;
	}
}