package ru.yandex.practicum.tasktraker.controller.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVClient {
    private final HttpClient client;
    private final String uri;
    private String apiToken;

    public KVClient(String uri) {
        this.client = HttpClient.newHttpClient();
        this.uri = uri;
        this.apiToken = register();
    }

    private String register() {
        URI url = URI.create(uri + "register/");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        return request(request).body();
    }

    private HttpResponse<String> request(HttpRequest request) {
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        try {
            return client.send(request, handler);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(String value, String gson) {
        URI url = URI.create(uri + "save/" + value + "?API_TOKEN=" + apiToken);
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(url)
                .header("Accept", "application/json")
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        request(request);
    }

    public String load(String value) {
        URI url = URI.create(uri + "load/" + value + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        return request(request).body();
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
