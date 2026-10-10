# Activity 22: JDBC Foundation (Connection, Schema, Provider)

## Overview
This activity establishes the relational database foundation for GDB by integrating **SQLite JDBC**. In this activity, database connection acquisition is abstracted using `ConnectionProvider` and `JdbcConnectionProvider`, relational tables are defined in `schema.sql`, an idempotent `SchemaInitializer` is created, and `RepositoryFactory` is updated to support JDBC mode.

---

## Files Included
| File | Description |
|---|---|
| `lib/sqlite-jdbc-3.44.1.0.jar` (+ SLF4J jars) | SQLite JDBC Driver and logging runtime libraries |
| `src/com/gdb/db/ConnectionProvider.java` | Interface abstracting connection acquisition (`getConnection()`, `shutdown()`, `getProviderName()`) |
| `src/com/gdb/db/JdbcConnectionProvider.java` | Concrete connection provider using `DriverManager.getConnection(url)` |
| `src/main/resources/schema.sql` | DDL script with idempotent definitions for `accounts` and `transactions` tables |
| `src/com/gdb/db/SchemaInitializer.java` | Helper loading and executing `schema.sql` statements idempotently |
| `src/main/resources/config/persistence.properties` | Configured with `persistence.mode=jdbc`, `persistence.db.url=jdbc:sqlite:gdb.db`, and `persistence.db.driver=org.sqlite.JDBC` |
| `src/com/gdb/repository/RepositoryFactory.java` | Updated JDBC factory branch initializing `JdbcConnectionProvider` and invoking `SchemaInitializer` |
| `src/com/gdb/tests/TestJdbcConnection.java` | Automated test suite verifying connection lifecycle, `SELECT 1`, schema deployment, and table existence |
| `docs/ACTIVITY_22.md` | Comprehensive lab documentation with student instructions and architecture diagrams |

---

## How to Compile & Run

### Windows (PowerShell)
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
Copy-Item src\main\resources\* bin -Recurse -Force
javac -encoding UTF-8 -cp "lib/*;." -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -ea -cp "lib/*;bin;src/main/resources" com.gdb.tests.TestJdbcConnection
```

### Linux & macOS (Terminal / Bash)
```bash
mkdir -p bin && cp -r src/main/resources/* bin/
javac -encoding UTF-8 -cp "lib/*:." -d bin $(find src -name "*.java")
java -ea -cp "lib/*:bin:src/main/resources" com.gdb.tests.TestJdbcConnection
```

---

## Expected Output (`TestJdbcConnection`)
```text
============================================================
  ACTIVITY 22 — JDBC CONNECTION & SCHEMA INITIALIZATION
============================================================
[TEST 1] Database Connection Establishment:
  Connected to: jdbc:sqlite:test_gdb.db
  Driver Name: SQLite JDBC
  -> PASSED

[TEST 2] Schema DDL Execution:
  Creating tables: accounts, transactions...
  Tables initialized successfully.
  -> PASSED

[TEST 3] Schema Verification:
  Table 'accounts' exists: true
  Table 'transactions' exists: true
  -> PASSED

============================================================
  ALL ACTIVITY 22 JDBC CONNECTION TESTS PASSED!
============================================================
```

---

## Verification Checklist
- [x] Project compiles with SQLite JDBC driver and SLF4J libraries on classpath.
- [x] `ConnectionProvider` defines connection lifecycle and provider naming contract.
- [x] `JdbcConnectionProvider` loads driver dynamically and returns open SQLite connection.
- [x] `SchemaInitializer` parses and executes `schema.sql` DDL statements idempotently.
- [x] Relational tables `accounts` and `transactions` are verified through `DatabaseMetaData`.
- [x] `RepositoryFactory.getConnectionProvider()` caches and initializes provider in JDBC mode.
- [x] `TestJdbcConnection` executes with 100% assertions satisfied.
