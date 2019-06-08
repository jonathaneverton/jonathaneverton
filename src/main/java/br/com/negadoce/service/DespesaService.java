package br.com.negadoce.service;

import java.util.List;

import br.com.negadoce.model.Despesa;

public interface DespesaService {

	public void salvar(Despesa despesa);

	public List<Despesa> listar();
	
	public Despesa buscarPorId(Long id);
	
	public void excluir(Long id);

}
