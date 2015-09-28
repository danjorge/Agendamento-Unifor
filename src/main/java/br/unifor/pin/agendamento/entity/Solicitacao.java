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
	@JoinColumn(name="usuario_id", nullable=false)
	private Usuarios usuario;
	
	public Solicitacao(){
		statusSolicitacao = new Status();
	}

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

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}
	

}
