package br.com.negadoce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.negadoce.model.Cardapio;
import br.com.negadoce.repository.CardapioRepository;
import br.com.negadoce.service.CardapioService;

@Service
public class CardapioServiceImpl implements CardapioService {

	@Autowired
	private CardapioRepository cardapioRepository;

	@Override
	public void salvar(Cardapio cardapio) {
		cardapioRepository.save(cardapio);
	}

	@Override
	public List<Cardapio> listar() {
		List<Cardapio> listCardapio = (List<Cardapio>) cardapioRepository.listar();
		return listCardapio;
	}
	
	@Override
	public Cardapio buscarPorId(Long id) {
		Cardapio itemCardapio = cardapioRepository.findOne(id);
		return itemCardapio;
	}

	@Override
	public void excluir(Long id) {
		cardapioRepository.delete(id);
	}

	@Override
	public List<Cardapio> listarPedidosPrincipais() {
		List<Cardapio> listCardapioPrincipais = (List<Cardapio>) cardapioRepository.listCardapioPrincipais();
		return listCardapioPrincipais;
	}

}
