package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;
import com.example.demo.mocks.MockCurrencyDataProcessing;
import com.example.demo.mocks.MockServlet;

public class ClassLoaderForMocks extends ClassLoader {

    private static Map<String, Class<? extends MockServlet>> classes = new HashMap<>() {
        {
            put("service/test/currencies", MockCurrencyDataProcessing.class);
        }
    };

    public ClassLoaderForMocks() {
        super();
    }

    public ClassLoaderForMocks(ClassLoader parent) {
        super(parent);
    }
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (classes.containsKey(name)) {
            return classes.get(name);
        } else {
            throw new ClassNotFoundException("not found" + name);
        }
    }
}
