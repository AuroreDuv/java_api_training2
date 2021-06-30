package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

class StartGame implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String body;
        try (InputStream inputSchema = getClass().getResourceAsStream("/schema.json")) { // Get Json Schema for validation
            JSONObject request = new JSONObject(new JSONTokener(exchange.getRequestBody()));
            SchemaLoader.load(new JSONObject(new JSONTokener(inputSchema))).validate(request); // Json Schema Validation
            // Send response with status Accepted 202
            body = "{\"id\": \"2aca7611-0ae4-49f3-bf63-75bef4769028\", \"url\": \"http://localhost:" + exchange.getRemoteAddress().getPort() + "\", \"message\": \"May the best code win\"}";
            exchange.sendResponseHeaders(202, body.length());

            ProcessBuilder pb = new ProcessBuilder("curl", request.get("url").toString() + "/api/game/fire?cell=B2");
            pb.start();
        } catch (Exception e) {
            body = "Bad Request";
            exchange.sendResponseHeaders(400, body.length());
        }
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }
}
