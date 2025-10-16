package com.example.demo.mocks;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MockCurrencyDataProcessingTest {

    @Test
    void doGetTest() {
        MockCurrencyDataProcessing instance = new MockCurrencyDataProcessing();
        Map<String, String> test = new HashMap<>();
        test.put("CODE", "CODE123");
        byte[] bytes = instance.doGet(test);
        String res = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(res);
    }
}