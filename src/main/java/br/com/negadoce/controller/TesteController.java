package br.com.negadoce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.negadoce.model.Teste;

@Controller
public class TesteController {
	
	@RequestMapping(value="/teste", method={RequestMethod.GET})
	public ModelAndView listar(Model model) {
		Teste teste = new Teste();
		model.addAttribute("teste", teste);
		ModelAndView mv = new ModelAndView("teste/teste");
		return mv;
	}
	
	@RequestMapping(value="/teste/salvar", method={RequestMethod.POST})
	public ModelAndView listar(Teste teste, Model model) {
		System.out.println(teste.getTeste());
		
		ModelAndView mv = new ModelAndView("teste/teste");
		return mv;
	}

}
