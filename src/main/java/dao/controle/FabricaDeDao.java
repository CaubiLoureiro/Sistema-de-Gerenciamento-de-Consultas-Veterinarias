package dao.controle;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dao.impl.AnimalDAOImpl;
import dao.impl.ConsultaDAOImpl;
import dao.impl.DonoDAOImpl;
import dao.impl.EspecieDAOImpl;
import net.sf.cglib.proxy.Enhancer;

// A anota��o @Configuration indica que esta classe possui um ou mais m�todos anotados com @Bean.
@Configuration
public class FabricaDeDao {
	
	// @Bean diz ao Spring:
	// Aqui est� uma inst�ncia da classe ProdutoDAOImpl, por favor, 
	// guarde esta inst�ncia e me devolva quando eu a pedir.

	// @Autowired em ProdutoAppServiceImpl diz:
	// Por favor, me retorne uma inst�ncia do tipo ProdutoDAO, isto �,  
	// aquela que foi criada mais cedo com a anota��o @Bean.
	
	
	
	@Bean
	public static AnimalDAOImpl getJogadorDao() throws Exception {
		return getDao(dao.impl.AnimalDAOImpl.class);
	}

	@Bean
	public static ConsultaDAOImpl getInventarioDao() throws Exception {
		return getDao(dao.impl.ConsultaDAOImpl.class);
	}

	@Bean
	public static EspecieDAOImpl getEspecieDao() throws Exception {
		return getDao(dao.impl.EspecieDAOImpl.class);
	}	
	
	@Bean
	public static DonoDAOImpl getDonoDao() throws Exception {
		return getDao(dao.impl.DonoDAOImpl.class);
	}	
	
	@SuppressWarnings("unchecked")
	public static <T> T getDao(Class<T> classeDoDao) throws Exception {
		return (T) Enhancer.create(classeDoDao, new InterceptadorDeDAO());
	}
}