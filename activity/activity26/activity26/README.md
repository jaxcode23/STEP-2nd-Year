# Activity 26: File Repository (CSV + Atomic Writes + Backup)

## Overview
This activity completes the persistence story by building file-based repositories (`FileAccountRepository`, `FileTransactionRepository`) backed by CSV formatting, crash-safe atomic write operations (`AtomicFileWriter`), and automated timestamped backups (`BackupManager`). It enables edge banking terminals and offline nodes to persist accounts and transactions safely without database servers.

---

## Files Included
| File | Description |
|---|---|
| `src/com/gdb/file/CsvMapper.java` | Serializes and deserializes `Account` and `Transaction` models to/from CSV rows |
| `src/com/gdb/file/AtomicFileWriter.java` | Writes to temporary files and atomically renames to target path for crash resilience |
| `src/com/gdb/file/BackupManager.java` | Creates timestamped backup copies (`.bak.<timestamp>`) and prunes old backups |
| `src/com/gdb/repository/FileAccountRepository.java` | File-backed repository persisting account records to CSV |
| `src/com/gdb/repository/FileTransactionRepository.java` | File-backed repository persisting transaction logs to CSV |
| `src/com/gdb/repository/RepositoryFactory.java` | Factory updated to support `persistence.mode=file` |
| `src/com/gdb/tests/TestFileRepository.java` | Comprehensive test driver validating CSV persistence, atomic writes, and backups |
| `docs/ACTIVITY_26.md` | Complete student lab guide with diagrams, instructions, and compilation commands |

---

## How to Compile & Run

### Windows (PowerShell)
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
Copy-Item src\main\resources\* bin -Recurse -Force
javac -encoding UTF-8 -cp "lib/*;." -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -ea -cp "lib/*;bin;src/main/resources" com.gdb.tests.TestFileRepository
```

### Linux & macOS (Terminal / Bash)
```bash
mkdir -p bin && cp -r src/main/resources/* bin/
javac -encoding UTF-8 -cp "lib/*:." -d bin $(find src -name "*.java")
java -ea -cp "lib/*:bin:src/main/resources" com.gdb.tests.TestFileRepository
```

---

## Expected Output (`TestFileRepository`)
```text
============================================================
  ACTIVITY 26 — FILE REPOSITORY (CSV + ATOMIC WRITES + BACKUP)
============================================================
[TEST 1] Account Save & Read Back from CSV:
 Loading account rules from properties files...
--------------------------------------------------
 Loaded rules for: SAVINGS (4 tenure buckets)
 Loaded rules for: CURRENT (3 tenure buckets)
 Loaded rules for: FIXEDDEPOSIT (4 tenure buckets)
 Loaded rules for: SALARY (4 tenure buckets)
--------------------------------------------------
 All rules loaded successfully!
   Loaded at: 2026-10-31T08:48:22.103317
   Account types: [SALARY, SAVINGS, FIXEDDEPOSIT, CURRENT]
  Saved: Account #1001 | Rajesh Sharma (30 yrs, Tenure: 0 yrs) | Savings | Rs. 50000.0 | Active
  Found: Account #1001 | Rajesh Sharma (25 yrs, Tenure: 0 yrs) | Savings | Rs. 50000.0 | Active
  -> PASSED

[TEST 2] Multiple Accounts & Atomic Update:
  Updated balance: Rs. 60000.0 (Expected: 60000.0)
  Total accounts in CSV: 2 (Expected: 2)
  -> PASSED

[TEST 3] Transaction Save & Query in CSV:
  Found transactions for #1001: 2 (Expected: 2)
  Found transactions for #1002: 1 (Expected: 1)
  -> PASSED

[TEST 4] Atomic Transfer via FileAccountRepository:
  Sender balance: Rs. 55000.0 (Expected: 55000.0)
  Receiver balance: Rs. 45000.0 (Expected: 45000.0)
  -> PASSED

[TEST 5] Verify Timestamped Backups Created:
  Found 2 backup files for accounts.csv.
  -> PASSED

============================================================
  ALL ACTIVITY 26 FILE REPOSITORY TESTS PASSED!
============================================================
```

---

## Verification Checklist
- [x] `AtomicFileWriter` creates valid files without partial write corruption.
- [x] `CsvMapper` accurately serializes and reconstructs `IAccount` and `Transaction` domain entities.
- [x] `BackupManager` creates timestamped backup files and limits retention to `maxBackups`.
- [x] `FileAccountRepository` performs CRUD operations and updates `accounts.csv` on disk.
- [x] `FileTransactionRepository` records transactions and updates sender/receiver balances during transfers.
- [x] Running `TestFileRepository` completes with all assertions passing.
