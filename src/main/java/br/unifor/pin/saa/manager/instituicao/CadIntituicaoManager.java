package br.unifor.pin.saa.manager.instituicao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.unifor.pin.saa.bussines.InstituicaoBO;
import br.unifor.pin.saa.entity.Instituicoes;
import br.unifor.pin.saa.utils.MessagesUtils;

@RequestScoped
@ManagedBean(name="cadInstituicao")
@Component(value="cadInstituicao")
public class CadIntituicaoManager {

	@Autowired
	private InstituicaoBO instituicaoBO;
	
	private String nome;
	private String sigla;
	
	public String salvar(){
		Instituicoes instituicao = new Instituicoes();
		instituicao.setNome(nome);
		instituicao.setSigla(sigla);
		instituicaoBO.salvar(instituicao);
		MessagesUtils.info("Instituição salva com sucesso!");
		this.limpaTela();
		return "sucesso";
	}
	
	private void limpaTela(){
		this.nome = "";
		this.sigla = "";
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
}
