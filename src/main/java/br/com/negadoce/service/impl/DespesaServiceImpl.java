package br.com.negadoce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.negadoce.model.Despesa;
import br.com.negadoce.repository.DespesaRepository;
import br.com.negadoce.service.DespesaService;

@Service
public class DespesaServiceImpl implements DespesaService {

	@Autowired
	private DespesaRepository despesaRepository;

	@Override
	public void salvar(Despesa despesa) {
		despesaRepository.save(despesa);
	}

	@Override
	public List<Despesa> listar() {
		List<Despesa> listDespesa = (List<Despesa>) despesaRepository.findAll();
		return listDespesa;
	}
	
	@Override
	public Despesa buscarPorId(Long id) {
		Despesa itemDespesa = despesaRepository.findOne(id);
		return itemDespesa;
	}

	@Override
	public void excluir(Long id) {
		despesaRepository.delete(id);
	}

}
