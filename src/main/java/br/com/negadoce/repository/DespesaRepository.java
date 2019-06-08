package br.com.negadoce.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.negadoce.model.Despesa;

public interface DespesaRepository extends CrudRepository<Despesa, Long> {

}
