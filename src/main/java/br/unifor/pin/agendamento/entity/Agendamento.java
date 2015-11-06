package br.unifor.pin.agendamento.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.unifor.pin.agendamento.utils.BaseEntity;

@Entity
@Table(name="TB_AGENDAMENTO")
public class Agendamento implements Serializable, BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name="solicitacao_id", nullable=false)
	private Solicitacao solicitacao;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="status_agendamento", nullable=false)
	private Status StatusAgendamento;
	
	@OneToOne
	@JoinColumn(name="agendamento_event_id", nullable=false)
	private AgendamentoEvent agendamentoEvent;
	
	public Agendamento(){}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
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
		Agendamento other = (Agendamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public AgendamentoEvent getAgendamentoEvent() {
		return agendamentoEvent;
	}

	public void setAgendamentoEvent(AgendamentoEvent agendamentoEvent) {
		this.agendamentoEvent = agendamentoEvent;
	}

	public Status getStatusAgendamento() {
		return StatusAgendamento;
	}

	public void setStatusAgendamento(Status statusAgendamento) {
		StatusAgendamento = statusAgendamento;
	}
	

}
