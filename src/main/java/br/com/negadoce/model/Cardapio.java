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
@Table(name="CARDAPIO")
public class Cardapio {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idItemCardapio;

	@Column(nullable=false)
	private String nomeItemCardapio;
	
	@Column(nullable=false)
	private String categoria;
	
	@Column
	private String descricao;
	
	@Column(nullable=false)
	private BigDecimal valor;
	
	@Column(nullable=false, columnDefinition = "boolean default false", insertable = true, updatable = true)
	private boolean principal;
	
	@Column(columnDefinition = "integer default 0", insertable = true, updatable = true)
	private int metaMensal;
	
	@Transient
	private String valorAux;
	

	public Long getIdItemCardapio() {
		return idItemCardapio;
	}

	public void setIdItemCardapio(Long idItemCardapio) {
		this.idItemCardapio = idItemCardapio;
	}

	public String getNomeItemCardapio() {
		return nomeItemCardapio;
	}

	public void setNomeItemCardapio(String nomeItemCardapio) {
		this.nomeItemCardapio = nomeItemCardapio;
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

	public boolean isPrincipal() {
		return principal;
	}

	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public int getMetaMensal() {
		return metaMensal;
	}

	public void setMetaMensal(int metaMensal) {
		this.metaMensal = metaMensal;
	}
	
}