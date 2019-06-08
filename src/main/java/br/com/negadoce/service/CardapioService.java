package br.com.negadoce.service;

import java.util.List;

import br.com.negadoce.model.Cardapio;

public interface CardapioService {

	public void salvar(Cardapio cardapio);

	public List<Cardapio> listar();
	
	public Cardapio buscarPorId(Long id);
	
	public void excluir(Long id);

	public List<Cardapio> listarPedidosPrincipais();

}
