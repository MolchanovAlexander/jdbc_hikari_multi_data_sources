package com.example.demo.mocks;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.demo.config.DataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author salat
 */
public class MockCurrencyDataProcessing implements MockServlet{

    private static final Logger logger = Logger.getLogger(MockCurrencyDataProcessing.class.getSimpleName());
    private static DataSource ds;

    public MockCurrencyDataProcessing() {
        try {
            ApplicationContext ac = new AnnotationConfigApplicationContext(DataSourceConfig.class);
            ds = (DataSource) ac.getBean("psgrs_ds_14102025");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public byte[] doGet(Map<String, String> map) {

        byte[] data = new byte[0];
        try {

            String someParam = map.get("CODE");

            String q = "SELECT body FROM finance.financedata where param = ?";
            try {
                JdbcTemplate jt = new JdbcTemplate(ds);
                data = jt.queryForObject(q, byte[].class, someParam);
            }catch (EmptyResultDataAccessException ignored) {
                logger.log(Level.WARNING, "not found");
            } finally {
                if (ds instanceof HikariDataSource) {
                    ((HikariDataSource) ds).close();
                }
            }

            if (data == null || data.length == 0) {
                JSONObject object = new JSONObject();
                object.put("message", "NONE");
                data = object.toString().getBytes();
            }

        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "err mess", ex);
            throw new RuntimeException(ex);
        }

        return data;
    }
}
