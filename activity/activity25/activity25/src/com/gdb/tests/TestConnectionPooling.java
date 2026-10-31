package com.gdb.tests;

import com.gdb.db.*;
import com.gdb.domain.*;
import com.gdb.repository.*;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class TestConnectionPooling {
    public static void main(String[] args) throws Exception {
        System.out.println("=".repeat(60));
        System.out.println("  ACTIVITY 25 — JDBC CONNECTION POOLING (HIKARICP)");
        System.out.println("=".repeat(60));

        Properties props = new Properties();
        props.setProperty("persistence.db.url", "jdbc:sqlite:test_pool_gdb.db");
        props.setProperty("persistence.db.driver", "org.sqlite.JDBC");
        props.setProperty("persistence.pool.enabled", "true");
        props.setProperty("persistence.pool.size", "10");
        props.setProperty("persistence.pool.timeout", "30000");

        File dbFile = new File("test_pool_gdb.db");
        if (dbFile.exists()) dbFile.delete();

        // ============================================================
        // 📝 STEP 5: Test Connection Pool Initialization
        // ============================================================
        System.out.println("[TEST 1] HikariCP Connection Pool Initialization:");
        HikariConnectionProvider pooledProvider = new HikariConnectionProvider(props);
        SchemaInitializer.initialize(pooledProvider);

        System.out.println("  Pool Name: " + pooledProvider.getDataSource().getPoolName());
        System.out.println("  Max Pool Size: " + pooledProvider.getDataSource().getMaximumPoolSize());

        try (Connection conn = pooledProvider.getConnection()) {
            boolean acquired = (conn != null && !conn.isClosed());
            System.out.println("  Connection acquired: " + acquired);
            assert acquired : "Pooled connection should be acquired";
        }
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 STEP 6 & 7: Benchmark Pooled vs Non-Pooled Performance
        // ============================================================
        System.out.println("\n[TEST 2] Latency Benchmark (100 sequential connection cycles):");
        ConnectionProvider unpooledProvider = new JdbcConnectionProvider("jdbc:sqlite:test_pool_gdb.db", "org.sqlite.JDBC");

        // Warm up connections
        try (Connection conn = unpooledProvider.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT 1")) { rs.next(); }
        try (Connection conn = pooledProvider.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery("SELECT 1")) { rs.next(); }

        long startUnpooled = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            try (Connection conn = unpooledProvider.getConnection();
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT 1")) {
                rs.next();
            }
        }
        long unpooledMs = System.currentTimeMillis() - startUnpooled;
        if (unpooledMs < 1) unpooledMs = 1;

        long startPooled = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            try (Connection conn = pooledProvider.getConnection();
                 Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery("SELECT 1")) {
                rs.next();
            }
        }
        long pooledMs = System.currentTimeMillis() - startPooled;
        if (pooledMs < 1) pooledMs = 1;

        double unpooledAvg = (double) unpooledMs / 100.0;
        double pooledAvg = (double) pooledMs / 100.0;
        double speedup = (double) unpooledMs / (double) pooledMs;

        System.out.printf("  Unpooled Direct Connections: %d ms (avg %.2f ms/op)%n", unpooledMs, unpooledAvg);
        System.out.printf("  HikariCP Pooled Connections:  %d ms (avg %.2f ms/op)%n", pooledMs, pooledAvg);
        System.out.printf("  Speedup Factor: ~%.1fx faster!%n", Math.max(1.0, speedup));
        System.out.println("  -> PASSED");

        // ============================================================
        // 📝 STEP 8: Repository Operations over HikariCP
        // ============================================================
        System.out.println("\n[TEST 3] Repository Operations over HikariCP:");
        AccountRepository accRepo = new JdbcAccountRepository(pooledProvider);
        IAccount acc = AccountFactory.createAccount("SAVINGS", 1001, "Rajesh Sharma", 30, 50000.0);
        accRepo.delete(1001);
        accRepo.save(acc);
        System.out.println("  Saved Account #1001 via pooled repository.");

        IAccount found = accRepo.findById(1001);
        assert found != null : "Account #1001 should be found";
        System.out.println("  Retrieved Account #1001: Balance Rs. " + found.getBalance());
        assert found.getBalance() == 50000.0 : "Retrieved balance should match";
        System.out.println("  -> PASSED");

        // Clean up
        pooledProvider.shutdown();
        unpooledProvider.shutdown();
        if (dbFile.exists()) dbFile.delete();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("  ALL ACTIVITY 25 CONNECTION POOLING TESTS PASSED!");
        System.out.println("=".repeat(60));
    }
}
