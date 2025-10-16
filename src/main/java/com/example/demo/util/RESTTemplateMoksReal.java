package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.demo.mocks.MockServlet;
import com.example.demo.service.NBUParser;
import org.springframework.stereotype.Component;

@Component
public class RESTTemplateMoksReal {
    private static ClassLoaderForMocks classLoaderForMocks = new ClassLoaderForMocks();
    private static final Logger logger = Logger.getLogger("Template");
    private static NBUParser parser;

    public RESTTemplateMoksReal(NBUParser parser) {
        this.parser = parser;
    }

    public static byte[] doGet(String uri, Map<String, String> prs) {
        try {
            MockServlet mockInstance = getMockInstance(uri);
            if (mockInstance == null) {
                String code = prs.get("CODE");
                byte[] bytes = parser.findData(code).toString().getBytes();
                logger.log(Level.INFO, String.format("Response: %s", new String(bytes, StandardCharsets.UTF_8)));
                return bytes;
            } else {
                byte[] bytes = mockInstance.doGet(prs);
                logger.log(Level.INFO, String.format("Response: %s", new String(bytes, StandardCharsets.UTF_8)));
                return bytes;
            }
        } catch (Exception e) {
            System.out.println("----------------DNNNOOOO___________________________");
        }
        return new byte[1];
    }

    private static MockServlet getMockInstance(String uri) {
        if (uri.startsWith("mock://")) {
            try {
                String mockClassName = uri.substring(7);
                MockServlet mockInstance = (MockServlet) classLoaderForMocks.loadClass(mockClassName)
                        .getDeclaredConstructor().newInstance();
                return mockInstance;
            } catch (Exception ex) {
                throw new RuntimeException("Failed to create mock instance", ex);
            }
        } else {
            return null;
        }
    }
}
