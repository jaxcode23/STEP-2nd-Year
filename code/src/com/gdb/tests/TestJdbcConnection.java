package com.gdb.tests;

import com.gdb.db.ConnectionProvider;
import com.gdb.db.JdbcConnectionProvider;
import com.gdb.db.SchemaInitializer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJdbcConnection {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 22 — JDBC CONNECTION & SCHEMA INITIALIZATION");
        System.out.println("=".repeat(60));

        String testDbUrl = "jdbc:sqlite:test_gdb.db";
        ConnectionProvider provider = new JdbcConnectionProvider(testDbUrl);

        // ============================================================
        // 📝 STEP 8: Test Direct JDBC Connection & Simple Query
        // ============================================================
        System.out.println("[TEST 1] Database Connection Establishment:");
        try (Connection conn = provider.getConnection()) {
            assert conn != null : "Connection should not be null";
            assert !conn.isClosed() : "Connection should be open";
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("  Connected to: " + testDbUrl);
            System.out.println("  Driver Name: " + meta.getDriverName());
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT 1")) {
                assert rs.next() : "SELECT 1 should return a row";
            }
        }
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 STEP 9: Initialize Schema & Verify Tables
        // ============================================================
        System.out.println("\n[TEST 2] Schema DDL Execution:");
        System.out.println("  Creating tables: accounts, transactions...");
        SchemaInitializer.initialize(provider);
        System.out.println("  Tables initialized successfully.");
        System.out.println("  -> PASSED");

        System.out.println("\n[TEST 3] Schema Verification:");
        boolean accountsExists = false;
        boolean transactionsExists = false;
        try (Connection conn = provider.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, "accounts", null)) {
                if (rs.next()) accountsExists = true;
            }
            try (ResultSet rs = meta.getTables(null, null, "transactions", null)) {
                if (rs.next()) transactionsExists = true;
            }
        }
        System.out.println("  Table 'accounts' exists: " + accountsExists);
        System.out.println("  Table 'transactions' exists: " + transactionsExists);
        assert accountsExists : "Table accounts must exist";
        assert transactionsExists : "Table transactions must exist";
        System.out.println("  -> PASSED");

        provider.shutdown();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ALL ACTIVITY 22 JDBC CONNECTION TESTS PASSED!");
        System.out.println("=".repeat(60));
    }
}
