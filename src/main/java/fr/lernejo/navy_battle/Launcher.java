package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Launcher {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(args[0]);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        server.createContext("/ping", new CallHandler());
        server.setExecutor(executor);
        server.start();
    }
}
