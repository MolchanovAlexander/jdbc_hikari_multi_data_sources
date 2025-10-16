package com.example.demo.mocks;

import java.util.Map;

public interface MockServlet {

    default byte[] doGet(Map<String, String> map) throws Exception {
        return null;
    }

    default byte[] doPost(byte[] bodyContent) {
        return null;
    }
}
