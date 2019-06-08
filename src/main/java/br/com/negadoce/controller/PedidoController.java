package br.com.negadoce.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.negadoce.global.Global;
import br.com.negadoce.model.Cardapio;
import br.com.negadoce.model.Pedido;
import br.com.negadoce.model.PedidoItem;
import br.com.negadoce.model.Usuario;
import br.com.negadoce.service.CardapioService;
import br.com.negadoce.service.PedidoItemService;
import br.com.negadoce.service.PedidoService;
import br.com.negadoce.service.UsuarioService;

@Controller
public class PedidoController extends Global {

	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private PedidoItemService pedidoItemService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CardapioService cardapioService;
	
	private int idAuxItem = 0;

	private String numeroPedido = "";
	private Usuario cliente = new Usuario();
	private Pedido pedido = new Pedido();
	private List<Cardapio> cboCardapio = new ArrayList<Cardapio>();
	private List<PedidoItem> carrinho = new ArrayList<PedidoItem>();
	private List<PedidoItem> carrinhoAux = new ArrayList<PedidoItem>();

	
	@RequestMapping(value = "/pedido/listarPedido/Todos", method = { RequestMethod.GET })
	public ModelAndView listarTodos(Model model) {
		List<Pedido> pedidos = pedidoService.carregarPedidos("T");
		List<Usuario> cboClientes = usuarioService.listar();
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("cboClientes", cboClientes);
		model.addAttribute("pedidos", pedidos);
		ModelAndView mv = new ModelAndView("pedido/pedido");

		return mv;
	}
	
