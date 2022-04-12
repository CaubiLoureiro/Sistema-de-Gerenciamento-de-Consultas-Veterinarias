package dao;

import java.util.List;
import java.util.Set;

import modelo.Dono;
import anotacao.RecuperaConjunto;
import anotacao.RecuperaLista;
import anotacao.RecuperaObjeto;
import anotacao.RecuperaUltimoOuPrimeiro;
import excecao.ObjetoNaoEncontradoException;

public interface DonoDAO extends DaoGenerico<Dono, Long> {
	/* ****** M�todos Gen�ricos ******* */

	@RecuperaObjeto
	Dono recuperaUmDonoEAnimais(long numero) throws ObjetoNaoEncontradoException;

	@RecuperaLista
	List<Dono> recuperaListaDeDonos();

	@RecuperaUltimoOuPrimeiro
	Dono recuperaPrimeiroDono() throws ObjetoNaoEncontradoException;

	@RecuperaLista
	List<Dono> recuperaListaDeDonosEAnimais();
	
	@RecuperaConjunto
	Set<Dono> recuperaConjuntoDeDonosEAnimais();

	/* ****** M�todos n�o Gen�ricos ******* */

	// Um m�todo definido aqui, que n�o seja anotado, dever� ser
	// implementado como final em ProdutoDAOImpl.
}
