package br.edu.ifms.ordem.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tecnico")
public class Tecnico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String telefone;
	private String email;
	private String senha;
	
	//relacionamento bi-direcional
	@OneToMany(mappedBy = "tecnico") //já foi mapeado no atributo tecnico em ordem de serviço
	private List<OrdemDeServico> ordemDeServicos;
	
	
	/**
	 * Informações de auditoria 
	 */
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updateAt;
	
	//construtor vazio
    public Tecnico() {		
	}

	//construtor com todos os atributos -- excluir o super --criar o construtor com o botao direito e clicar na opção using fields e selecionar todos os atributos
	public Tecnico(Long id, String nome, String telefone, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdateAt() {
		return updateAt;
	}
	
	
	
	//buscar somente o get do ordem de serviço
	public List<OrdemDeServico> getOrdemDeServicos() {
		return ordemDeServicos;
	}

	//método para setar as variáveis de auditoria
	@PrePersist
	public void prePersist() {
		createdAt = Instant.now();		
	}
	
	@PreUpdate
	public void preUpdate() {
		updateAt = Instant.now();	
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
		Tecnico other = (Tecnico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
