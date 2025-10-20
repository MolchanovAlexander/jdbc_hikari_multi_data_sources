package com.example.demo.config;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class DataSourceConfig {

    @Bean(name = "psgrs_ds_14102025")
    @Lazy
    public DataSource postgresDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/your_db");
        config.setUsername("your_user");
        config.setPassword("your_password");

        config.setMinimumIdle(1);
        config.setMaximumPoolSize(3);
        config.setIdleTimeout(3000);
        config.setConnectionInitSql("SET statement_timeout = '20000'");
        config.setPoolName("postgres_POOL");
        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);
    }

    @Bean(name = "mysql_ds_20102025")
    @Lazy
    public DataSource mysqlDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb");//?enabledTLSProtocols=TLSv1.2
        config.setUsername("testuser");
        config.setPassword("your_password");

        config.setMinimumIdle(1);
        config.setMaximumPoolSize(3);
        config.setIdleTimeout(3000);
        config.setPoolName("mysql_POOL");
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTimeout(5000);

        return new HikariDataSource(config);
    }
}
