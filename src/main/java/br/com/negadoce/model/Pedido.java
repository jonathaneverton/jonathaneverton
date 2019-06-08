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
@Table(name="PEDIDO")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idPedido;
	
	@Column(nullable=false)
	private String numeroPedido;
	
	@Column()
	private BigDecimal valorAdiatamento = new BigDecimal(0);
	
	@Column()
	private BigDecimal valorTaxaEntrega = new BigDecimal(0);
	
	@Column()
	private BigDecimal valorDesconto = new BigDecimal(0);
	
	@Column()
	private BigDecimal valorAPagar = new BigDecimal(0);
	
	@Column(nullable=false)
	private BigDecimal valorTotal = new BigDecimal(0);
	
	@Column(nullable=false)
//	@Temporal(TemporalType.DATE)
//	private Calendar dtPedido;
	private String dtPedido;
	
	@Column(nullable=false)
	private String hrPedido;
	
	@Column(nullable=false)
	private String dtEntrega;
	
	@Column(nullable=false)
	private String hrEntrega;
	
	@Column()
	private String status = "N";
	
	@ManyToOne
    @JoinColumn(name = "idUsuario")
	private Usuario usuario;
	
	@Transient
	private Cardapio cardapio;
	
	@Transient
	private String valorAuxTotal = "0,00";
	
	@Transient
	private String valorAuxAdiantamento = "0,00";
	
	@Transient
	private String valorAuxAPagar = "0,00";
	
	@Transient
	private String valorAuxTaxaEntrega = "0,00";
	
	@Transient
	private String valorAuxDesconto = "0,00";
	
	@Transient
	private BigDecimal quantidade;
	
	@Transient
	private String dtEntregaAux;
	
	
	// ---------------------------------------------------------------------------------------------- //
	

	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public String getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(String numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public BigDecimal getValorAdiatamento() {
		return valorAdiatamento;
	}

	public void setValorAdiatamento(BigDecimal valorAdiatamento) {
		this.valorAdiatamento = valorAdiatamento;
	}

	public BigDecimal getValorAPagar() {
		return valorAPagar;
	}

	public void setValorAPagar(BigDecimal valorAPagar) {
		this.valorAPagar = valorAPagar;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(String dtPedido) {
		this.dtPedido = dtPedido;
	}

	public String getHrPedido() {
		return hrPedido;
	}

	public void setHrPedido(String hrPedido) {
		this.hrPedido = hrPedido;
	}

	public String getDtEntrega() {
		return dtEntrega;
	}

	public void setDtEntrega(String dtEntrega) {
		this.dtEntrega = dtEntrega;
	}

	public String getHrEntrega() {
		return hrEntrega;
	}

	public void setHrEntrega(String hrEntrega) {
		this.hrEntrega = hrEntrega;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cardapio getCardapio() {
		return cardapio;
	}

	public void setCardapio(Cardapio cardapio) {
		this.cardapio = cardapio;
	}

	public String getValorAuxTotal() {
		return valorAuxTotal;
	}

	public void setValorAuxTotal(String valorAuxTotal) {
		this.valorAuxTotal = valorAuxTotal;
	}

	public String getValorAuxAdiantamento() {
		return valorAuxAdiantamento;
	}

	public void setValorAuxAdiantamento(String valorAuxAdiantamento) {
		this.valorAuxAdiantamento = valorAuxAdiantamento;
	}

	public String getValorAuxAPagar() {
		return valorAuxAPagar;
	}

	public void setValorAuxAPagar(String valorAuxAPagar) {
		this.valorAuxAPagar = valorAuxAPagar;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getValorTaxaEntrega() {
		return valorTaxaEntrega;
	}

	public void setValorTaxaEntrega(BigDecimal valorTaxaEntrega) {
		this.valorTaxaEntrega = valorTaxaEntrega;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public String getValorAuxTaxaEntrega() {
		return valorAuxTaxaEntrega;
	}

	public void setValorAuxTaxaEntrega(String valorAuxTaxaEntrega) {
		this.valorAuxTaxaEntrega = valorAuxTaxaEntrega;
	}

	public String getValorAuxDesconto() {
		return valorAuxDesconto;
	}

	public void setValorAuxDesconto(String valorAuxDesconto) {
		this.valorAuxDesconto = valorAuxDesconto;
	}

	public String getDtEntregaAux() {
		return dtEntregaAux;
	}

	public void setDtEntregaAux(String dtEntregaAux) {
		this.dtEntregaAux = dtEntregaAux;
	}
	
}