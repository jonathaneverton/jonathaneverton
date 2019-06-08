package br.com.negadoce.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.negadoce.model.Pedido;

public interface PedidoService {

	public void salvar(Pedido carrinho);

	public List<Pedido> listar();
	
	public List<Pedido> listarPorStatus(String status);

	public Pedido buscarPorId(Long id);

	public void excluir(Long id);

	public BigDecimal getValorTotalPedidos();

	public long getQtTotalPedidos();

	public long getQtPedidos(String string);
	
	public List<Pedido> listarUltimosPedidos();

	public List<Pedido> carregarPedidos(String string);

}
