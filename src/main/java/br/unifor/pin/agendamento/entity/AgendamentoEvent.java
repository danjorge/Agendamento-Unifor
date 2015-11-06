package br.unifor.pin.agendamento.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.primefaces.model.ScheduleEvent;


@Entity
@Table(name="TB_AGENDAMENTO_EVENT")
public class AgendamentoEvent implements Serializable, ScheduleEvent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -321421019525314384L;

	@Transient
	private String id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer idEvent;
	
	private String titulo;
	
	@Column(name="DSC_AGENDAMENTO_EVENT")
	private String dscAgendamentoEvent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Integer idEvent) {
		this.idEvent = (id != null ?  Integer.parseInt(id) : idEvent);
	}

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

	@Override
	public Date getStartDate() {
		// TODO Auto-generated method stub
		return this.getDataInicio();
	}

	@Override
	public Date getEndDate() {
		// TODO Auto-generated method stub
		return this.getDataFim();
	}

	@Override
	public boolean isAllDay() {
		// TODO Auto-generated method stub
		return false;
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
	
	
}
