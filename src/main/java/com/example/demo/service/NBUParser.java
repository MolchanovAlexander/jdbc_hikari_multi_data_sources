package com.example.demo.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class NBUParser {

    private final CurrenciesClient client;
    @Autowired
    WebClient nbuCurrClient;

    public NBUParser(CurrenciesClient client) {
        this.client = client;
    }

    public JSONObject findData(String codeL) {
        Exception exception = null;
        try {
            JSONArray arr = client.getNBUrates(codeL);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                if (obj.getString("CurrencyCodeL").equalsIgnoreCase(codeL)) {
                    return obj;
                }
            }
            return new JSONObject().put("error", "not found");
        } catch (Exception e) {
            exception = e;
        }
        return new JSONObject().put("error", exception);
    }
}
