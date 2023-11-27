package it.exobank.conf;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import it.exobank.rest.*;

@ApplicationPath("/rest")
public class ControllerRestApplication extends Application {
	
	private Set<Object> singletons;
	
	public ControllerRestApplication() {
		super();
		CorsFilter corsFilter = new CorsFilter();
		corsFilter.getAllowedOrigins().add("*");
		corsFilter.setAllowedMethods("GET, POST, DELETE, PUT, PATCH");
		singletons = new HashSet<Object>();
		singletons.add(corsFilter);
	}	
	
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}


	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> set = new HashSet<>();
		set.add(UtenteRest.class);
		set.add(ContoCorrenteRest.class);
		set.add(StatoContoCorrenteRest.class);
		set.add(TransazioneRest.class);
		set.add(StatoTransazioneRest.class);
		set.add(TipoTransazioneRest.class);
		return set;
	}


}