	/**
	 * Carregar Pedidos Prioritários
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pedido/listarPedido/A", method = { RequestMethod.GET })
	public ModelAndView listarPedidoPrioritario(Model model) {
		List<Pedido> pedidos = pedidoService.carregarPedidos("A");
		List<Usuario> cboClientes = usuarioService.listar();
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("cboClientes", cboClientes);
		model.addAttribute("pedidos", pedidos);
		ModelAndView mv = new ModelAndView("pedido/pedido");

		return mv;
	}
	
	@RequestMapping(value = "/pedido/listarPedido/N", method = { RequestMethod.GET })
	public ModelAndView listarPedidoNovo(Model model) {
		List<Pedido> pedidos = pedidoService.carregarPedidos("N");
		List<Usuario> cboClientes = usuarioService.listar();
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("cboClientes", cboClientes);
		model.addAttribute("pedidos", pedidos);
		ModelAndView mv = new ModelAndView("pedido/pedido");

		return mv;
	}
	
	@RequestMapping(value = "/pedido/listarPedido/E", method = { RequestMethod.GET })
	public ModelAndView listarPedidoEntregue(Model model) {
		List<Pedido> pedidos = pedidoService.carregarPedidos("E");
		List<Usuario> cboClientes = usuarioService.listar();
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("cboClientes", cboClientes);
		model.addAttribute("pedidos", pedidos);
		ModelAndView mv = new ModelAndView("pedido/pedido");

		return mv;
	}
	
	@RequestMapping(value = "/pedido/listarPedido/P", method = { RequestMethod.GET })
	public ModelAndView listarPedidoPago(Model model) {
		List<Pedido> pedidos = pedidoService.carregarPedidos("P");
		List<Usuario> cboClientes = usuarioService.listar();
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);
		model.addAttribute("cboClientes", cboClientes);
		model.addAttribute("pedidos", pedidos);
		ModelAndView mv = new ModelAndView("pedido/pedido");

		return mv;
	}

	@RequestMapping(value = "/pedido/cadastrarPedido", method = { RequestMethod.GET })
	public ModelAndView cadastrarPedido(Usuario usuario, Model model) {

		if (usuario.getIdUsuario() != null) { // requisição é feita pela tela da lista
			this.pedido = new Pedido();
			this.cliente = usuarioService.buscarPorId(usuario.getIdUsuario());
			this.pedido.setUsuario(cliente);
			this.cboCardapio = cardapioService.listar();
			this.carrinho = new ArrayList<PedidoItem>();
			this.pedido.setNumeroPedido(geraNumeroPedido());
		}

		model.addAttribute("numeroPedido", this.numeroPedido);
		model.addAttribute("pedido", this.pedido);
		model.addAttribute("cboUsuarios", this.cliente);
		model.addAttribute("cboCardapio", this.cboCardapio);
		model.addAttribute("carrinho", this.carrinho);

		ModelAndView mv = new ModelAndView("pedido/pedidoCad");
		return mv;
	}

	@RequestMapping(value = "/pedido/addItemCarrinho", method = { RequestMethod.POST })
	public ModelAndView addItemCarrinho(Pedido pedido) {
		// Calculos do Total - Adiantamento - A pagar
		Cardapio produtoSelecionado = cardapioService.buscarPorId(pedido.getCardapio().getIdItemCardapio());
		BigDecimal valTotalItem = produtoSelecionado.getValor().multiply(pedido.getQuantidade()); // Total do Item (Parcial)

		if (this.pedido.getValorTotal() != null) {
			this.pedido.setValorTotal(this.pedido.getValorTotal().add(valTotalItem));
		} else {
			this.pedido.setValorTotal(valTotalItem);
		}

		// Lista dos Itens
		PedidoItem pedidoItem = new PedidoItem();
		pedidoItem.setCardapio(produtoSelecionado);
		pedidoItem.setPedido(this.pedido);
		pedidoItem.setQuantidade(pedido.getQuantidade());
		pedidoItem.setValorTotal(valTotalItem);
		pedidoItem.setValorUnitario(produtoSelecionado.getValor());
		pedidoItem.setValorAuxTotal(formataValorReal(valTotalItem));

		idAuxItem ++;
		pedidoItem.setIdAux(idAuxItem);
		carrinho.add(pedidoItem);

		ModelAndView mv = new ModelAndView("redirect:/pedido/cadastrarPedido");
		return mv;
	}
	
	@RequestMapping(value = "/pedido/removeItemCarrinho/{idAux}", method = { RequestMethod.GET })
	public ModelAndView removeItemCarrinho(@PathVariable int idAux) {
		
		for (PedidoItem item : carrinho) {
			if (item.getIdAux() == idAux) {
				carrinho.remove(item);
				this.pedido.setValorTotal(this.pedido.getValorTotal().subtract(item.getValorTotal()));
				
				break;
			}
		}

		ModelAndView mv = new ModelAndView("redirect:/pedido/cadastrarPedido");
		return mv;
	}

	@RequestMapping(value = "/pedido/salvar", method = { RequestMethod.POST })
	public ModelAndView salvarPedido(Pedido pedido, RedirectAttributes attributes) {
		
		long dias;
		
		try {
			String dtEntrega = formataDataPadraoBR(pedido.getDtEntrega().replaceAll("-", ""));
			
			if (!validaData(dtEntrega)) {
				attributes.addFlashAttribute("mensagem", "A data informada não é válida!");
				ModelAndView mv = new ModelAndView("redirect:/pedido/cadastrarPedido");
				return mv;
			}
			
			dias = calcularDiferecaDatas(formataDataHora(new Date(), "dd/MM/yyyy"), dtEntrega);
			
//			if (dias < 0) {
//				attributes.addFlashAttribute("mensagem", "A data da entrega não pode ser menor que a data atual!");
//				ModelAndView mv = new ModelAndView("redirect:/pedido/cadastrarPedido");
//				return mv;
//			}
			
			if (carrinho.size() == 0) {
				attributes.addFlashAttribute("mensagem", "Informe um ou mais produtos no pedido!");
				ModelAndView mv = new ModelAndView("redirect:/pedido/cadastrarPedido");
				return mv;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String dataPedido = formataDataHora(new Date(), "yyyyMMdd");
		String horaPedido = formataDataHora(new Date(), "HH:mm");

		this.pedido.setDtPedido(dataPedido.replaceAll("-", ""));
		this.pedido.setHrPedido(horaPedido);
		this.pedido.setDtEntrega(pedido.getDtEntrega().replaceAll("-", ""));
		this.pedido.setHrEntrega(pedido.getHrEntrega());

		if (pedido.getValorAuxAdiantamento().equals("")) {
			pedido.setValorAuxAdiantamento("0,00");
		}

		this.pedido.setValorAdiatamento(formataValorBanco(pedido.getValorAuxAdiantamento()));

		if (pedido.getValorAuxDesconto().equals("")) {
			pedido.setValorAuxDesconto("0,00");
		}

		this.pedido.setValorDesconto(formataValorBanco(pedido.getValorAuxDesconto()));

		if (pedido.getValorAuxTaxaEntrega().equals("")) {
			pedido.setValorAuxTaxaEntrega("0,00");
		}

		this.pedido.setValorTaxaEntrega(formataValorBanco(pedido.getValorAuxTaxaEntrega()));
		this.pedido.setValorTotal(this.pedido.getValorTotal().add(this.pedido.getValorTaxaEntrega()).subtract(this.pedido.getValorDesconto()));
		
		if (!this.pedido.getStatus().equalsIgnoreCase("P")) { // Status Pago - A pagar fica zero
			this.pedido.setValorAPagar(this.pedido.getValorTotal().subtract(this.pedido.getValorAdiatamento()));
		} else {
			this.pedido.setValorAPagar(new BigDecimal(0));
		}

		// Salva o Pedido
		pedidoService.salvar(this.pedido);

		System.out.println(this.pedido.getIdPedido());
		if (this.pedido.getIdPedido() != null) {
			pedidoItemService.excluirPorIdPedido(this.pedido.getIdPedido());
		}
		
		pedidoItemService.salvar(this.carrinho);
		idAuxItem = 0;
		ModelAndView mv = new ModelAndView("redirect:/pedido/listarPedido/Todos");
		return mv;
	}
	
	public String geraNumeroPedido() {
		Random random = new Random();
		Calendar cal = Calendar.getInstance();
		long randonPD = Math.abs(random.nextLong() / 999999999);
		randonPD = Math.abs(randonPD / 99999);
		int year = cal.get(Calendar.YEAR);
		String numPD = String.valueOf(year).concat("PD");
		numPD = numPD.concat(String.valueOf(randonPD));
		return this.numeroPedido = numPD;
	}

	@RequestMapping(value = "/pedido/visualizarInvoice/{id}", method = { RequestMethod.GET })
	public ModelAndView visualizarPedido(@PathVariable Long id, Model model) {
		List<PedidoItem> pedidoList = pedidoItemService.listarPorIDPedido(id);
		Pedido ped = pedidoService.buscarPorId(id);

		ped.setDtPedido(formataDataPadraoBR(ped.getDtPedido()));
		ped.setDtEntrega(formataDataPadraoBR(ped.getDtEntrega()));
		ped.setValorAuxAPagar(formataValorReal(ped.getValorAPagar()));
		ped.setValorAuxAdiantamento(formataValorReal(ped.getValorAdiatamento()));
		ped.setValorAuxTotal(formataValorReal(ped.getValorTotal()));
		ped.setValorAuxAPagar(formataValorReal(ped.getValorAPagar()));

		if (ped.getValorAdiatamento() != null) {
			ped.setValorAuxAdiantamento(formataValorReal(ped.getValorAdiatamento()));
		} else {
			ped.setValorAuxAdiantamento("R$ 0,00");
		}

		if (ped.getValorTaxaEntrega() != null) {
			ped.setValorAuxTaxaEntrega(formataValorReal(ped.getValorTaxaEntrega()));
		} else {
			ped.setValorAuxTaxaEntrega("R$ 0,00");
		}

		if (ped.getValorDesconto() != null) {
			ped.setValorAuxDesconto(formataValorReal(ped.getValorDesconto()));
		} else {
			ped.setValorAuxDesconto("R$ 0,00");
		}

		BigDecimal subTotal = new BigDecimal(0);

		for (PedidoItem p : pedidoList) {
			p.getCardapio().setValorAux(formataValorReal(p.getCardapio().getValor()));
			p.setValorAuxUnitario(formataValorReal(p.getValorUnitario()));
			p.setValorAuxTotal(formataValorReal(p.getValorTotal()));
			subTotal = subTotal.add(p.getValorTotal());
		}

		model.addAttribute("data", formataDataHora(new Date(), "dd/MM/yyyy"));
		model.addAttribute("ped", ped);
		model.addAttribute("subTotal", formataValorReal(subTotal));
		model.addAttribute("pedidoList", pedidoList);
		ModelAndView mv = new ModelAndView("pedido/pedidoInvoice");

		return mv;
	}

	@RequestMapping(value = "/pedido/entregarPedido/{id}", method = { RequestMethod.GET })
	public ModelAndView alterarParaEntregue(@PathVariable Long id, Model model, RedirectAttributes attributes) {
		Pedido ped = pedidoService.buscarPorId(id);

		if (!ped.getStatus().equals("P")) {
			ped.setStatus("E");
		} else {
			attributes.addFlashAttribute("mensagem", "Não é permitido alterar o status de pedidos que já foram pagos!");
		}

		// Salva o Pedido
		pedidoService.salvar(ped);

		ModelAndView mv = new ModelAndView("redirect:/pedido/listarPedido/Todos");

		return mv;
	}

	@RequestMapping(value = "/pedido/pagarPedido/{id}", method = { RequestMethod.GET })
	public ModelAndView alterarParaPago(@PathVariable Long id, Model model) {
		Pedido ped = pedidoService.buscarPorId(id);
		ped.setStatus("P");
		ped.setValorAPagar(new BigDecimal(0));

		// Salva o Pedido
		pedidoService.salvar(ped);

		ModelAndView mv = new ModelAndView("redirect:/pedido/listarPedido/Todos");

		return mv;
	}
	
	@RequestMapping(value = "/pedido/statusNovo/{id}", method = { RequestMethod.GET })
	public ModelAndView alterarParaNovo(@PathVariable Long id, Model model, RedirectAttributes attributes) {
		Pedido ped = pedidoService.buscarPorId(id);

		if (!ped.getStatus().equals("P")) {
			ped.setStatus("N");
		} else {
			attributes.addFlashAttribute("mensagem", "Não é permitido alterar o status de pedidos que já foram pagos!");
		}

		// Salva o Pedido
		pedidoService.salvar(ped);

		ModelAndView mv = new ModelAndView("redirect:/pedido/listarPedido/Todos");

		return mv;
	}

	@RequestMapping(value = "/pedido/excluirPedido/{id}", method = { RequestMethod.GET })
	public ModelAndView excluirPedido(@PathVariable Long id, RedirectAttributes attributes) {
		Pedido ped = pedidoService.buscarPorId(id);
		
		if (ped.getStatus().equals("N") || ped.getStatus().equals("A")) {
			pedidoItemService.excluirPorIdPedido(id);
			pedidoService.excluir(id);
		} else {
			attributes.addFlashAttribute("mensagem", "Não é permitido apagar pedidos que já foram pagos!");
		}

		ModelAndView mv = new ModelAndView("redirect:/pedido/listarPedido/Todos");
		return mv;
	}
	
	@RequestMapping(value = "/pedido/imprimirPedido/{id}", method = { RequestMethod.GET })
	public ModelAndView imprimirPedido(@PathVariable Long id, Model model) {
		List<PedidoItem> pedidoList = pedidoItemService.listarPorIDPedido(id);
		Pedido ped = pedidoService.buscarPorId(id);

		ped.setDtPedido(formataDataPadraoBR(ped.getDtPedido()));
		ped.setDtEntrega(formataDataPadraoBR(ped.getDtEntrega()));
		ped.setValorAuxAPagar(formataValorReal(ped.getValorAPagar()));
		ped.setValorAuxTotal(formataValorReal(ped.getValorTotal()));

		if (ped.getValorAdiatamento() != null) {
			ped.setValorAuxAdiantamento(formataValorReal(ped.getValorAdiatamento()));
		} else {
			ped.setValorAuxAdiantamento("R$ 0,00");
		}

		if (ped.getValorTaxaEntrega() != null) {
			ped.setValorAuxTaxaEntrega(formataValorReal(ped.getValorTaxaEntrega()));
		} else {
			ped.setValorAuxTaxaEntrega("R$ 0,00");
		}

		if (ped.getValorDesconto() != null) {
			ped.setValorAuxDesconto(formataValorReal(ped.getValorDesconto()));
		} else {
			ped.setValorAuxDesconto("R$ 0,00");
		}

		BigDecimal subTotal = new BigDecimal(0);

		for (PedidoItem p : pedidoList) {
			p.getCardapio().setValorAux(formataValorReal(p.getCardapio().getValor()));
			p.setValorAuxUnitario(formataValorReal(p.getValorUnitario()));
			p.setValorAuxTotal(formataValorReal(p.getValorTotal()));
			subTotal = subTotal.add(p.getValorTotal());
		}

		model.addAttribute("data", formataDataHora(new Date(), "dd/MM/yyyy"));
		model.addAttribute("ped", ped);
		model.addAttribute("subTotal", formataValorReal(subTotal));
		model.addAttribute("pedidoList", pedidoList);
		
		ModelAndView mv = new ModelAndView("pedido/pedidoPrint");
		return mv;
	}
	
	
	@RequestMapping(value="/pedido/editarPedido/{id}", method={RequestMethod.GET})
	public ModelAndView editarPedido(@PathVariable Long id, Model model) {
		this.carrinho = new ArrayList<PedidoItem>();
		this.carrinhoAux = new ArrayList<PedidoItem>();
		
		Pedido pedido = pedidoService.buscarPorId(id);
		pedido.setDtEntrega(formataDataPadrao(pedido.getDtEntrega()));
		pedido.setValorAuxTaxaEntrega(formataValorReal(pedido.getValorTaxaEntrega()));
		pedido.setValorAuxAdiantamento(formataValorReal(pedido.getValorAdiatamento()));
		pedido.setValorAuxDesconto(formataValorReal(pedido.getValorDesconto()));
		
		List<PedidoItem> listP = pedidoItemService.listarPorIDPedido(pedido.getIdPedido());
		BigDecimal valorTotal = new BigDecimal(0);
		
		for (PedidoItem pItem : listP) {
			Cardapio produtoSelecionado = cardapioService.buscarPorId(pItem.getCardapio().getIdItemCardapio());
			BigDecimal totaParcial = produtoSelecionado.getValor().multiply(pItem.getQuantidade()); // Total do Item (Parcial)	
			valorTotal = valorTotal.add(totaParcial);
			
			PedidoItem pedidoItem = new PedidoItem();
			pedidoItem.setIdPedidoItem(pItem.getIdPedidoItem());
			pedidoItem.setCardapio(produtoSelecionado);
			pedidoItem.setPedido(pedido);
			pedidoItem.setQuantidade(pItem.getQuantidade());
			pedidoItem.setValorTotal(totaParcial);
			pedidoItem.setValorUnitario(produtoSelecionado.getValor());
			pedidoItem.setValorAuxTotal(formataValorReal(totaParcial));

			idAuxItem ++;
			pedidoItem.setIdAux(idAuxItem);
			this.carrinho.add(pedidoItem);
			this.carrinhoAux.add(pedidoItem);
		}
		
		pedido.setValorTotal(valorTotal);
		pedido.setValorAuxTotal(formataValorReal(pedido.getValorTotal()));
		this.numeroPedido = pedido.getNumeroPedido();
		this.pedido = pedido;
		this.cliente = usuarioService.buscarPorId(pedido.getUsuario().getIdUsuario());
		this.cboCardapio = cardapioService.listar();
		
		
		model.addAttribute("numeroPedido", this.numeroPedido);
		model.addAttribute("pedido_alt", pedido);
		model.addAttribute("cboUsuarios", this.cliente);
		model.addAttribute("cboCardapio", this.cboCardapio);
		model.addAttribute("carrinho", this.carrinho);
		
		ModelAndView mv = new ModelAndView("pedido/pedidoAlt"); // Caminho da pasta do arq
		return mv;
	}

}