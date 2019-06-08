package br.com.negadoce.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.negadoce.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long> {

	@Query("Select sum(valorTotal) from Pedido")
	public BigDecimal getValorTotalPedidos();

	@Query("Select count(*) from Pedido")
	public long getTotalPedidos();
	
	@Query("Select count(*) from Pedido p Where p.status = :status")
	public long getQtPedidosPorStatus(@Param("status") String status);

	@Query("Select p from Pedido p Where p.status = :status")
	public List<Pedido> listarPorStatus(@Param("status") String status);
	
	@Query("Select p from Pedido p Order by p.status, p.dtEntrega asc")
	public List<Pedido> listarTodos();

	@Query("Select p from Pedido p Order by p.status, p.dtEntrega asc")
	public List<Pedido> listarUltimosPedidos();
	
}