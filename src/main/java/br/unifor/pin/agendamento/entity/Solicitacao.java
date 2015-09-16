package br.unifor.pin.agendamento.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_SOLICITACOES")
public class Solicitacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String assunto;
	
	private String descricao;
	
	@OneToOne
	@JoinColumn(name="status_solicitacao", nullable=false)
	private Status statusSolicitacao;
	
	@ManyToOne
	@JoinColumn(name="coordenador_id", nullable=false)
	private Coordenador coordenador;
	
	@ManyToOne
	@JoinColumn(name="aluno_id", nullable=false)
	private Aluno aluno;
	
	public Solicitacao(){}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Status getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(Status statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
	

}
