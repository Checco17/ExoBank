  package it.exobank.conf;

import javax.naming.InitialContext;

public class EjbService<T> {

	private final static String PREFIX = "java:global/ExoBankEAR/ExoBankEjb/";

	private Class<T> interfaceClass;

	public EjbService(Class<T> interfaceClass) {
		this.interfaceClass = interfaceClass;
	}

	@SuppressWarnings("unchecked") // UTILIZZATA PER SOPPRIMERE I WARMING E GLI AVVISI DI ERRORE
	public T getEJB() throws Exception {
		InitialContext context = new InitialContext();
		return (T) context.lookup(PREFIX + interfaceClass.getSimpleName() + "!" + interfaceClass.getName());
	}

}
