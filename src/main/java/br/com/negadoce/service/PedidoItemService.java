package br.com.negadoce.service;

import java.util.List;

import br.com.negadoce.model.PedidoItem;
import br.com.negadoce.model.PedidoReport;

public interface PedidoItemService {

	public void salvar(List<PedidoItem> carrinho);

	public List<PedidoItem> listarPorIDPedido(Long idPedido);

	public void excluir(Long idPedidoItem);
	
	public void excluirPorIdPedido(Long idPedido);
	
	public List<PedidoItem> listar();

	public String buscaValTotalMensalPorProduto(long produto, String dtInicial, String dtFinal);

	public List<PedidoItem> listarPedidos(PedidoReport pedidoReport);

}
