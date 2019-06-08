package br.com.negadoce.service;

import java.util.List;

import br.com.negadoce.model.Usuario;

public interface UsuarioService {

	public void salvar(Usuario usuario);

	public List<Usuario> listar();

	public Usuario buscarPorId(Long id);
	
	public void excluir(Long id);

	public long getQtUsuarios();
	
}
