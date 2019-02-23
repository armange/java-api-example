package br.com.armange.tree.server.http.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.deltaspike.cdise.servlet.CdiServletContextListener;
import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.glassfish.jersey.servlet.ServletContainer;

import br.com.armange.tree.jaxrs.application.Application;
import br.com.armange.tree.server.http.configuration.PropertyKeyHandler;
import br.com.armange.tree.server.http.configuration.ServerProperties;

public class TomcatServer {
    
    private static TomcatServer INSTANCE;
    private static final Logger LOGGER = LogManager.getLogger(TomcatServer.class);
    private static final String SERVLET_NAME = "br.com.armange.jaxrs.Application";
    private final String contextPath = "/";
    private final String appBase = ".";
    
    private Tomcat tomcat;
    private Context context;
    
    private TomcatServer() {
        configureLogLevel();
    }

    private void configureLogLevel() {
        final String logLevel = ConfigResolver
                .resolve(PropertyKeyHandler.build(ServerProperties.LOG_LEVEL))
                .as(String.class)
                .withDefault(ServerProperties.DEFAULT_LOG_LEVEL)
                .getValue()
                .toUpperCase();
        
        Configurator.setRootLevel(Level.getLevel(logLevel));
    }
    
    public void start() throws LifecycleException {
        createAndConfigureTomcatServer();
        
        configureServerContext();
        
        addServletsToTomcatServer();
        
        startTomcatServer();
        
        logServerStatus();
    }

    private void createAndConfigureTomcatServer() {
        final Integer port = ConfigResolver
                .resolve(PropertyKeyHandler.build(ServerProperties.HTTP_SERVER_PORT))
                .as(Integer.class)
                .withDefault(Integer.parseInt(ServerProperties.DEFAULT_HTTP_SERVER_PORT))
                .getValue();
        
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getHost().setAppBase(appBase);
    }
    
    private void addServletsToTomcatServer() {
        
        Tomcat.addServlet(context, SERVLET_NAME, buildServletContainer());
        context.addServletMapping("/*", SERVLET_NAME);
    }

    private void configureServerContext() {
        context = tomcat.addWebapp(contextPath, appBase);
        
        final StandardContext standardContext = (StandardContext)context;
        standardContext.addApplicationListener(CdiServletContextListener.class.getName());
    }
    
    private static ServletContainer buildServletContainer() {
        return new ServletContainer(new Application());
    }

    private void startTomcatServer() {
        final Thread tomcatThread = new Thread(() -> {
            try {
                tomcat.start();
                tomcat.getServer().await();    
            } catch (final LifecycleException e) {
                e.printStackTrace();
            }
        });
        
        tomcatThread.start();
    }
    
    private void logServerStatus() {
        while(!tomcat.getServer().getState().equals(LifecycleState.STARTED)) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Started successfully!!");
    }

    public static TomcatServer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TomcatServer();
        }
        
        return INSTANCE;
    }
    
    public static void main(String[] args) throws LifecycleException {
        TomcatServer.getInstance().start();
    }
}
