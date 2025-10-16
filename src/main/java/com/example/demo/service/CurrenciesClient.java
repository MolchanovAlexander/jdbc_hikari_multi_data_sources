package com.example.demo.service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;


@Component
public class CurrenciesClient {

    private static final String NBU_URL = "https://bank.gov.ua/NBU_Exchange/exchange?json";

    public JSONArray getNBUrates(String codeL) {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(NBU_URL))
                .build();

        try {
            HttpResponse<String> response = httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            return new JSONArray(response.body());
        } catch (IOException | InterruptedException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
