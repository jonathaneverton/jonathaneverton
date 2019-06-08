package br.com.negadoce.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PEDIDOITEM")
public class PedidoItem {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idPedidoItem;
	
	@ManyToOne
	@JoinColumn(name = "idPedido")
	private Pedido pedido;
		
	@ManyToOne
	@JoinColumn(name = "idItemCardapio")
	private Cardapio cardapio;
	
	@Column(nullable=false)
	private BigDecimal quantidade;
	
	@Column(nullable=false)
	private BigDecimal valorUnitario;
	
	@Column(nullable=false)
	private BigDecimal valorTotal;
	
	@Transient
	private String valorAuxTotal;
	
	@Transient
	private String valorAuxUnitario;
	
	@Transient
	private int idAux;
	
	
	// ---------------------------------------------------------------------------------------------- //
	

	public Long getIdPedidoItem() {
		return idPedidoItem;
	}

	public void setIdPedidoItem(Long idPedidoItem) {
		this.idPedidoItem = idPedidoItem;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Cardapio getCardapio() {
		return cardapio;
	}

	public void setCardapio(Cardapio cardapio) {
		this.cardapio = cardapio;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getValorAuxTotal() {
		return valorAuxTotal;
	}

	public void setValorAuxTotal(String valorAuxTotal) {
		this.valorAuxTotal = valorAuxTotal;
	}

	public String getValorAuxUnitario() {
		return valorAuxUnitario;
	}

	public void setValorAuxUnitario(String valorAuxUnitario) {
		this.valorAuxUnitario = valorAuxUnitario;
	}

	public int getIdAux() {
		return idAux;
	}

	public void setIdAux(int idAux) {
		this.idAux = idAux;
	}

}