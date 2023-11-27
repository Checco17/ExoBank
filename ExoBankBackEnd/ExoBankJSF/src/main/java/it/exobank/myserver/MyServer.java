package it.exobank.myserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class MyServer {
    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestURI = httpExchange.getRequestURI().toString();
            if (requestURI.equals("/")) {
                String redirectUrl = "http://localhost:3000";
                httpExchange.getResponseHeaders().set("Location", redirectUrl);
                httpExchange.sendResponseHeaders(302, -1);
                httpExchange.close();
            } else {
                String response = "Ciao, mondo!";
                httpExchange.sendResponseHeaders(200, response.length());
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(response.getBytes());
                outputStream.close();
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("sto nel my server");
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
    }
}