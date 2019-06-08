package br.com.negadoce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.negadoce.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	
	@Query("Select u from Usuario u Order by u.nome asc")
	public List<Usuario> listarTodos();

}
