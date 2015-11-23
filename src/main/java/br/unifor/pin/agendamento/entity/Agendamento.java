package br.unifor.pin.agendamento.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.primefaces.model.ScheduleEvent;

@Entity
@Table(name="TB_AGENDAMENTO")
public class Agendamento implements Serializable, ScheduleEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Transient
	private String id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer idEvent;
	
	private String titulo;
	
	@Column(name="DSC_AGENDAMENTO_EVENT")
	private String dscAgendamentoEvent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	
	@Column(name="is_All_Day")
	private boolean isAllDay;
	
	@OneToOne
	@JoinColumn(name="solicitacao_id", nullable=false)
	private Solicitacao solicitacao;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="status_agendamento", nullable=false)
	private Status StatusAgendamento;
	
	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.getTitulo();
	}
	
	public void setTitle(String title){
		titulo = title;
	}

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return this.getDataInicio();
	}
	
	public void setStartDate(Date startDate) {
		dataInicio = startDate;
	}

	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub
		return this.getDataFim();
	}
	
	public void setEndDate(Date endDate){
		dataFim = endDate;
	}

	@Override
	public boolean isAllDay() {
		// TODO Auto-generated method stub
		return this.isAllDay;
	}
	
	@Override
	public String getStyleClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.getDscAgendamentoEvent();
	}
	
	public void setDescription(String description){
		dscAgendamentoEvent = description;
	}
	
	public Agendamento(){}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIdEvent() {
		return idEvent;
	}

	public void setId(Integer idEvent) {
		this.idEvent = idEvent;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}
	
	public Status getStatusAgendamento() {
		return StatusAgendamento;
	}

	public void setStatusAgendamento(Status statusAgendamento) {
		StatusAgendamento = statusAgendamento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDscAgendamentoEvent() {
		return dscAgendamentoEvent;
	}

	public void setDscAgendamentoEvent(String dscAgendamentoEvent) {
		this.dscAgendamentoEvent = dscAgendamentoEvent;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setAllDay(boolean isAllDay) {
		this.isAllDay = isAllDay;
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
}
