package com.example.demo.util;

import com.example.demo.config.DataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PoolIdleTest {

    public static void main(String[] args) throws SQLException, InterruptedException {
        HikariDataSource ds = (HikariDataSource) new DataSourceConfig().mysqlDataSource();

        System.out.println("=== Phase 1: Creating new connections (cold start) ===");
        List<Connection> connections = new ArrayList<>();

        // measure time for creating connections
        long startCreate = System.nanoTime();
        for (int i = 0; i < 3; i++) { // assuming max pool = 3
            Connection conn = ds.getConnection();
            connections.add(conn);
            System.out.printf("Got connection #%d%n", i + 1);
            printPoolStats(ds);
        }
        long endCreate = System.nanoTime();
        System.out.printf("⏱ Time to create new connections: %.3f ms%n",
                (endCreate - startCreate) / 1_000_000.0);

        System.out.println("\n=== Phase 2: Reusing idle connections (warm start) ===");
        // Close connections so they go idle
        for (Connection c : connections) c.close();
        printPoolStats(ds);

        Thread.sleep(1000); // wait a bit to ensure they’re marked idle

        long startReuse = System.nanoTime();
        for (int i = 0; i < 3; i++) {
            Connection conn = ds.getConnection();
            conn.close(); // immediately return to pool
        }
        long endReuse = System.nanoTime();

        System.out.printf("⏱ Time to get/reuse connections: %.3f ms%n",
                (endReuse - startReuse) / 1_000_000.0);

        System.out.println("\n=== Final Pool State ===");
        printPoolStats(ds);

        ds.close();
    }

    private static void printPoolStats(HikariDataSource ds) {
        System.out.printf("Active: %d | Idle: %d | Total: %d | Waiting: %d%n",
                ds.getHikariPoolMXBean().getActiveConnections(),
                ds.getHikariPoolMXBean().getIdleConnections(),
                ds.getHikariPoolMXBean().getTotalConnections(),
                ds.getHikariPoolMXBean().getThreadsAwaitingConnection());
    }
}
