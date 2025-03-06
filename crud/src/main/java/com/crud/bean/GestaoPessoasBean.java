package com.crud.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.crud.enums.Sexo;
import com.crud.model.DominioTipoEndereco;
import com.crud.model.Endereco;
import com.crud.model.Estado;
import com.crud.model.Municipio;
import com.crud.model.Pessoa;
import com.crud.service.EnderecoService;
import com.crud.service.EstadoService;
import com.crud.service.MunicipioService;
import com.crud.service.PessoaService;
import com.crud.service.TipoEnderecoService;

@Named
@ViewScoped
public class GestaoPessoasBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final String MENU_PRINCIPAL_PAGE = "/index.xhtml?faces-redirect=true";
	
	@EJB
    private TipoEnderecoService tipoEnderecoService;
	
	@EJB
	private PessoaService pessoaService;
	
	@EJB
	private EstadoService estadoService;
	
	@EJB
	private MunicipioService municipioService;
	
	@EJB
	private EnderecoService enderecoService;
	
	private List<DominioTipoEndereco> listaTipoEndereco;
	
	private DominioTipoEndereco tipoEnderecoSelecionado;
	
	private String filtroNome;
	
	private String filtroCpf;
	
	private Date filtroDataNascimento;
	
	private Sexo filtroSexo;
	
	private List<Pessoa> listaPessoa;
	
	private Pessoa pessoaParaCadastrarOuEditar;
	
	private Endereco enderecoParaCadastrarOuEditar;
	
	private boolean cadastrando;
	
	private Estado estadoSelecionado;
	
	private List<Estado> listaEstado;
	
	private List<Municipio> listaMunicipio;
	
	private List<Endereco> listaEnderecosDaPessoa;
	
	private String nomePessoaEnderecos;

	@PostConstruct
    public void init() {
		pessoaParaCadastrarOuEditar = new Pessoa();
		enderecoParaCadastrarOuEditar = new Endereco();
		listaTipoEndereco = new ArrayList<>();
		listaMunicipio = new ArrayList<>();
		listaEstado = new ArrayList<>();
		listaPessoa = new ArrayList<>();
		listaEnderecosDaPessoa = new ArrayList<>();
		cadastrando = true;
	}
	
	public void pesquisar() {

		listaPessoa = pessoaService.pesquisar(filtroNome, filtroCpf, filtroDataNascimento, filtroSexo);
		
		if (listaPessoa.isEmpty()) {
			exibirMensagem(FacesMessage.SEVERITY_INFO, "Sua busca não retornou resultados.");
		}
	}
	
	public void novo() {
		pessoaParaCadastrarOuEditar = new Pessoa();
	}
	
	public void novoEndereco(Pessoa pessoa) {
		estadoSelecionado = null;
		prepararParaCadastrarOuEditar(true);
		enderecoParaCadastrarOuEditar = new Endereco();
		enderecoParaCadastrarOuEditar.setPessoa(pessoa);
	}
	
	public void editarEndereco(Endereco endereco) {
		estadoSelecionado = endereco.getMunicipio().getEstado();
		prepararParaCadastrarOuEditar(false);
		enderecoParaCadastrarOuEditar = endereco;
	}
	
	private void prepararParaCadastrarOuEditar(boolean cadastrando) {
		this.cadastrando = cadastrando;
		carregarListas();
		selecionarEstado();
	}
	
	public void editar(Pessoa pessoa) {
		pessoaParaCadastrarOuEditar = pessoa;
	}
	
	public void salvar(Pessoa pessoa) {
		try {
			pessoaService.salvar(pessoa);
			exibirMensagem(FacesMessage.SEVERITY_INFO, "Pessoa salva com sucesso.");
		} catch (Exception e) {
			exibirMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao salvar a pessoa.");
		}
		
		if (!listaPessoa.isEmpty()) {
			pesquisar();
		}
	}
	
	public void excluir(Pessoa pessoa) {
		try {
			pessoaService.excluir(pessoa);
	        exibirMensagem(FacesMessage.SEVERITY_INFO, pessoa.getNome() + " excluído(a) com sucesso.");
	    } catch (Exception e) {
	        exibirMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao excluir " + pessoa.getNome());
	    }
		
		pesquisar();
	}
	
	public void excluirEndereco(Endereco endereco) {
		
		Pessoa p = endereco.getPessoa();
		
		try {
			enderecoService.excluir(endereco);
	        exibirMensagem(FacesMessage.SEVERITY_INFO, "Endereço excluído com sucesso.");
	    } catch (Exception e) {
	        exibirMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao excluir endereço.");
	    }
		
		buscarEnderecosPorPessoa(p);
	}
	
	public void exibirEnderecos(Pessoa pessoa) {
		cadastrando = false;
		nomePessoaEnderecos = pessoa.getNome();
		buscarEnderecosPorPessoa(pessoa);
	}
	
	public void selecionarEstado() {
		listaMunicipio = municipioService.buscarMunicipiosPorEstado(estadoSelecionado);
	}
	
	public List<Sexo> getListaSexo() {
	    return Sexo.getListaSexo();
	}
	
	public String voltarParaMenuPrincipal() {
		return MENU_PRINCIPAL_PAGE;
	}
	
	public Date getMinDate() {
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(1900, Calendar.JANUARY, 1);
	    return calendar.getTime();
	}
	
	public Date getMaxDate() {
	    return new Date();
	}
	
	@SuppressWarnings("deprecation")
	public String getYearRange() {
	    int anoMin = getMinDate().getYear() + 1900;
	    int anoMax = getMaxDate().getYear() + 1900;
	    return anoMin + ":" + anoMax;
	}
	
	public void salvarEndereco(Endereco endereco) {
		try {
			enderecoService.salvar(endereco);
			exibirMensagem(FacesMessage.SEVERITY_INFO, "Endereço salvo com sucesso.");
		} catch (Exception e) {
			exibirMensagem(FacesMessage.SEVERITY_ERROR, "Erro ao salvar o endereço.");
		}
	}
	
	public void salvarEnderecoEditado(Endereco endereco) {
		Pessoa p = endereco.getPessoa();
		salvarEndereco(endereco);
		buscarEnderecosPorPessoa(p);
	}
	
	private void buscarEnderecosPorPessoa(Pessoa pessoa) {
		listaEnderecosDaPessoa = enderecoService.buscarEnderecosPorPessoa(pessoa);
	}
	
	private void carregarListas() {
		listaTipoEndereco = tipoEnderecoService.listarTodos();
		listaEstado = estadoService.listarTodos();
	}
	
	private void exibirMensagem(Severity severity, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, ""));
	}

	public List<DominioTipoEndereco> getListaTipoEndereco() {
		return listaTipoEndereco;
	}

	public void setListaTipoEndereco(List<DominioTipoEndereco> listaTipoEndereco) {
		this.listaTipoEndereco = listaTipoEndereco;
	}

	public DominioTipoEndereco getTipoEnderecoSelecionado() {
		return tipoEnderecoSelecionado;
	}

	public void setTipoEnderecoSelecionado(DominioTipoEndereco tipoEnderecoSelecionado) {
		this.tipoEnderecoSelecionado = tipoEnderecoSelecionado;
	}

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}

	public String getFiltroCpf() {
		return filtroCpf;
	}

	public void setFiltroCpf(String filtroCpf) {
		this.filtroCpf = filtroCpf;
	}

	public Date getFiltroDataNascimento() {
		return filtroDataNascimento;
	}

	public void setFiltroDataNascimento(Date filtroDataNascimento) {
		this.filtroDataNascimento = filtroDataNascimento;
	}

	public Sexo getFiltroSexo() {
		return filtroSexo;
	}

	public void setFiltroSexo(Sexo filtroSexo) {
		this.filtroSexo = filtroSexo;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}

	public Pessoa getPessoaParaCadastrarOuEditar() {
		return pessoaParaCadastrarOuEditar;
	}

	public void setPessoaParaCadastrarOuEditar(Pessoa pessoaParaCadastrarOuEditar) {
		this.pessoaParaCadastrarOuEditar = pessoaParaCadastrarOuEditar;
	}

	public Endereco getEnderecoParaCadastrarOuEditar() {
		return enderecoParaCadastrarOuEditar;
	}

	public void setEnderecoParaCadastrarOuEditar(Endereco enderecoParaCadastrarOuEditar) {
		this.enderecoParaCadastrarOuEditar = enderecoParaCadastrarOuEditar;
	}

	public boolean isCadastrando() {
		return cadastrando;
	}

	public void setCadastrando(boolean cadastrando) {
		this.cadastrando = cadastrando;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public List<Municipio> getListaMunicipio() {
		return listaMunicipio;
	}

	public void setListaMunicipio(List<Municipio> listaMunicipio) {
		this.listaMunicipio = listaMunicipio;
	}

	public List<Endereco> getListaEnderecosDaPessoa() {
		return listaEnderecosDaPessoa;
	}

	public void setListaEnderecosDaPessoa(List<Endereco> listaEnderecosDaPessoa) {
		this.listaEnderecosDaPessoa = listaEnderecosDaPessoa;
	}

	public String getNomePessoaEnderecos() {
		return nomePessoaEnderecos;
	}

	public void setNomePessoaEnderecos(String nomePessoaEnderecos) {
		this.nomePessoaEnderecos = nomePessoaEnderecos;
	}
}