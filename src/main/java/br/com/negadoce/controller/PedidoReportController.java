package br.com.negadoce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.negadoce.global.Global;
import br.com.negadoce.model.Cardapio;
import br.com.negadoce.model.PedidoItem;
import br.com.negadoce.model.PedidoReport;
import br.com.negadoce.model.Usuario;
import br.com.negadoce.service.CardapioService;
import br.com.negadoce.service.PedidoItemService;
import br.com.negadoce.service.UsuarioService;

@Controller
public class PedidoReportController extends Global {

	@Autowired
	private PedidoItemService pedidoItemService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CardapioService cardapioService;
	
	private List<PedidoItem> listaPedidos = new ArrayList<PedidoItem>();
	
	@RequestMapping(value = "/relatorio/relatorios", method = { RequestMethod.GET })
	public ModelAndView pedidoReportNew(Model model) {
		listaPedidos = new ArrayList<PedidoItem>();
		ModelAndView mv = new ModelAndView("redirect:/relatorio/pedidoReport");

		return mv;
	}
	
	@RequestMapping(value = "/relatorio/pedidoReport", method = { RequestMethod.GET })
	public ModelAndView pedidoReport(Model model) {
		List<Usuario> cboClientes = usuarioService.listar();
		List<Cardapio> cboCardapio =  cardapioService.listar();
		PedidoReport pedidoReport = new PedidoReport();
		
		model.addAttribute("pedidoReport", pedidoReport);
		model.addAttribute("cboClientes", cboClientes);
		model.addAttribute("cboCardapio", cboCardapio);
		model.addAttribute("pedidoList", listaPedidos);
		ModelAndView mv = new ModelAndView("relatorio/pedidoReport");

		return mv;
	}
	
	@RequestMapping(value = "/relatorio/pedidoReportProcurar", method = { RequestMethod.POST })
	public ModelAndView pedidoReportProcurar(PedidoReport pedidoReport, Model model, RedirectAttributes attributes) {
		listaPedidos = pedidoItemService.listarPedidos(pedidoReport);
		ModelAndView mv = new ModelAndView("redirect:/relatorio/pedidoReport");
		return mv;
	}

}