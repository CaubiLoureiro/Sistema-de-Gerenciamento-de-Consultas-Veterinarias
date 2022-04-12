package aspecto;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import anotacao.Perfil;
import excecao.UsuarioNaoAutorizadoException;
import modelo.SingletonPerfis;

@Aspect
public class AspectoAutorizado {
	
	@Pointcut("call(* service..*.*(..))") //Pointcut para todos os programas dentro do package service
	public void efetuaAutorizacao() {
		
	}
	
	
	@Around("efetuaAutorizacao()")
	public Object efetuaAutorizacao(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			String[] perfilUser = SingletonPerfis.getSingletonPerfis().getPerfis(); // retorna lista de perfis do usu�rio
			MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // pega assinatura do m�todo utilizado
			Method metodo = signature.getMethod(); // pega o metodo que contem a assinatura
			if (metodo.isAnnotationPresent(Perfil.class)){ // verifica se a anotacao Perfil est� presente no m�todo
				String[] perfilMetodo = metodo.getAnnotation(Perfil.class).nomes(); // criamos uma lista de dos perfis associados ao metodo
				if(perfilMetodo!=null && perfilMetodo.length >0) { // verifica se a lista de perfis do metodo � diferente de null e se o tamanho � maior que 0
					if(perfilUser!=null) { // verifica se a lista de perfil de user � diferente de null
						for (String acessoPermitido : perfilMetodo) { //percorremos perfilMetodo
							for(String cargoUser : perfilUser) { // percorremos perfilUser
								if(cargoUser. equals(acessoPermitido)){ // verificamos se o cargo do usuario � igual ao requerido pelo metodo
									return joinPoint.proceed(); // continuamos com a execu��o do m�todo
								}
							}				
						}
					}
					throw new UsuarioNaoAutorizadoException("Acesso Negado! \n Voc� n�o possui o perfil necess�rio para acessar esse recurso."); // aqui lan�amos a exce��o
				}				
			}
			return joinPoint.proceed(); //se a anota��o Perfil n�o est� presente, continua a execu��o do m�todo
		} catch(Throwable throwable) {
			throw throwable;
		}
	}
}