package com.example.demo.controllers;


import com.example.demo.beans.CurrencyDataProcessing;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/nbu")
public class UNBController {
    private static final String NBU_URL = "https://bank.gov.ua/NBU_Exchange/exchange?json";
    private static final String MOCK_URL_CURR = "mock://service/test/currencies";
    private final CurrencyDataProcessing processor;

    public UNBController(CurrencyDataProcessing processor) {
        this.processor = processor;
    }

    @GetMapping("/rates")
    public String getConversions(
            @RequestParam(defaultValue = "USD") String code
    ) {
        return processor.getCurInfoFromUNB(code, NBU_URL).toString();
    }

    @GetMapping("/rates2")
    public String getConversions2(
            @RequestParam(defaultValue = "USD") String code
    ) {
        return processor.getCurInfoFromUNB(code, MOCK_URL_CURR).toString();
    }
}
