package com.gdb.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Production-grade ConnectionProvider backed by HikariCP Connection Pool.
 */
public class HikariConnectionProvider implements ConnectionProvider {

    // ============================================================
    // 📝 STEP 1: Declare DataSource Field
    // ============================================================
    private final HikariDataSource dataSource;

    // ============================================================
    // 📝 STEP 2: Implement Constructor with HikariConfig
    // ============================================================
    public HikariConnectionProvider(Properties config) {
        HikariConfig hikariConfig = new HikariConfig();
        String url = config.getProperty("persistence.db.url", "jdbc:sqlite:gdb.db");
        String driver = config.getProperty("persistence.db.driver", "org.sqlite.JDBC");
        int poolSize = Integer.parseInt(config.getProperty("persistence.pool.size", "10"));
        long timeout = Long.parseLong(config.getProperty("persistence.pool.timeout", "30000"));

        hikariConfig.setJdbcUrl(url);
        if (driver != null && !driver.trim().isEmpty()) {
            hikariConfig.setDriverClassName(driver);
        }
        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setConnectionTimeout(timeout);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setPoolName("HikariPool-1");

        this.dataSource = new HikariDataSource(hikariConfig);
    }

    public HikariConnectionProvider(String url) {
        this(createDefaultProperties(url));
    }

    private static Properties createDefaultProperties(String url) {
        Properties p = new Properties();
        p.setProperty("persistence.db.url", url);
        p.setProperty("persistence.db.driver", "org.sqlite.JDBC");
        p.setProperty("persistence.pool.size", "10");
        p.setProperty("persistence.pool.timeout", "30000");
        return p;
    }

    // ============================================================
    // 📝 STEP 3: Implement getConnection()
    // ============================================================
    @Override
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("HikariDataSource is not initialized");
        }
        return dataSource.getConnection();
    }

    // ============================================================
    // 📝 STEP 4: Implement shutdown()
    // ============================================================
    @Override
    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    @Override
    public String getProviderName() {
        return "HikariConnectionProvider [Pool size: " + (dataSource != null ? dataSource.getMaximumPoolSize() : "N/A") + "]";
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
