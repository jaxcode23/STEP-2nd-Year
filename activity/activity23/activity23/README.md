# Activity 23: JdbcAccountRepository (CRUD Operations)

## Overview
This activity delivers complete relational persistence for accounts using **JDBC PreparedStatements** and **ResultSet mapping**. In this activity, `JdbcAccountRepository` implements the `AccountRepository` CRUD contract against the SQLite database initialized in Activity 22, including parameterized queries to prevent SQL injection, safe resource management with try-with-resources, and translation of `SQLException` into `DataAccessException`.

---

## Files Included
| File | Description |
|---|---|
| `src/com/gdb/exceptions/DataAccessException.java` | Unchecked runtime exception wrapping SQL errors |
| `src/com/gdb/repository/AccountRepository.java` | Pure CRUD interface for account data access and persistence |
| `src/com/gdb/repository/JdbcAccountRepository.java` | Relational repository implementation backed by SQLite JDBC |
| `src/com/gdb/repository/RepositoryFactory.java` | Factory supplying `JdbcAccountRepository` when configured in JDBC mode |
| `src/com/gdb/tests/TestJdbcAccountRepository.java` | Automated test suite validating CRUD operations, mapping, and lifecycle |
| `docs/ACTIVITY_23.md` | Lab documentation with student instructions, acceptance criteria, and expected output |

---

## How to Compile & Run

### Windows (PowerShell)
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
Copy-Item src\main\resources\* bin -Recurse -Force
javac -encoding UTF-8 -cp "lib/*;." -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -ea -cp "lib/*;bin;src/main/resources" com.gdb.tests.TestJdbcAccountRepository
```

### Linux & macOS (Terminal / Bash)
```bash
mkdir -p bin && cp -r src/main/resources/* bin/
javac -encoding UTF-8 -cp "lib/*:." -d bin $(find src -name "*.java")
java -ea -cp "lib/*:bin:src/main/resources" com.gdb.tests.TestJdbcAccountRepository
```

---

## Expected Output (`TestJdbcAccountRepository`)
```text
============================================================
  ACTIVITY 23 — JDBC ACCOUNT REPOSITORY CRUD TESTS
============================================================
[TEST 1] Save & FindById:
 Loading account rules from properties files...
--------------------------------------------------
 Loaded rules for: SAVINGS (4 tenure buckets)
 Loaded rules for: CURRENT (3 tenure buckets)
 Loaded rules for: FIXEDDEPOSIT (4 tenure buckets)
 Loaded rules for: SALARY (4 tenure buckets)
--------------------------------------------------
 All rules loaded successfully!
   Loaded at: 2026-10-31T08:38:48.951345800
   Account types: [SALARY, SAVINGS, FIXEDDEPOSIT, CURRENT]
  Found: Account #1001 | Rajesh Sharma (25 yrs, Tenure: 0 yrs) | Savings | Rs. 50000.0 | Active
  -> PASSED

[TEST 2] Update & FindAll:
  Updated balance: Rs. 65000.0 (Expected: 65000.0)
  Total accounts in DB: 2 (Expected: 2)
  -> PASSED

[TEST 3] Delete & Exists:
  Account 1002 successfully deleted.
  -> PASSED

============================================================
  ALL ACTIVITY 23 JDBC ACCOUNT REPOSITORY TESTS PASSED!
============================================================
```

---

## Verification Checklist
- [x] `save()` inserts a new account row into `accounts` table without errors.
- [x] `findById()` retrieves the accurate account entity by its account number.
- [x] `update()` modifies the stored balance in SQLite database correctly.
- [x] `delete()` removes the specified account record.
- [x] `exists()` returns `true` for existing accounts and `false` for nonexistent accounts.
- [x] Running `TestJdbcAccountRepository` outputs all tests passed with zero assertion failures.
