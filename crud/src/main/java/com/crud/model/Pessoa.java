package com.crud.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;
import com.crud.enums.Sexo;
import com.crud.model.base.BaseModel;

@Entity
@Table
public class Pessoa extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O nome é obrigatório e não pode estar em branco.")
	@Column(nullable = false)
	private String nome;
	
	@CPF
	@NotBlank(message = "O CPF é obrigatório e não pode estar em branco.")
	@Column(nullable = false, unique = true)
	private String cpf;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "data_nascimento")
    private Date dataNascimento;
	
	@Enumerated(EnumType.STRING)
    @Column(length = 1)
    private Sexo sexo;
	
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();
	
	public Integer getIdade() {
	    if (dataNascimento == null) {
	        return null;
	    }
	    Instant instant = Instant.ofEpochMilli(dataNascimento.getTime());
	    LocalDate birthDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate currentDate = LocalDate.now();
	    return Period.between(birthDate, currentDate).getYears();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
}