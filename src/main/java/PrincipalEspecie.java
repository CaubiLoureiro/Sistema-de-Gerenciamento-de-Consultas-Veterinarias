import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import corejava.Console;
import excecao.EspecieNaoEncontradoException;
import modelo.Animal;
import modelo.Especie;
import modelo.SingletonPerfis;
import service.EspecieAppService;

public class PrincipalEspecie {
	public static void main(String[] args) {
		String nome;
		Especie umaEspecie;

		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		EspecieAppService especieAppService = (EspecieAppService) fabrica.getBean("especieAppService");
		
		 SingletonPerfis singletonPerfis = SingletonPerfis.getSingletonPerfis();
		
	     singletonPerfis.setPerfis(new String[] {"user"});

		boolean continua = true;
		while (continua) {
			System.out.println('\n' + "O que voc� deseja fazer?");
			System.out.println('\n' + "1. Cadastrar uma esp�cie");
			System.out.println("2. Alterar uma esp�cie");
			System.out.println("3. Remover uma esp�cie");
			System.out.println("4. Listar uma esp�cie e seus animais");
			System.out.println("5. Listar todas as esp�cies e seus animais");
			System.out.println("6. Sair");

			int opcao = Console.readInt('\n' + "Digite um n�mero entre 1 e 6:");

			switch (opcao) {
			case 1: {
				nome = Console.readLine('\n' + "Informe o nome da esp�cie: ");
				
				Especie especie = new Especie(nome);

				long numero = especieAppService.inclui(especie);

				System.out.println('\n' + "Esp�cie n�mero " + numero + " inclu�da com sucesso!");

				break;
			}

			case 2: {
				int resposta = Console.readInt('\n' + "Digite o n�mero da esp�cie que voc� deseja alterar: ");

				try {
					umaEspecie = especieAppService.recuperaUmEspecie(resposta);
				} catch (EspecieNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaEspecie.getId() + 
						              "    Nome = " + umaEspecie.getNome());

				System.out.println(" ");

				int opcaoAlteracao = 1;

				switch (opcaoAlteracao) {
				case 1:
					String novoNome = Console.readLine("Digite o novo nome: ");
					umaEspecie.setNome(novoNome);

					try {
						especieAppService.altera(umaEspecie);

						System.out.println('\n' + "Altera��o de nome efetuada com sucesso!");
					} catch (EspecieNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}

					break;

				
				
				default:
					System.out.println('\n' + "Op��o inv�lida!");
				}

				break;
			}
			
			
			

			case 3: {
				int resposta = Console.readInt('\n' + "Digite o n�mero do dono que voc� deseja remover: ");

				try {
					umaEspecie = especieAppService.recuperaUmEspecie(resposta);
				} catch (EspecieNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaEspecie.getId() + 
			              "    Nome = " + umaEspecie.getNome());

				String resp = Console.readLine('\n' + "Confirma a remo��o da esp�cie?");

				if (resp.equals("s")) {
					try {
						especieAppService.exclui(umaEspecie);
						System.out.println('\n' + "Esp�cie removido com sucesso!");
					} catch (EspecieNaoEncontradoException e) {
						System.out.println('\n' + e.getMessage());
					}
				} else {
					System.out.println('\n' + "Esp�cie n�o removida.");
				}

				break;
			}
			
			
			
			

			case 4: {
				long numero = Console.readInt('\n' + "Informe o n�mero da esp�cie: ");

				try {
					umaEspecie = especieAppService.recuperaUmEspecieEAnimais(numero);
				} catch (EspecieNaoEncontradoException e) {
					System.out.println('\n' + e.getMessage());
					break;
				}

				System.out.println('\n' + "N�mero = " + umaEspecie.getId() + 
			              "    Nome = " + umaEspecie.getNome());

				List<Animal> animais = umaEspecie.getAnimais();

				for (Animal animal : animais) {
					System.out.println( '\n'+"N�mero = " + animal.getId() + 
				              "   Nome = " + animal.getNome() + 
				              "   Data de Nascimento = " + animal.getDataNasc() +
				              "   Ra�a = " + animal.getRaca()
								);

				}

				break;
			}

			case 5: {
				List<Especie> especies = especieAppService.recuperaEspeciesEAnimais();

				if (especies.size() != 0) {
					System.out.println("");

					for (Especie especie : especies) {
						System.out.println('\n' + "N�mero = " + especie.getId() + 
					              "    Nome = " + especie.getNome());

						List<Animal> animais = especie.getAnimais();

						for (Animal animal : animais) {
							System.out.println( '\n'+"N�mero = " + animal.getId() + 
						              "   Nome = " + animal.getNome() + 
						              "   Data de Nascimento = " + animal.getDataNasc() +
						              "   Ra�a = " + animal.getRaca()
										);

						}
					}
				} else {
					System.out.println('\n' + "N�o h� esp�cies cadastrados.");
				}

				break;
			}

			case 6: {
				continua = false;
				break;
			}

			
			default:
				System.out.println('\n' + "Op��o inv�lida!");
			}
		}
	}
}
