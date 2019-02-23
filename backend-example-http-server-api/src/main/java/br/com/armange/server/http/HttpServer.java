package br.com.armange.server.http;

public interface HttpServer {

    void start() throws Exception;
    
    void stop() throws Exception;
}
