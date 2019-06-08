package br.com.negadoce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.negadoce.global.Global;
import br.com.negadoce.model.Pedido;
import br.com.negadoce.repository.PedidoRepository;
import br.com.negadoce.service.PedidoService;

@Service
public class PedidoServiceImpl extends Global implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Override
	public void salvar(Pedido pedido) {
		pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> listar() {
		List<Pedido> listPedido = (List<Pedido>) pedidoRepository.listarTodos();
		return listPedido;
	}
	
	@Override
	public List<Pedido> listarPorStatus(String status) {
		List<Pedido> listPedido = (List<Pedido>) pedidoRepository.listarPorStatus(status);
		return listPedido;
	}

	@Override
	public Pedido buscarPorId(Long id) {
		Pedido pedido = pedidoRepository.findOne(id);
		return pedido;
	}

	@Override
	public void excluir(Long id) {
		pedidoRepository.delete(id);
	}

	@Override
	public BigDecimal getValorTotalPedidos() {
		return pedidoRepository.getValorTotalPedidos();
	}

	@Override
	public long getQtTotalPedidos() {
		return pedidoRepository.getTotalPedidos();
	}

	@Override
	public long getQtPedidos(String pedidoStatus) {
		return pedidoRepository.getQtPedidosPorStatus(pedidoStatus);
	}
	
	@Override
	public List<Pedido> listarUltimosPedidos() {
		List<Pedido> listUltimosPedidos = (List<Pedido>) pedidoRepository.listarUltimosPedidos();
		List<Pedido> Lista10Ultimos = new ArrayList<Pedido>();
		
		if (listUltimosPedidos.size() >= 10) {
			for (int x=0; x<=9; x++) {
				Lista10Ultimos.add(listUltimosPedidos.get(x));
			}
		}
		
		return Lista10Ultimos;
	}
	
	@Override
	public List<Pedido> carregarPedidos(String status) {
		List<Pedido> ped = null;
		List<Pedido> pedidos = null;

		if (status.equals("T")) {
			ped = listar();
		} else if (status.equals("UP")) {
			ped = listarUltimosPedidos();
		} else {
			ped = listarPorStatus(status);
		}
		
		pedidos = verificaRegistro(ped);

		for (Pedido aux : pedidos) {
			aux.setValorAuxAdiantamento(formataValorReal(aux.getValorAdiatamento()));
			aux.setValorAuxDesconto(formataValorReal(aux.getValorDesconto()));
			aux.setValorAuxTaxaEntrega(formataValorReal(aux.getValorTaxaEntrega()));
			aux.setValorAuxAPagar(formataValorReal(aux.getValorAPagar()));
			aux.setValorAuxTotal(formataValorReal(aux.getValorTotal()));
			aux.setDtEntrega(formataDataPadraoBR(aux.getDtEntrega()));
			aux.setDtPedido(formataDataPadraoBR(aux.getDtPedido()));
		}

		return ped;
	}

	private List<Pedido> verificaRegistro(List<Pedido> listPed) {
		List<Pedido> listaPedidos = new ArrayList<Pedido>();
		
		try {
			for (Pedido pedido : listPed) {
				long dias;
				String dtFinal = formataDataPadraoBR(pedido.getDtEntrega());
				dias = calcularDiferecaDatas(formataDataHora(new Date(), "dd/MM/yyyy"), dtFinal);
				if (dias <= 2 && pedido.getStatus().equals("N")) {
					pedido.setStatus("A");
					// Altera o status do Pedido
					salvar(pedido);
				}
				
				listaPedidos.add(pedido);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaPedidos;
	}


}