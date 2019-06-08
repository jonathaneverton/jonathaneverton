package br.com.negadoce.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.negadoce.global.Global;
import br.com.negadoce.model.Cardapio;
import br.com.negadoce.model.Pedido;
import br.com.negadoce.model.PedidoItem;
import br.com.negadoce.service.CardapioService;
import br.com.negadoce.service.PedidoItemService;
import br.com.negadoce.service.PedidoService;
import br.com.negadoce.service.UsuarioService;

@Controller
public class HomeController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoItemService pedidoItemService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private CardapioService cardapioService;

	@RequestMapping("/")
	public ModelAndView index(Model model) {
		Global global = new Global();
		long aux;
		
		/**
		 * Carrega a lista dos ultimos pedidos
		 */
		List<Pedido> ultimosPedidos = pedidoService.carregarPedidos("UP");
		model.addAttribute("ultimosPedidos", ultimosPedidos);
		
		/**
		 * Busca o valor total de todos os pedidos
		 */
		BigDecimal val = pedidoService.getValorTotalPedidos();
		if (global.isNotNull(val)) {
			model.addAttribute("valorTotalPedidos", global.formataValorReal(val));
		} else {
			model.addAttribute("valorTotalPedidos", "0");
		}
		
		/**
		 * Busca a quantidade total dos pedidos
		 */
		aux = pedidoService.getQtTotalPedidos();
		if (global.isNotNull(aux)) {
			model.addAttribute("quantPedidos", aux);
		}

		/**
		 * Busca o total dos pedidos novos
		 */
		aux = pedidoService.getQtPedidos("N");
		if (global.isNotNull(aux)) {
			global.setQtPedidosNovos(aux);
			model.addAttribute("qtPedidosNovos", aux);
		}

		/**
		 * Busca o total dos pedidos prioritarios
		 */
		aux = pedidoService.getQtPedidos("A");
		if (global.isNotNull(aux)) {
			global.setQtPedidosPrioritarios(aux);
			model.addAttribute("qtPedidosPrioritarios", aux);
		}

		/**
		 * Busca o total dos pedidos entregues
		 */
		aux = pedidoService.getQtPedidos("E");
		if (global.isNotNull(aux)) {
			global.setQtPedidosEntregues(aux);
			model.addAttribute("qtPedidosEntregues", aux);
		}

		/**
		 * Busca o total dos pedidos pagos
		 */
		aux = pedidoService.getQtPedidos("P");
		if (global.isNotNull(aux)) {
			global.setQtPedidosPagos(aux);
			model.addAttribute("qtPedidosPagos", aux);
		}
		
		/**
		 * Busca o total dos usuários
		 */
		aux =  usuarioService.getQtUsuarios();
		if (global.isNotNull(aux)) {
			model.addAttribute("quantUsuarios", aux);
		}
		
		
		
		
		
		/**
		 * TODO:   Relatório de Vendas por mês
		 */
		
		
		
		
		
		/**
		 * Vendas por período - Produtos Principais
		 */
		Calendar hoje = Calendar.getInstance();
		String anoAtual = String.valueOf(hoje.get(Calendar.YEAR));
		int anoAnterior; // Verifica mais adiante se o mês é janeiro. Se for janeiro, o ano anterior é diminuido. 
		model.addAttribute("dataDeAte", "Vendas: 01/01/" + anoAtual + " até 31/12/" + anoAtual);
		
		
		/**
		 * Quais produtos o adminstrador quer mostrar
		 */
		List<Cardapio> cardapioReport = cardapioService.listarPedidosPrincipais();
		
		if (cardapioReport.size() != 0) {
			
			
			/**
			 * Nome dos produtos principais
			 */
			long cardapioid1 = cardapioReport.get(0).getIdItemCardapio();
			long cardapioid2 = cardapioReport.get(1).getIdItemCardapio();
			long cardapioid3 = cardapioReport.get(2).getIdItemCardapio();
			long cardapioid4 = cardapioReport.get(3).getIdItemCardapio();
			
			String produto1 = cardapioReport.get(0).getNomeItemCardapio(), 
			       produto2 = cardapioReport.get(1).getNomeItemCardapio(), 
			       produto3 = cardapioReport.get(2).getNomeItemCardapio(), 
			       produto4 = cardapioReport.get(3).getNomeItemCardapio();
			
			System.out.println("ID  PEDIDO: " + cardapioid1 + "-" + produto1 + " " + 
											   cardapioid2 + "-" + produto2 + " " +
											   cardapioid3 + "-" + produto3 + " " +
											   cardapioid4 + "-" + produto4);
			
			model.addAttribute("produto1", produto1);
			model.addAttribute("produto2", produto2);
			model.addAttribute("produto3", produto3);
			model.addAttribute("produto4", produto4);
			
			
			/**
			 * meta
			 */
			BigDecimal meta1 = new BigDecimal(cardapioReport.get(0).getMetaMensal()), 
					   meta2 = new BigDecimal(cardapioReport.get(1).getMetaMensal()), 
					   meta3 = new BigDecimal(cardapioReport.get(2).getMetaMensal()), 
					   meta4 = new BigDecimal(cardapioReport.get(3).getMetaMensal());
			
			model.addAttribute("meta1", meta1);
			model.addAttribute("meta2", meta2);
			model.addAttribute("meta3", meta3);
			model.addAttribute("meta4", meta4);
			
			
			/**
			 * quantidade de produtos por mês
			 */
			
			int mes = hoje.get(GregorianCalendar.MONTH);
			int mesAnterior = mes;
			
			if (mesAnterior == 0) {
				mesAnterior = 12;
				anoAnterior = Integer.parseInt(anoAtual) - 1;
			} else {
				anoAnterior = Integer.parseInt(anoAtual);
			}
			
			mes = mes + 1; // Mes atual
			String mesAtual =  String.format("%02d", mes);
			
			List<PedidoItem> listPe = pedidoItemService.listar();
			BigDecimal qtProduto1 = new BigDecimal(0), 
					   qtProduto2 = new BigDecimal(0), 
					   qtProduto3 = new BigDecimal(0), 
					   qtProduto4 = new BigDecimal(0);
			
			qtProduto1 = calculaQuantidadeProduto(listPe, mesAtual, cardapioReport.get(0).getIdItemCardapio());
			qtProduto2 = calculaQuantidadeProduto(listPe, mesAtual, cardapioReport.get(1).getIdItemCardapio());
			qtProduto3 = calculaQuantidadeProduto(listPe, mesAtual, cardapioReport.get(2).getIdItemCardapio());
			qtProduto4 = calculaQuantidadeProduto(listPe, mesAtual, cardapioReport.get(3).getIdItemCardapio());
			
			model.addAttribute("qtProduto1", qtProduto1);
			model.addAttribute("qtProduto2", qtProduto2);
			model.addAttribute("qtProduto3", qtProduto3);
			model.addAttribute("qtProduto4", qtProduto4);
			
			
			/**
			 * % venda em relação a meta
			 */
			BigDecimal perctProduto1, perctProduto2, perctProduto3, perctProduto4;
			BigDecimal cemPercet = new BigDecimal(100);
			BigDecimal metaZero = new BigDecimal(0);
			
			if (meta1 != metaZero) {
				perctProduto1 = qtProduto1.multiply(cemPercet).divide(meta1, BigDecimal.ROUND_UP).round(new MathContext(2));
			} else {
				perctProduto1 = metaZero;
			}
			
			if (meta2 != metaZero) {
				perctProduto2 = qtProduto2.multiply(cemPercet).divide(meta2, BigDecimal.ROUND_UP).round(new MathContext(2));
			} else {
				perctProduto2 = metaZero;
			}
			
			if (meta3 != metaZero) {
				perctProduto3 = qtProduto3.multiply(cemPercet).divide(meta3, BigDecimal.ROUND_UP).round(new MathContext(2));
			} else {
				perctProduto3 = metaZero;
			}
			
			if (meta4 != metaZero) {
				perctProduto4 = qtProduto4.multiply(cemPercet).divide(meta4, BigDecimal.ROUND_UP).round(new MathContext(2));
			} else {
				perctProduto4 = metaZero;
			}
			
			model.addAttribute("perctProduto1", perctProduto1);
			model.addAttribute("perctProduto2", perctProduto2);
			model.addAttribute("perctProduto3", perctProduto3);
			model.addAttribute("perctProduto4", perctProduto4);
			
			
			/**
			 * Valor total por produto (Gráfico da linha anual)
			 */
			// Produto 1
			String jan = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0101"), anoAtual.concat("0131")));
			String fev = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0201"), anoAtual.concat("0231")));
			String mar = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0301"), anoAtual.concat("0331")));
			String abr = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0401"), anoAtual.concat("0431")));
			String mai = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0501"), anoAtual.concat("0531")));
			String jun = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0601"), anoAtual.concat("0631")));
			String jul = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0701"), anoAtual.concat("0731")));
			String ago = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0801"), anoAtual.concat("0831")));
			String set = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("0901"), anoAtual.concat("0931")));
			String out = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("1001"), anoAtual.concat("1031")));
			String nov = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("1101"), anoAtual.concat("1131")));
			String dez = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat("1201"), anoAtual.concat("1231")));
			
			
			String anoPedido1 = jan + ";" + 
							    fev + ";" + 
							    mar + ";" + 
							    abr + ";" + 
							    mai + ";" + 
							    jun + ";" + 
							    jul + ";" + 
							    ago + ";" + 
							    set + ";" + 
							    out + ";" + 
							    nov + ";" + 
							    dez;
			
			// Produto 2
			jan = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0101"), anoAtual.concat("0131")));
			fev = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0201"), anoAtual.concat("0231")));
			mar = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0301"), anoAtual.concat("0331")));
			abr = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0401"), anoAtual.concat("0431")));
			mai = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0501"), anoAtual.concat("0531")));
			jun = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0601"), anoAtual.concat("0631")));
			jul = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0701"), anoAtual.concat("0731")));
			ago = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0801"), anoAtual.concat("0831")));
			set = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("0901"), anoAtual.concat("0931")));
			out = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("1001"), anoAtual.concat("1031")));
			nov = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("1101"), anoAtual.concat("1131")));
			dez = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat("1201"), anoAtual.concat("1231")));
			
			
			String anoPedido2 = jan + ";" + 
				                fev + ";" + 
				                mar + ";" + 
				                abr + ";" + 
				                mai + ";" + 
				                jun + ";" + 
				                jul + ";" + 
				                ago + ";" + 
				                set + ";" + 
				                out + ";" + 
				                nov + ";" + 
				                dez;
			
			// Produto 3
			jan = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0101"), anoAtual.concat("0131")));
			fev = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0201"), anoAtual.concat("0231")));
			mar = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0301"), anoAtual.concat("0331")));
			abr = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0401"), anoAtual.concat("0431")));
			mai = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0501"), anoAtual.concat("0531")));
			jun = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0601"), anoAtual.concat("0631")));
			jul = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0701"), anoAtual.concat("0731")));
			ago = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0801"), anoAtual.concat("0831")));
			set = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("0901"), anoAtual.concat("0931")));
			out = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("1001"), anoAtual.concat("1031")));
			nov = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("1101"), anoAtual.concat("1131")));
			dez = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat("1201"), anoAtual.concat("1231")));
			
			
			String anoPedido3 = jan + ";" + 
				                fev + ";" + 
				                mar + ";" + 
				                abr + ";" + 
				                mai + ";" + 
				                jun + ";" + 
				                jul + ";" + 
				                ago + ";" + 
				                set + ";" + 
				                out + ";" + 
				                nov + ";" + 
				                dez;
			
			// Pedido 4
			jan = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0101"), anoAtual.concat("0131")));
			fev = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0201"), anoAtual.concat("0231")));
			mar = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0301"), anoAtual.concat("0331")));
			abr = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0401"), anoAtual.concat("0431")));
			mai = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0501"), anoAtual.concat("0531")));
			jun = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0601"), anoAtual.concat("0631")));
			jul = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0701"), anoAtual.concat("0731")));
			ago = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0801"), anoAtual.concat("0831")));
			set = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("0901"), anoAtual.concat("0931")));
			out = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("1001"), anoAtual.concat("1031")));
			nov = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("1101"), anoAtual.concat("1131")));
			dez = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat("1201"), anoAtual.concat("1231")));
			
			
			String anoPedido4 = jan + ";" + 
				                fev + ";" + 
				                mar + ";" + 
				                abr + ";" + 
				                mai + ";" + 
				                jun + ";" + 
				                jul + ";" + 
				                ago + ";" + 
				                set + ";" + 
				                out + ";" + 
				                nov + ";" + 
				                dez;
			
			model.addAttribute("qtProdutosVendidos", anoPedido1 + ";" + anoPedido2 + ";" + anoPedido3 + ";" +  anoPedido4);
			
			
			/**
			 * Total vendido de produtos no mês atual e no mês anterior
			 */
			
			String mesAnt = String.format("%02d", mesAnterior);
			String anoAnt = String.valueOf(anoAnterior);
			BigDecimal valMes = new BigDecimal(0);
			BigDecimal valAnt = new BigDecimal(0);
			BigDecimal variacao = new BigDecimal(0);
			int perctV = 0;;
			
			String vltProduto1Mes = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAtual.concat(mesAtual).concat("01"), anoAtual.concat(mesAtual).concat("31")));;
			String vltProduto1Ant = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid1, anoAnt.concat(mesAnt).concat("01"), anoAnt.concat(mesAnt).concat("31")));
			model.addAttribute("vltProduto1",  "Mês: " + global.formataValorReal(new BigDecimal(vltProduto1Mes)));
			model.addAttribute("vltProduto1A", "Ant: " + global.formataValorReal(new BigDecimal(vltProduto1Ant)));
			
			//-------------------- Calculo Variacao
			valMes = new BigDecimal(vltProduto1Mes);
			valAnt = new BigDecimal(vltProduto1Ant);
			variacao = valMes.subtract(valAnt);
			
			if (valAnt.compareTo(metaZero) != 0) {
				perctV = variacao.divide(valAnt, BigDecimal.ROUND_UP).multiply(cemPercet).intValue();
			} else {
				perctV = variacao.multiply(cemPercet).intValue();
			}
			
			model.addAttribute("varProduto1", perctV + "%");
			
			if (variacao.compareTo(metaZero) == 0) {
				model.addAttribute("per1", "0");
			} else if (variacao.compareTo(metaZero) > 0) {
				model.addAttribute("per1", "1");
			} else {
				model.addAttribute("per1", "-1");
			}
			//---------------------------fim
			
			
			String vltProduto2Mes = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAtual.concat(mesAtual).concat("01"), anoAtual.concat(mesAtual).concat("31")));;
			String vltProduto2Ant = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid2, anoAnt.concat(mesAnt).concat("01"), anoAnt.concat(mesAnt).concat("31")));
			model.addAttribute("vltProduto2",  "Mês: " + global.formataValorReal(new BigDecimal(vltProduto2Mes)));
			model.addAttribute("vltProduto2A", "Ant: " + global.formataValorReal(new BigDecimal(vltProduto2Ant)));
			
			//-------------------- Calculo Variacao
			valMes = new BigDecimal(vltProduto2Mes);
			valAnt = new BigDecimal(vltProduto2Ant);
			variacao = valMes.subtract(valAnt);
			
			if (valAnt.compareTo(metaZero) != 0) {
				perctV = variacao.divide(valAnt, BigDecimal.ROUND_UP).multiply(cemPercet).intValue();
			} else {
				perctV = variacao.multiply(cemPercet).intValue();
			}
			
			model.addAttribute("varProduto2", perctV + "%");
			
			if (variacao.compareTo(metaZero) == 0) {
				model.addAttribute("per2", "0");
			} else if (variacao.compareTo(metaZero) > 0) {
				model.addAttribute("per2", "1");
			} else {
				model.addAttribute("per2", "-1");
			}
			//---------------------------fim
			
			String vltProduto3Mes = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAtual.concat(mesAtual).concat("01"), anoAtual.concat(mesAtual).concat("31")));;
			String vltProduto3Ant = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid3, anoAnt.concat(mesAnt).concat("01"), anoAnt.concat(mesAnt).concat("31")));
			model.addAttribute("vltProduto3",  "Mês: " + global.formataValorReal(new BigDecimal(vltProduto3Mes)));
			model.addAttribute("vltProduto3A", "Ant: " + global.formataValorReal(new BigDecimal(vltProduto3Ant)));
			
			//-------------------- Calculo Variacao
			valMes = new BigDecimal(vltProduto3Mes);
			valAnt = new BigDecimal(vltProduto3Ant);
			variacao = valMes.subtract(valAnt);
			
			if (valAnt.compareTo(metaZero) != 0) {
				perctV = variacao.divide(valAnt, BigDecimal.ROUND_UP).multiply(cemPercet).intValue();
			} else {
				perctV = variacao.multiply(cemPercet).intValue();
			}
			
			model.addAttribute("varProduto3", perctV + "%");
			
			if (variacao.compareTo(metaZero) == 0) {
				model.addAttribute("per3", "0");
			} else if (variacao.compareTo(metaZero) > 0) {
				model.addAttribute("per3", "1");
			} else {
				model.addAttribute("per3", "-1");
			}
			//---------------------------fim
			
			String vltProduto4Mes = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAtual.concat(mesAtual).concat("01"), anoAtual.concat(mesAtual).concat("31")));;
			String vltProduto4Ant = isValorNull(pedidoItemService.buscaValTotalMensalPorProduto(cardapioid4, anoAnt.concat(mesAnt).concat("01"), anoAnt.concat(mesAnt).concat("31")));
			model.addAttribute("vltProduto4",  "Mês: " + global.formataValorReal(new BigDecimal(vltProduto4Mes)));
			model.addAttribute("vltProduto4A", "Ant: " + global.formataValorReal(new BigDecimal(vltProduto4Ant)));
			
			//-------------------- Calculo Variacao
			valMes = new BigDecimal(vltProduto4Mes);
			valAnt = new BigDecimal(vltProduto4Ant);
			variacao = valMes.subtract(valAnt);
			
			if (valAnt.compareTo(metaZero) != 0) {
				perctV = variacao.divide(valAnt, BigDecimal.ROUND_UP).multiply(cemPercet).intValue();
			} else {
				perctV = variacao.multiply(cemPercet).intValue();
			}
			
			model.addAttribute("varProduto4", perctV + "%");
			
			if (variacao.compareTo(metaZero) == 0) {
				model.addAttribute("per4", "0");
			} else if (variacao.compareTo(metaZero) > 0) {
				model.addAttribute("per4", "1");
			} else {
				model.addAttribute("per4", "-1");
			}
			//---------------------------fim
			
		} else {
			model.addAttribute("qtProduto1", "0");
			model.addAttribute("qtProduto2", "0");
			model.addAttribute("qtProduto3", "0");
			model.addAttribute("qtProduto4", "0");
			
			model.addAttribute("meta1", "0");
			model.addAttribute("meta2", "0");
			model.addAttribute("meta3", "0");
			model.addAttribute("meta4", "0");
			
			model.addAttribute("perctProduto1", "0");
			model.addAttribute("perctProduto2", "0");
			model.addAttribute("perctProduto3", "0");
			model.addAttribute("perctProduto4", "0");
			
			model.addAttribute("vltProduto1", "R$ 0");
			model.addAttribute("vltProduto2", "R$ 0");
			model.addAttribute("vltProduto3", "R$ 0");
			model.addAttribute("vltProduto4", "R$ 0");
			
			model.addAttribute("vltProduto1A", "R$ 0");
			model.addAttribute("vltProduto2A", "R$ 0");
			model.addAttribute("vltProduto3A", "R$ 0");
			model.addAttribute("vltProduto4A", "R$ 0");
		}
		
		ModelAndView mv = new ModelAndView("/index");
		return mv;
	}

	private String isValorNull(String valor) {
		if (valor != null) {
			return valor;
		}
		
		return "0";
	}

	private BigDecimal calculaQuantidadeProduto(List<PedidoItem> pedidos, String mesAtual, Long idItemCardapio) {
		BigDecimal qtd = new BigDecimal(0);
		
		for (PedidoItem pe : pedidos) {
			System.out.println(pe.getPedido().getDtEntrega());
			String mesPedido = "";
			
			if (pe.getPedido().getDtEntrega().contains("/")) {
				System.out.println(pe.getPedido().getDtEntrega().substring(3,5));
				mesPedido = pe.getPedido().getDtEntrega().substring(3,5);
			} else {
				System.out.println(pe.getPedido().getDtEntrega().substring(4,6));
				mesPedido = pe.getPedido().getDtEntrega().substring(4, 6);
			}
			
			if (pe.getCardapio().getIdItemCardapio() == idItemCardapio && mesPedido.equals(mesAtual)) {
				qtd = qtd.add(pe.getQuantidade());
			}
		}
		
		return qtd;
	}

}