package com.example.demo.mocks;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
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
    private static final List<String> postgresValues = Arrays.asList("USD", "EUR");
    private static DataSource ds;
    private static DataSource dsMysql;

    public MockCurrencyDataProcessing() {
        try {
            ApplicationContext ac = new AnnotationConfigApplicationContext(DataSourceConfig.class);
            ds = (DataSource) ac.getBean("psgrs_ds_14102025");
            dsMysql = (DataSource) ac.getBean("mysql_ds_20102025");
        } catch (Exception ex) {
            logger.log(Level.WARNING, "mock_context_fail");
            throw new RuntimeException(ex);
        }

    }

    @Override
    public byte[] doGet(Map<String, String> map) {

        byte[] data = new byte[0];
        try {
        // MDL AZN - Moldova Azerbaijan mysql / USD EUR - postgres
            String q = "SELECT body FROM finance.financedata where param = ?";
            String someParam = map.get("CODE");
            boolean useMysql = false;
            if (!postgresValues.contains(someParam)) {
                q = "SELECT body FROM testdb.financedata WHERE param = ?";
                useMysql = true;
            }
            try {
                JdbcTemplate jt = useMysql ? new JdbcTemplate(dsMysql) : new JdbcTemplate(ds);
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
