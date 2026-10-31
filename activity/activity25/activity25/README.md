# Activity 25: Connection Pooling with HikariCP

## Overview
This activity replaces direct `DriverManager` connection calls with **HikariCP Connection Pooling**, enhancing application scalability, connection reusability, and latency characteristics under high concurrency. It integrates `HikariDataSource` into the `ConnectionProvider` architecture, configures pool limits via `persistence.properties`, and benchmarks connection acquisition speedup.

---

## Files Included
| File | Description |
|---|---|
| `lib/HikariCP-5.0.1.jar` | High-performance JDBC connection pool library |
| `src/com/gdb/db/HikariConnectionProvider.java` | Connection pool implementation backed by `HikariDataSource` |
| `src/main/resources/config/persistence.properties` | Configured with `persistence.pool.enabled=true`, pool size, and timeouts |
| `src/com/gdb/repository/RepositoryFactory.java` | Factory dynamically selecting `HikariConnectionProvider` when pool is enabled |
| `src/com/gdb/tests/TestConnectionPooling.java` | Test suite verifying pool lifecycle, concurrent usage, and performance benchmarks |
| `docs/ACTIVITY_25.md` | Student lab guide with architecture diagrams, instructions, and benchmarks |

---

## How to Compile & Run

### Windows (PowerShell)
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
Copy-Item src\main\resources\* bin -Recurse -Force
javac -encoding UTF-8 -cp "lib/*;." -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -ea -cp "lib/*;bin;src/main/resources" com.gdb.tests.TestConnectionPooling
```

### Linux & macOS (Terminal / Bash)
```bash
mkdir -p bin && cp -r src/main/resources/* bin/
javac -encoding UTF-8 -cp "lib/*:." -d bin $(find src -name "*.java")
java -ea -cp "lib/*:bin:src/main/resources" com.gdb.tests.TestConnectionPooling
```

---

## Expected Output (`TestConnectionPooling`)
```text
============================================================
  ACTIVITY 25 — JDBC CONNECTION POOLING (HIKARICP)
============================================================
[TEST 1] HikariCP Connection Pool Initialization:
  Pool Name: HikariPool-1
  Max Pool Size: 10
  Connection acquired: true
  -> PASSED

[TEST 2] Latency Benchmark (100 sequential connection cycles):
  Unpooled Direct Connections: 106 ms (avg 1.06 ms/op)
  HikariCP Pooled Connections:  5 ms (avg 0.05 ms/op)
  Speedup Factor: ~21.2x faster!
  -> PASSED

[TEST 3] Repository Operations over HikariCP:
 Loading account rules from properties files...
--------------------------------------------------
 Loaded rules for: SAVINGS (4 tenure buckets)
 Loaded rules for: CURRENT (3 tenure buckets)
 Loaded rules for: FIXEDDEPOSIT (4 tenure buckets)
 Loaded rules for: SALARY (4 tenure buckets)
--------------------------------------------------
 All rules loaded successfully!
   Loaded at: 2026-10-31T08:44:08.898264400
   Account types: [SALARY, SAVINGS, FIXEDDEPOSIT, CURRENT]
  Saved Account #1001 via pooled repository.
  Retrieved Account #1001: Balance Rs. 50000.0
  -> PASSED

============================================================
  ALL ACTIVITY 25 CONNECTION POOLING TESTS PASSED!
============================================================
```

---

## Verification Checklist
- [x] `HikariConnectionProvider` initializes a valid `HikariDataSource` with configured pool limits.
- [x] `getConnection()` successfully delivers working database connections.
- [x] Closing a borrowed `Connection` returns it to the pool rather than terminating the physical TCP socket.
- [x] Benchmark confirms pooled connection reuse is measurably faster than unpooled connection creation.
- [x] Running `TestConnectionPooling` passes all functional and latency assertions.
