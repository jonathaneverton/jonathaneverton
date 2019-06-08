package br.com.negadoce.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NamedNativeQuery;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.negadoce.model.PedidoItem;

public interface PedidoItemRepository extends CrudRepository<PedidoItem, Long> {

	@Query("Select p from PedidoItem p Where p.pedido.idPedido = :idPedido")
	public List<PedidoItem> buscaItensPedido(@Param("idPedido") Long idPedido);

	@Query("Delete from PedidoItem p Where p.pedido.idPedido = :idPedido")
	public void excluir(@Param("idPedido") Long idPedido);

	@Query("Select sum(b.valorTotal) from Pedido a, PedidoItem b "
		 + "Where a.idPedido = b.pedido.idPedido "
		 + "and   b.cardapio.idItemCardapio = :idproduto "
		 + "and   a.dtEntrega >= :dtInicial "
		 + "and   a.dtEntrega <= :dtFinal")
	public String buscaValTotalMensalPorProduto(@Param("idproduto") long idproduto, @Param("dtInicial") String dtInicial, @Param("dtFinal") String dtFinal);

	@Query("Select p from PedidoItem p")
	public List<PedidoItem> listar();

	@Transactional
	@Modifying
	@Query("Delete from PedidoItem p Where p.pedido.idPedido = :idPedido")
	public void excluirPorIdPedido(@Param("idPedido") Long idPedido);
	
	@Query("Select i from PedidoItem i, Pedido p Where i.pedido.idPedido = p.idPedido Order By p.dtEntrega, p.numeroPedido asc")
	public List<PedidoItem> listarTodosPedidos();

//	@Query("Select i from PedidoItem i, Pedido p "
//		 + "Where i.pedido.idPedido = p.idPedido "
//		 + "and p.status = :status "
//		 + "Order By p.dtEntrega, p.numeroPedido asc")
//	public List<PedidoItem> listarPedidosPorStatus(@Param("status")String status);
	
	@Query(value = "?0", nativeQuery = true)
	public String listarPedidosPorStatus(String sql);
	
	
	

}
