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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tb_anexos")
@XmlRootElement
public class Anexos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DAT_ANEXO", columnDefinition = "DATE")
	private Date datAnexo;
	
	@Column(name="FLG_ATIVO")
	private boolean flgAtivo;
	
	@Column(name="TXT_DIRETORIO")
	private String txtDiretorio;
	
	@Column(name="TXT_NOME_ARQUIVO")
	private String txtNomeArquivo;
	
	@Transient
	private String photo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="usuarios_id")
	private Usuarios usuarios;
	
	public Anexos() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDatAnexo() {
		return datAnexo;
	}

	public void setDatAnexo(Date datAnexo) {
		this.datAnexo = datAnexo;
	}

	public boolean isFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(boolean flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public String getTxtDiretorio() {
		return txtDiretorio;
	}

	public void setTxtDiretorio(String txtDiretorio) {
		this.txtDiretorio = txtDiretorio;
	}

	public String getTxtNomeArquivo() {
		return txtNomeArquivo;
	}

	public void setTxtNomeArquivo(String txtNomeArquivo) {
		this.txtNomeArquivo = txtNomeArquivo;
	}

	public Usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Usuarios usuarios) {
		this.usuarios = usuarios;
	}
	
	public String getPhoto(){
		return photo;
	}
	
	public void setPhoto(String photo){
		this.photo = photo;
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
		Anexos other = (Anexos) obj;
		if(id == null){
			if(other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
