package com.example.demo.util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.demo.config.DataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariPoolTest {

    public static void main(String[] args) throws SQLException, InterruptedException {
        // Assume mysqlDataSource() returns your Hikari DataSource
        HikariDataSource ds = (HikariDataSource) new DataSourceConfig().mysqlDataSource();

        List<Connection> connections = new ArrayList<>();

        // Open 5 connections (more than max pool of 3)
        for (int i = 0; i < 5; i++) {
            try {
                Connection conn = ds.getConnection();
                connections.add(conn);
                System.out.println("Got connection #" + (i+1));
                printPoolStats(ds);
            } catch (Exception e) {
                System.out.println("Failed to get connection #" + (i+1) + ": " + e.getMessage());
            }
        }

        // Wait for idleTimeout + some buffer
        System.out.println("Before closing...");
        //Thread.sleep(5000);

        // Close all connections
        for (Connection c : connections) {
            c.close();
            System.out.println("Threads waiting: " + ds.getHikariPoolMXBean().getThreadsAwaitingConnection());
        }

        System.out.println("After closing connections:");
        printPoolStats(ds);

        ds.close();
    }

    public static void printPoolStats(HikariDataSource ds) {
        System.out.println("Active connections: " + ds.getHikariPoolMXBean().getActiveConnections());
        System.out.println("Idle connections: " + ds.getHikariPoolMXBean().getIdleConnections());
        System.out.println("Total connections: " + ds.getHikariPoolMXBean().getTotalConnections());
        System.out.println("Threads waiting: " + ds.getHikariPoolMXBean().getThreadsAwaitingConnection());
    }
}
