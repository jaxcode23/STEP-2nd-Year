package com.gdb.db;

import java.io.*;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Initializes the database schema idempotently by executing schema.sql DDL statements.
 */
public class SchemaInitializer {

    // ============================================================
    // 📝 STEP 5: Implement initialize(ConnectionProvider provider)
    //
    // INSTRUCTIONS:
    //   1. Obtain a Connection from provider.
    //   2. Read the contents of schema.sql from resource stream or file.
    //   3. Split SQL script into individual statements by semicolon ';'.
    //   4. Execute each non-empty statement using Statement.execute(sql).
    // ============================================================
    public static void initialize(ConnectionProvider provider) {
        String sql = readSchemaSql();
        if (sql == null || sql.trim().isEmpty()) {
            throw new IllegalStateException("schema.sql is empty or could not be loaded");
        }
        try (Connection conn = provider.getConnection();
             Statement stmt = conn.createStatement()) {
            String[] statements = sql.split(";");
            for (String s : statements) {
                String trimmed = s.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database schema", e);
        }
    }

    // ============================================================
    // 📝 STEP 6: Helper Method to Read SQL Script
    // ============================================================
    public static String readSchemaSql() {
        String[] paths = {
            "schema.sql",
            "src/main/resources/schema.sql",
            "bin/schema.sql"
        };
        for (String p : paths) {
            File f = new File(p);
            if (f.exists()) {
                try (InputStream in = new FileInputStream(f)) {
                    return readFromStream(in);
                } catch (IOException ignored) {}
            }
        }
        // Try ClassLoader resource stream
        try (InputStream is = SchemaInitializer.class.getClassLoader().getResourceAsStream("schema.sql")) {
            if (is != null) {
                return readFromStream(is);
            }
        } catch (IOException ignored) {}

        // Fallback embedded schema DDL if file not found
        return "CREATE TABLE IF NOT EXISTS accounts (\n" +
               "    account_number INTEGER PRIMARY KEY,\n" +
               "    account_holder_name TEXT NOT NULL,\n" +
               "    age INTEGER NOT NULL CHECK (age >= 18),\n" +
               "    balance REAL NOT NULL,\n" +
               "    account_type TEXT NOT NULL,\n" +
               "    status TEXT NOT NULL,\n" +
               "    pin INTEGER,\n" +
               "    opening_date TEXT NOT NULL,\n" +
               "    overdraft_used REAL DEFAULT 0,\n" +
               "    interest_earned REAL DEFAULT 0\n" +
               ");\n" +
               "\n" +
               "CREATE TABLE IF NOT EXISTS transactions (\n" +
               "    transaction_id TEXT PRIMARY KEY,\n" +
               "    timestamp TEXT NOT NULL,\n" +
               "    account_number INTEGER NOT NULL,\n" +
               "    type TEXT NOT NULL,\n" +
               "    amount REAL NOT NULL,\n" +
               "    balance_after REAL NOT NULL,\n" +
               "    status TEXT NOT NULL,\n" +
               "    from_account INTEGER,\n" +
               "    to_account INTEGER,\n" +
               "    description TEXT,\n" +
               "    FOREIGN KEY (account_number) REFERENCES accounts(account_number)\n" +
               ");";
    }

    private static String readFromStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }
}
