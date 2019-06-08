package br.com.negadoce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.negadoce.global.Global;
import br.com.negadoce.model.PedidoItem;
import br.com.negadoce.model.PedidoReport;
import br.com.negadoce.repository.PedidoItemRepository;
import br.com.negadoce.service.PedidoItemService;

@Service
public class PedidoItemServiceImpl extends Global implements PedidoItemService {
	
	@Autowired
	private PedidoItemRepository pedidoItemRepository;

	@Override
	public void salvar(List<PedidoItem> carrinho) {
		pedidoItemRepository.save(carrinho);
	}

	@Override
	public List<PedidoItem> listarPorIDPedido(Long idPedido) {
		return pedidoItemRepository.buscaItensPedido(idPedido);
	}

	@Override
	public void excluir(Long idPedidoItem) {
		pedidoItemRepository.delete(idPedidoItem);
	}

	@Override
	public List<PedidoItem> listar() {
		List<PedidoItem> listPedidoItem = (List<PedidoItem>) pedidoItemRepository.listar();
		return listPedidoItem;
	}

	@Override
	public String buscaValTotalMensalPorProduto(long produto, String dtInicial, String dtFinal) {
		String totalProdutoPorMes = pedidoItemRepository.buscaValTotalMensalPorProduto(produto, dtInicial, dtFinal);
		return totalProdutoPorMes;
	}

	@Override
	public void excluirPorIdPedido(Long idPedido) {
		pedidoItemRepository.excluirPorIdPedido(idPedido);
	}

	@Override
	public List<PedidoItem> listarPedidos(PedidoReport report) {
		Long cliente = report.getPedido().getIdPedido();
		Long produto = report.getPedido().getCardapio().getIdItemCardapio();
		String categoria = report.getPedido().getCardapio().getCategoria();
		String status = report.getPedido().getStatus();
		String data = report.getPedido().getStatus();
		
		List<PedidoItem> listPedidoItem = new ArrayList<PedidoItem>();
		
		if (cliente == null && produto == null && categoria.equals("") && status.equals("")) { // todos os pedidos
			listPedidoItem = (List<PedidoItem>) pedidoItemRepository.listarTodosPedidos();
		}
//		} else if (cliente == null && produto == null && categoria.equals("") && !status.equals("")) { // pedidos por status
//			listPedidoItem = (List<PedidoItem>) pedidoItemRepository.listarPedidosStatus();
//		} else if (cliente == null && produto == null && !categoria.equals("") && status.equals("")) { // pedidos por categoria
//			listPedidoItem = (List<PedidoItem>) pedidoItemRepository.listarPedidosCategoria();
//		} else if (cliente == null && produto != null && categoria.equals("") && status.equals("")) { // pedidos por produto
//			listPedidoItem = (List<PedidoItem>) pedidoItemRepository.listarPedidosProduto();
//		} else if (cliente != null && produto == null && categoria.equals("") && status.equals("")) { // pedidos por cliente
//			listPedidoItem = (List<PedidoItem>) pedidoItemRepository.listarPedidosCliente();
//		} else if (cliente != null && produto != null && categoria.equals("") && status.equals("")) { // pedidos por cliente e por produto
//			listPedidoItem = (List<PedidoItem>) pedidoItemRepository.listarPedidosClienteProduto();
//		} else if (cliente != null && produto != null && !categoria.equals("") && status.equals("")) { // cliente, produto e categoria
//			
//		} else if (cliente != null && produto != null && !categoria.equals("") && !status.equals("")) { // cliente, produto, categoria, status
//			
//		} else if (cliente != null && produto != null && categoria.equals("") && !status.equals("")) { // cliente, produto, status
//		
//		} else if (cliente != null && produto == null && categoria.equals("") && !status.equals("")) { // cliente, status
//			
//		} else if (cliente != null && produto != null && !categoria.equals("") && status.equals("")) { // cliente, categoria
//			
//		} else if (cliente != null && produto != null && categoria.equals("") && status.equals("")) { // cliente, produto
//		
//		} else if (cliente == null && produto != null && categoria.equals("") && !status.equals("")) { // produto, status
//			
//		} else if (cliente == null && produto != null && !categoria.equals("") && status.equals("")) { // produto, categoria
//			
//		} else if (cliente == null && produto == null && !categoria.equals("") && !status.equals("")) { // categoria, status
//			
//		} else { // cliente, produto, categoria, status
//			
//		}
		
		
		
		for (PedidoItem p : listPedidoItem) {
			p.setValorAuxUnitario(formataValorReal(p.getValorUnitario()));
			p.setValorAuxTotal(formataValorReal(p.getValorTotal()));
			String dt = formataDataPadraoBR(p.getPedido().getDtEntrega());
			p.getPedido().setDtEntregaAux(dt);
		}
		
		return listPedidoItem;
	}


}
