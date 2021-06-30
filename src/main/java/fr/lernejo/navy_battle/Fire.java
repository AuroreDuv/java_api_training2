package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Fire implements HttpHandler {
    final private String shipState = "sunk";
    final private Boolean shipLeft = true;

    public String getConsequence() {
        return shipState;
    }

    public Boolean getShipLeft() {
        return shipLeft;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String body;

        try (InputStream inputSchema = getClass().getResourceAsStream("/schema_fire.json")) { // Get Json Schema for validation
            SchemaLoader.load(new JSONObject(new JSONTokener(inputSchema))).validate(new JSONObject(new JSONTokener(exchange.getRequestBody()))); // Json Schema Validation

            String shipState = getConsequence();
            Boolean shipLeft = getShipLeft();
            String cell = exchange.getRequestURI().getQuery().replace("cell=", "");
            System.out.println(cell);
            body = "{\"consequence\": \"" + shipState + "\", \"shipLeft\": " + shipLeft + "}";
            exchange.sendResponseHeaders(202, body.length());
        } catch (Exception e) {
            body = "Bad Request";
            exchange.sendResponseHeaders(400, body.length());
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }
}
