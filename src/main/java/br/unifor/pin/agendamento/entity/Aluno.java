package br.unifor.pin.agendamento.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TB_ALUNOS")
public class Aluno implements Serializable{


	private static final long serialVersionUID = 7548139743566463644L;

	@Id
	@SequenceGenerator(name="alunos_seq",sequenceName="alunos_seq",allocationSize=1)
    @GeneratedValue(generator="alunos_seq",strategy=GenerationType.SEQUENCE)
	private Long id;
	
	@Column(nullable=false)
	private String nome;
	
	@Column(name="matricula", nullable=false, unique=true)
	private String matricula;
	
	@ManyToOne
	@JoinColumn(name="curso_id", nullable=false)
	private Cursos curso;

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

	public Cursos getCurso() {
		return curso;
	}

	public void setCurso(Cursos curso) {
		this.curso = curso;
	}

		
	
	
}
