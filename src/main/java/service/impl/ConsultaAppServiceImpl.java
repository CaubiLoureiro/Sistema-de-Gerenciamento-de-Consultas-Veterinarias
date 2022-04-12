package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.ConsultaDAO;
import dao.AnimalDAO;
import excecao.ConsultaNaoEncontradoException;
import excecao.AnimalNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import modelo.Consulta;
import modelo.Animal;
import service.ConsultaAppService;

public class ConsultaAppServiceImpl implements ConsultaAppService {

	@Autowired
	private AnimalDAO animalDAO;

	@Autowired
	private ConsultaDAO consultaDAO;

	@Transactional
	public long inclui(Consulta umaConsulta)
			throws AnimalNaoEncontradoException {
		// A implementa��o do m�todo getPorIdComLock(umProduto.getId())
		// impede que dois lances sejam cadastrados em paralelo, i. �,
		// os lances devem ser cadastrados obedecendo a uma fila. Isto
		// impede que o valor do segundo lance seja inferior ao valor do
		// primeiro.

		Animal umAnimal = umaConsulta.getAnimal();

		try {
			umAnimal = animalDAO.getPorIdComLock(umAnimal.getId());
		} catch (ObjetoNaoEncontradoException e) {
			throw new AnimalNaoEncontradoException("Animal n�o encontrado");
		}

		Consulta inventario = consultaDAO.inclui(umaConsulta);

		return inventario.getId();
	}

	
	@Transactional
	public void exclui(Consulta umaConsulta) throws ConsultaNaoEncontradoException {
		try {
			umaConsulta = consultaDAO.getPorId(umaConsulta.getId());
			
			consultaDAO.exclui(umaConsulta);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ConsultaNaoEncontradoException("Consulta n�o encontrado.");
		}
	}

	
	
	public Consulta recuperaUmConsulta(long numero) throws ConsultaNaoEncontradoException {
		try {
			return consultaDAO.getPorId(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new ConsultaNaoEncontradoException("Consulta n�o encontrado");
		}
	}

	
	
	public List<Consulta> recuperaConsultas() {
		return consultaDAO.recuperaListaDeConsultas();
	}
	
	
	
	
}