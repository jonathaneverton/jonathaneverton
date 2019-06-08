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
import br.com.negadoce.model.Despesa;
import br.com.negadoce.service.DespesaService;

@Controller
public class DespesaController extends Global {

	@Autowired
	private DespesaService despesaService;
	
	private Despesa despesa = new Despesa();

	// NAVEGACAO
	
	@RequestMapping(value = "/despesa/listarDespesa", method = { RequestMethod.GET })
	public ModelAndView listagem(Model model) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		List<Despesa> despesaList = despesaService.listar();
		
		for (Despesa aux: despesaList) {
			String valorFormatado = nf.format(aux.getValor());
			aux.setValorAux(valorFormatado);
			aux.setDtDespesa(formataDataPadraoBR(aux.getDtDespesa()));
		}
		
		model.addAttribute("despesaList", despesaList);
		ModelAndView mv = new ModelAndView("despesa/despesa");
		return mv;
	}

	@RequestMapping(value = "/despesa/cadastrarDespesa", method = { RequestMethod.GET })
	public ModelAndView cadastrarDespesa(Model model) {
		Despesa despesa = new Despesa();
		model.addAttribute("despesa", despesa);
		ModelAndView mv = new ModelAndView("despesa/despesaCad");
		return mv;
	}
	
	/**
	 * Página solicitada quando há um erro no form
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/despesa/cadastrarDespesaErro", method = { RequestMethod.GET })
	public ModelAndView cadastrarDespesaErro(Model model) {
		model.addAttribute("despesa", this.despesa);
		ModelAndView mv = new ModelAndView("despesa/despesaCad");
		return mv;
	}
	
	@RequestMapping(value="/despesa/alterarDespesa/{id}", method={RequestMethod.GET})
	public ModelAndView alterar(@PathVariable Long id, Model model) {
		Despesa despesa = new Despesa();
		despesa = despesaService.buscarPorId(id);
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String valorFormatado = nf.format(despesa.getValor());
		despesa.setValorAux(valorFormatado); // Transforma o valor vindo do banco pra String e seta na variável valorAux
		despesa.setDtDespesa(formataDataPadrao(despesa.getDtDespesa()));
		model.addAttribute("despesa", despesa);
		ModelAndView mv = new ModelAndView("despesa/despesaAlt"); // Caminho da pasta do arq
		return mv;
	}
	
	// ACAO

	@RequestMapping(value = "/despesa/salvar", method = { RequestMethod.POST })
	public ModelAndView salvar(Despesa despesa, Model model, RedirectAttributes attributes) {
		BigDecimal valor = formataValorBanco(despesa.getValorAux());
		ModelAndView mv = new ModelAndView("redirect:/despesa/listarDespesa");
		
		if (valor.longValue() <= 0) {
			attributes.addFlashAttribute("mensagem", "O valor da despesa não pode ser negativo!");
			this.despesa = despesa;
			mv = new ModelAndView("redirect:/despesa/cadastrarDespesaErro");
			return mv;
		} else {
			despesa.setDtDespesa(despesa.getDtDespesa().replaceAll("-", ""));
			despesa.setValor(despesa.getValorAux()); // Seta na variável valor o valor da String que veio da tela para ser salva no banco
			despesaService.salvar(despesa);
			attributes.addFlashAttribute("mensagem", "Despesa salva com sucesso!");
		}
		
		mv = new ModelAndView("redirect:/despesa/listarDespesa");
		return mv;
	}

	@RequestMapping(value = "/despesa/excluir/{id}", method = { RequestMethod.GET })
	public ModelAndView excluir(@PathVariable Long id) {
		despesaService.excluir(id);
		ModelAndView mv = new ModelAndView("redirect:/despesa/listarDespesa");
		return mv;
	}

}