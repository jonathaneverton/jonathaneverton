package br.com.negadoce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.negadoce.model.Cardapio;

public interface CardapioRepository extends CrudRepository<Cardapio, Long> {

	@Query("Select c from Cardapio c where c.principal = true")
	public List<Cardapio> listCardapioPrincipais();

	@Query("Select c from Cardapio c order by c.nomeItemCardapio")
	public List<Cardapio> listar();

}
