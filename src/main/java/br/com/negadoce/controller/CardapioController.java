package br.com.negadoce.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

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
import br.com.negadoce.service.CardapioService;

@Controller
public class CardapioController extends Global {

	@Autowired
	private CardapioService cardapioService;
	
	private Cardapio itemCardapio = new Cardapio();

	// NAVEGACAO
	
	@RequestMapping(value = "/cardapio/listarCardapio", method = { RequestMethod.GET })
	public ModelAndView listagem(Model model) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		List<Cardapio> cardapioList = cardapioService.listar();
		
		for (Cardapio aux: cardapioList) {
			String valorFormatado = nf.format(aux.getValor());
			aux.setValorAux(valorFormatado);
		}
		
		model.addAttribute("cardapioList", cardapioList);
		ModelAndView mv = new ModelAndView("cardapio/cardapio");
		return mv;
	}

	@RequestMapping(value = "/cardapio/cadastrarItemCardapio", method = { RequestMethod.GET })
	public ModelAndView cadastrarNovoItem(Model model) {
		Cardapio itemCardapio = new Cardapio();
		model.addAttribute("itemCardapio", itemCardapio);
		ModelAndView mv = new ModelAndView("cardapio/cardapioCad");
		return mv;
	}
	
	/**
	 * Página solicitada quando há um erro no form
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cardapio/cadastrarItemCardapioErro", method = { RequestMethod.GET })
	public ModelAndView cadastroItemComErro(Model model) {
		model.addAttribute("itemCardapio", this.itemCardapio);
		ModelAndView mv = new ModelAndView("cardapio/cardapioCad");
		return mv;
	}
	
	@RequestMapping(value="/cardapio/alterarItemCardapio/{id}", method={RequestMethod.GET})
	public ModelAndView alterar(@PathVariable Long id, Model model) {
		Cardapio itemCardapio = new Cardapio();
		itemCardapio = cardapioService.buscarPorId(id);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String valorFormatado = nf.format(itemCardapio.getValor());
		itemCardapio.setValorAux(valorFormatado); // Transforma o valor vindo do banco pra String e seta na variável valorAux
		model.addAttribute("itemCardapio", itemCardapio);
		ModelAndView mv = new ModelAndView("cardapio/cardapioAlt"); // Caminho da pasta do arq
		return mv;
	}
	
	// ACAO

	@RequestMapping(value = "/cardapio/salvar", method = { RequestMethod.POST })
	public ModelAndView salvar(Cardapio cardapio, Model model, RedirectAttributes attributes) {
		BigDecimal valor = formataValorBanco(cardapio.getValorAux());
		ModelAndView mv = new ModelAndView("redirect:/cardapio/listarCardapio");
		
		System.out.println(cardapio.isPrincipal());
		

		
		if (valor.longValue() <= 0) {
			attributes.addFlashAttribute("mensagem", "O valor do produto não pode ser negativo!");
			this.itemCardapio = cardapio;
			mv = new ModelAndView("redirect:/cardapio/cadastrarItemCardapioErro");
			return mv;
		} else {
			cardapio.setValor(cardapio.getValorAux()); // Seta na variável valor o valor da String que veio da tela para ser salva no banco
			cardapioService.salvar(cardapio);
			attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso!");
		}
		
		mv = new ModelAndView("redirect:/cardapio/listarCardapio");
		return mv;
	}

	@RequestMapping(value = "/cardapio/excluir/{id}", method = { RequestMethod.GET })
	public ModelAndView excluir(@PathVariable Long id) {
		cardapioService.excluir(id);
		ModelAndView mv = new ModelAndView("redirect:/cardapio/listarCardapio");
		return mv;
	}

}