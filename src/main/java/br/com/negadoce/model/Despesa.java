package br.com.negadoce.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="DESPESA")
public class Despesa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idDespesa;
	
	@Column(nullable=false)
	private String dtDespesa;

	@Column(nullable=false)
	private String nomeDespesa;
	
	@Column(nullable=false)
	private String categoria;
	
	@Column
	private String descricao;
	
	@Column(nullable=false)
	private BigDecimal valor;
	
	@Transient
	private String valorAux;
	

	// ---------------------------------------------------------------------------------------------- //
	
	
	public Long getIdDespesa() {
		return idDespesa;
	}

	public void setIdDespesa(Long idDespesa) {
		this.idDespesa = idDespesa;
	}

	public String getNomeDespesa() {
		return nomeDespesa;
	}

	public void setNomeDespesa(String nomeDespesa) {
		this.nomeDespesa = nomeDespesa;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(String valor) {
		String aux = valor.replace("R$ ", "").replace(".", "").replace(",", ".");
		BigDecimal valorFormatado = new BigDecimal(aux);
		this.valor = valorFormatado;
	}

	public String getValorAux() {
		return valorAux;
	}

	public void setValorAux(String valorAux) {
		this.valorAux = valorAux;
	}

	public String getDtDespesa() {
		return dtDespesa;
	}

	public void setDtDespesa(String dtDespesa) {
		this.dtDespesa = dtDespesa;
	}

}