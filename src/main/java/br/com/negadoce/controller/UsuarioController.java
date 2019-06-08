package br.com.negadoce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.negadoce.model.Usuario;
import br.com.negadoce.service.UsuarioService;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	// NAVEGACAO
	
	@RequestMapping(value="/usuario/listarUsuario", method={RequestMethod.GET})
	public ModelAndView listar(Model model) {
		List<Usuario> usuarios = usuarioService.listar();
		model.addAttribute("usuarios", usuarios);
		ModelAndView mv = new ModelAndView("usuario/usuario"); // Caminho da pasta do arq
		return mv;
	}
	
	@RequestMapping(value="/usuario/cadastrarUsuario", method={RequestMethod.GET})
	public ModelAndView cadastrar(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		ModelAndView mv = new ModelAndView("usuario/usuarioCad"); // Caminho da pasta do arq
		return mv;
	}
	
	@RequestMapping(value="/usuario/alterarUsuario/{id}", method={RequestMethod.GET})
	public ModelAndView alterar(@PathVariable Long id, Model model) {
		Usuario usuario = new Usuario();
		usuario = usuarioService.buscarPorId(id);
		model.addAttribute("usuario_alt", usuario);
		ModelAndView mv = new ModelAndView("usuario/usuarioAlt"); // Caminho da pasta do arq
		return mv;
	}
	
	// ACAO
	
	@RequestMapping(value="/usuario/salvar", method={RequestMethod.POST})
	public ModelAndView salvar(Usuario usuario, BindingResult bindingResult, RedirectAttributes attributes) {
		String caminhoNav = "/usuario/listarUsuario";
		
		if (bindingResult.hasErrors()) {
			caminhoNav =  "/usuario/cadastrarUsuario";
        } else {
        		usuarioService.salvar(usuario);
        }
		
		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		ModelAndView mv = new ModelAndView("redirect:" + caminhoNav); // Caminho da navegacao
		
		return mv;
	}
	
	
	/*
	 * @RequestMapping(value = "/posts/salvar", method=RequestMethod.POST)
	public ModelAndView salvar(@Valid Post post, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView("redirect:/posts/novo");
			return mv;
		}
		
		post.setCriacao(Calendar.getInstance());
		post.setAtualizacao(Calendar.getInstance());
		postService.salvar(post);
		
		ModelAndView mv = new ModelAndView("redirect:/posts/" + post.getId());
		attributes.addFlashAttribute("mensagem", "Post salvo com sucesso.");
		return mv;
	}
	 */
	
	
	@RequestMapping(value="/usuario/excluir/{id}", method={RequestMethod.GET})
	public ModelAndView excluir(@PathVariable Long id, RedirectAttributes attributes) {
		usuarioService.excluir(id);
		ModelAndView mv = new ModelAndView("redirect:/usuario/listarUsuario"); // Caminho da navegacao
		attributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
		return mv;
	}

}
