package br.com.armange.jaxrs;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import br.com.armange.hello.Hello;

@ApplicationPath("/")
public class Application extends ResourceConfig {

	public Application() {
		loadResources();
	}
	
	private void loadResources() {
		register(Hello.class);
	}
}
