package br.com.negadoce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.negadoce.model.Usuario;
import br.com.negadoce.repository.UsuarioRepository;
import br.com.negadoce.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void salvar(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	@Override
	public List<Usuario> listar() {
		return (List<Usuario>) usuarioRepository.listarTodos();
	}

	@Override
	public Usuario buscarPorId(Long id) {
		Usuario usuario = usuarioRepository.findOne(id);
		return usuario;
	}

	@Override
	public void excluir(Long id) {
		usuarioRepository.delete(id);
	}

	@Override
	public long getQtUsuarios() {
		return usuarioRepository.count();
	}

}
