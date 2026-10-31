# Activity 24: JdbcTransactionRepository & Data Migration

## Overview
This activity introduces relational persistence for financial transactions using `JdbcTransactionRepository` and provides a `DataMigrator` utility to transition transaction logs from memory/file storage into SQLite. It implements manual JDBC transaction control (`conn.setAutoCommit(false)`, `conn.commit()`, and `conn.rollback()`) to guarantee atomic multi-account transfers with ACID integrity.

---

## Files Included
| File | Description |
|---|---|
| `src/com/gdb/repository/JdbcTransactionRepository.java` | Relational transaction repository with atomic transfers and rollback |
| `src/com/gdb/db/DataMigrator.java` | Data migration utility transferring legacy logs into `TransactionRepository` |
| `src/com/gdb/repository/RepositoryFactory.java` | Factory supplying `JdbcTransactionRepository` when in JDBC mode |
| `src/com/gdb/tests/TestJdbcTransactionRepository.java` | Test suite validating transaction queries, atomic transfers, rollback, and migration |
| `docs/ACTIVITY_24.md` | Comprehensive lab documentation with student instructions and diagrams |

---

## How to Compile & Run

### Windows (PowerShell)
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
Copy-Item src\main\resources\* bin -Recurse -Force
javac -encoding UTF-8 -cp "lib/*;." -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -ea -cp "lib/*;bin;src/main/resources" com.gdb.tests.TestJdbcTransactionRepository
```

### Linux & macOS (Terminal / Bash)
```bash
mkdir -p bin && cp -r src/main/resources/* bin/
javac -encoding UTF-8 -cp "lib/*:." -d bin $(find src -name "*.java")
java -ea -cp "lib/*:bin:src/main/resources" com.gdb.tests.TestJdbcTransactionRepository
```

---

## Expected Output (`TestJdbcTransactionRepository`)
```text
============================================================
  ACTIVITY 24 — JDBC TRANSACTION REPOSITORY & MIGRATION
============================================================
[TEST 1] Transaction Save & Query:
  Saved 2 transaction records.
  Found transactions for Account #1001: 2
  -> PASSED

[TEST 2] Atomic Transfer Execution:
 Loading account rules from properties files...
--------------------------------------------------
 Loaded rules for: SAVINGS (4 tenure buckets)
 Loaded rules for: CURRENT (3 tenure buckets)
 Loaded rules for: FIXEDDEPOSIT (4 tenure buckets)
 Loaded rules for: SALARY (4 tenure buckets)
--------------------------------------------------
 All rules loaded successfully!
   Loaded at: 2026-10-31T08:42:04.512313300
   Account types: [SALARY, SAVINGS, FIXEDDEPOSIT, CURRENT]
  Initial Sender Balance: Rs. 50000.0, Receiver Balance: Rs. 30000.0
  Executing atomic transfer of Rs. 10000.0...
  Updated Sender Balance: Rs. 40000.0
  Updated Receiver Balance: Rs. 40000.0
  Transfer audit transaction recorded in database.
  -> PASSED

[TEST 3] Atomic Rollback on Insufficient Balance:
  Attempting transfer of Rs. 999999.0 (exceeds balance)...
  Caught expected exception: Insufficient balance
  Sender Balance unchanged: Rs. 40000.0
  Receiver Balance unchanged: Rs. 40000.0
  -> PASSED

[TEST 4] Data Migration from Memory to JDBC:
  Migrated 3 accounts and 5 transaction records.
  Verified migrated accounts in SQLite database.
  -> PASSED

============================================================
  ALL ACTIVITY 24 JDBC TRANSACTION TESTS PASSED!
============================================================
```

---

## Verification Checklist
- [x] `save()` records transaction details into `transactions` table accurately.
- [x] `transfer()` deducts money from sender and credits receiver atomically.
- [x] In case of insufficient funds or DB failure, `transfer()` rolls back all updates cleanly without modifying balances.
- [x] `DataMigrator` migrates all in-memory accounts and transaction records to JDBC repositories.
- [x] Running `TestJdbcTransactionRepository` reports 100% test pass status.
