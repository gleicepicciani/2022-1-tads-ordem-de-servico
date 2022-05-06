package br.edu.ifms.ordem.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.ifms.ordem.entities.enums.Prioridade;
import br.edu.ifms.ordem.entities.enums.Status;

@Entity
@Table(name = "tb_ordem_de_servico")
public class OrdemDeServico implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "descricao_problema")
	private String descricaoProblema;
	@Column(name = "descricao_solucao")
	private String descricaoSolucao;
	@Column(name = "data_cadastro")
	private Date dataCadastro;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;
	
	//atributo que representa o relacionamento com o técnico
	@ManyToOne //muitos pra um, olhando pra tabela ordem de servico
	@JoinColumn(name = "id_tecnico_fk")
	private Tecnico tecnico;
	
	
	@ManyToMany
	@JoinTable(name = "tb_equipamento_servico",
			joinColumns = @JoinColumn(name = "id_ordem"),
			inverseJoinColumns = @JoinColumn(name = "id_equipamento")) //criando uma tabela intermediária, que une as duas entidades
	Set<Equipamento> equipamentos = new HashSet<>(); //set é interface, por isso implementamos HashSet
	
	
	public OrdemDeServico() {		
	}

	
	

	
	public OrdemDeServico(Long id, String descricaoProblema, String descricaoSolucao, Date dataCadastro, Status status,
			Prioridade prioridade, Tecnico tecnico) {
		
		this.id = id;
		this.descricaoProblema = descricaoProblema;
		this.descricaoSolucao = descricaoSolucao;
		this.dataCadastro = dataCadastro;
		this.status = status;
		this.prioridade = prioridade;
		this.tecnico = tecnico;
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescricaoProblema() {
		return descricaoProblema;
	}


	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}


	public String getDescricaoSolucao() {
		return descricaoSolucao;
	}


	public void setDescricaoSolucao(String descricaoSolucao) {
		this.descricaoSolucao = descricaoSolucao;
	}


	public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public Prioridade getPrioridade() {
		return prioridade;
	}


	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	
	
	
    //somente get
	public Set<Equipamento> getEquipamentos() {
		return equipamentos;
	}



	public Tecnico getTecnico() {
		return tecnico;
	}





	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
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
		OrdemDeServico other = (OrdemDeServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
