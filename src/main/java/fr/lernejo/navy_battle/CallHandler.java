package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

class CallGetHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String body = "OK";
        exchange.sendResponseHeaders(200, body.length());

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }
}

class CallPostHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String body;
        try (InputStream inputSchema = getClass().getResourceAsStream("/schema.json")) { // Get Json Schema for validation
            // Get request Body (POST)
            InputStream inputRequestBody = exchange.getRequestBody();

            // Json Schema Validation
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputSchema));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(new JSONTokener(inputRequestBody)));

            // Send response with status Accepted 202
            body = "{\"id\": \"2aca7611-0ae4-49f3-bf63-75bef4769028\", \"url\": \"http://localhost:9876\", \"message\": \"May the best code win\"}";
            exchange.sendResponseHeaders(202, body.length());
        } catch (Exception e) {
            body = "Bad Request";
            exchange.sendResponseHeaders(400, body.length());
            e.printStackTrace();
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }
}
