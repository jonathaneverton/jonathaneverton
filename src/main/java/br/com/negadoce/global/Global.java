package br.com.negadoce.global;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {

	protected long qtPedidosNovos;
	protected long qtPedidosPrioritarios;
	protected long qtPedidosEntregues;
	protected long qtPedidosPagos;

	/**
	 * Formata o objeto de acordo com o formato passado
	 * 
	 * @param obj
	 * @param formato
	 * @return
	 */
	public String formataDataHora(Object obj, String formato) {
		SimpleDateFormat format = new SimpleDateFormat(formato);
		return format.format(obj);
	}

	/**
	 * Formata a data que vem do banco de dados no padrão BR
	 * 
	 * @param dataBanco
	 * @return
	 */
	public String formataDataPadraoBR(String dataBanco) {
		String dataBR = "";
		dataBR = dataBanco.substring(6, 8).concat("/")
				.concat(dataBanco.substring(4, 6).concat("/").concat(dataBanco.substring(0, 4)));
		return dataBR;
	}
	
	/**
	 * Formata a data que vem do banco de dados no padrão BR para editar
	 * 
	 * @param dataBanco
	 * @return
	 */
	public String formataDataPadrao(String dataBanco) {
		String dataBR = "";
		dataBR = dataBanco.substring(0, 4).concat("-").concat(dataBanco.substring(4, 6)).concat("-").concat(dataBanco.substring(6, 8));
				
		return dataBR;
	}

	/**
	 * Formata o valor que vem do banco de dados para o formato Real
	 * 
	 * @param valor
	 * @return
	 */
	public String formataValorReal(BigDecimal valor) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(valor);
	}

	/**
	 * Formata o valor que vem da tela para ir pro banco de dados
	 * 
	 * @param valor
	 * @return
	 */
	public BigDecimal formataValorBanco(String valor) {
		valor = valor.replace("R$ ", "").replace(".", "").replace(",", ".");
		return new BigDecimal(valor);
	}

	/**
	 * Calcula o número de dias entre duas datas
	 * 
	 * @param inicio
	 * @param fim
	 * @return
	 * @throws ParseException
	 */
	public static long calcularDiferecaDatas(String inicio, String fim) throws ParseException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date dtInicial = df.parse(inicio);
		Date dtFinal = df.parse(fim);
		return (dtFinal.getTime() - dtInicial.getTime() + 3600000L) / 86400000L;
	}

	public boolean isNotNull(Object obj) {
		if (obj != null) {
			return true;
		}

		return false;
	}
	
	
	/**
	 * Valido se uma data é falsa
	 * 
	 * @param data
	 * @return
	 */
	public boolean validaData(String data) {
        try {
            //SimpleDateFormat é usada para trabalhar com formatação de datas
            //neste caso meu formatador irá trabalhar com o formato "dd/MM/yyyy"
            //dd = dia, MM = mes, yyyy = ano
            //o "M" dessa String é maiusculo porque "m" minusculo se n me engano é minutos
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            //a mágica desse método acontece aqui, pois o setLenient() é usado para setar
            //sua escolha sobre datas estranhas, quando eu seto para "false" estou dizendo
            //que não aceito datas falsas como 31/02/2016
            sdf.setLenient(false);
            //aqui eu tento converter a String em um objeto do tipo date, se funcionar
            //sua data é valida
            sdf.parse(data);
            //se nada deu errado retorna true (verdadeiro)
            return true;
        } catch (ParseException ex) {
            //se algum passo dentro do "try" der errado quer dizer que sua data é falsa, então,
            //retorna falso
            return false;
        }
    }
	

	public long getQtPedidosNovos() {
		return qtPedidosNovos;
	}

	public void setQtPedidosNovos(long qtPedidosNovos) {
		this.qtPedidosNovos = qtPedidosNovos;
	}

	public long getQtPedidosPrioritarios() {
		return qtPedidosPrioritarios;
	}

	public void setQtPedidosPrioritarios(long qtPedidosPrioritarios) {
		this.qtPedidosPrioritarios = qtPedidosPrioritarios;
	}

	public long getQtPedidosEntregues() {
		return qtPedidosEntregues;
	}

	public void setQtPedidosEntregues(long qtPedidosEntregues) {
		this.qtPedidosEntregues = qtPedidosEntregues;
	}

	public long getQtPedidosPagos() {
		return qtPedidosPagos;
	}

	public void setQtPedidosPagos(long qtPedidosPagos) {
		this.qtPedidosPagos = qtPedidosPagos;
	}

}