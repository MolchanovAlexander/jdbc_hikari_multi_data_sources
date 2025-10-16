package com.example.demo.beans;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import com.example.demo.util.RESTTemplateMoksReal;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CurrencyDataProcessing {


    public JSONObject getCurInfoFromUNB(String code, String url) {

        Map<String, String> map = new HashMap<>();
        map.put("CODE", code);
        byte[] bytes = RESTTemplateMoksReal.doGet(url, map);
        JSONObject obj = new JSONObject(new String(bytes, StandardCharsets.UTF_8));
        return obj;
    }

}
