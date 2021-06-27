package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {
    private HttpServer server;

    public void start_server(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        server.createContext("/ping", new CallGetHandler());
        server.createContext("/api/game/start", new CallPostHandler());
        server.setExecutor(executor);
        server.start();

        this.server = server;
    }

    public void stop_server() {
        this.server.stop(0);
    }

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);

        Launcher launcher = new Launcher();

        launcher.start_server(port);
    }
}
