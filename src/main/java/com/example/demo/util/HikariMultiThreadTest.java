package com.example.demo.util;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariPoolMXBean;


import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HikariMultiThreadTest {

    public static void main(String[] args) throws InterruptedException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC");
        config.setUsername("testuser");
        config.setPassword("your_password");
        config.setMaximumPoolSize(3);
        config.setMinimumIdle(1);
        config.setIdleTimeout(10000);
        config.setMaxLifetime(30000);
        config.setConnectionTimeout(5000);

        HikariDataSource ds = new HikariDataSource(config);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int id = i + 1;
            executor.submit(() -> {
                try {
                    System.out.println("Thread " + id + " requesting connection...");
                    Connection conn = ds.getConnection(); // may block if pool full
                    System.out.println("Thread " + id + " got connection");
                    Thread.sleep(8000); // hold connection to simulate long task
                    conn.close(); // return connection to pool
                    System.out.println("Thread " + id + " released connection");
                } catch (Exception e) {
                    System.out.println("Thread " + id + " failed: " + e.getMessage());
                }
            });
        }

        // Monitor pool stats in parallel
        new Thread(() -> {
            try {
                HikariPoolMXBean stats = ds.getHikariPoolMXBean();
                for (int i = 0; i < 100; i++) {
                    System.out.println("Active: " + stats.getActiveConnections() +
                            ", Idle: " + stats.getIdleConnections() +
                            ", Total: " + stats.getTotalConnections() +
                            ", Waiting: " + stats.getThreadsAwaitingConnection());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ignored) {}
        }).start();

        executor.shutdown();
    }
}
