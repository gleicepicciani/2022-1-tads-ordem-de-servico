package br.edu.ifms.ordem.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_setor")
public class Setor implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sigla;
	private String nome;
	private String email;
	private String telefone;	
	private String coordenador;
		
	//relacionamento bi-direcional
	@OneToMany(mappedBy = "setor") //já foi mapeado no atributo setor em equipamento
	private List<Equipamento> equipamento;
	
	
	//construtor vazio para sobrecarga
	public Setor() {		
	}
	
	//construtor com todos os atributos 
	public Setor(Long id, String sigla, String nome, String email, String telefone, String coordenador) {
		this.id = id;
		this.sigla = sigla;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.coordenador = coordenador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(String coordenador) {
		this.coordenador = coordenador;
	}
	
	
	//buscar somente o get do equipamentos, pois queremos uma lista dos itens cadastrados
	public List<Equipamento> getEquipamento() {
		return equipamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Setor other = (Setor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
