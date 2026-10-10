# Activity 21: Repository Pattern (Interfaces + InMemory + Factory)

## Overview
This activity introduces the **Repository Pattern**, decoupling domain models and the service layer from data persistence mechanisms. In this activity, repository interfaces are defined for accounts and transactions, in-memory implementations are built using Java collections, a configurable `RepositoryFactory` driven by `persistence.properties` is established, and `AccountService` is refactored to delegate data persistence cleanly to repositories.

---

## Files Included
| File | Description |
|---|---|
| `src/com/gdb/repository/AccountRepository.java` | Contract defining account persistence operations (`save`, `findById`, `findAll`, `update`, `delete`, `exists`, `nextAccountNumber`) |
| `src/com/gdb/repository/TransactionRepository.java` | Contract defining transaction persistence operations (`save`, `findByAccount`, `findAll`, `clear`) |
| `src/com/gdb/repository/InMemoryAccountRepository.java` | In-memory `Map<Integer, IAccount>` implementation with auto-increment ID generation |
| `src/com/gdb/repository/InMemoryTransactionRepository.java` | In-memory `List<Transaction>` implementation supporting filtering by account ID |
| `src/com/gdb/repository/RepositoryFactory.java` | Factory reading `persistence.properties` (`memory`, with future placeholders for `jdbc` and `file`) |
| `src/main/resources/config/persistence.properties` | Configuration declaring `persistence.mode=memory` |
| `src/com/gdb/service/AccountService.java` | Refactored service layer operating exclusively through repository interfaces |
| `src/com/gdb/tests/TestRepositoryInMemory.java` | Test driver validating repository CRUD, transactions, auto ID generation, and service integration |
| `docs/ACTIVITY_21.md` | Full activity lab guide with instructions, architecture diagrams, and exercises |

---

## How to Compile & Run

### Windows (PowerShell)
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
Copy-Item src\main\resources\config bin -Recurse -Force
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -ea -cp "bin;src/main/resources" com.gdb.tests.TestRepositoryInMemory
```

### Linux & macOS (Terminal / Bash)
```bash
mkdir -p bin && cp -r src/main/resources/config bin/
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -ea -cp "bin:src/main/resources" com.gdb.tests.TestRepositoryInMemory
```

---

## Expected Output (`TestRepositoryInMemory`)
```text
============================================================
  ACTIVITY 21 — REPOSITORY PATTERN (IN-MEMORY)
============================================================

📂 Loading account rules from properties files...
--------------------------------------------------
✅ Loaded rules for: SAVINGS (4 tenure buckets)
✅ Loaded rules for: CURRENT (3 tenure buckets)
✅ Loaded rules for: FIXEDDEPOSIT (4 tenure buckets)
✅ Loaded rules for: SALARY (4 tenure buckets)
--------------------------------------------------
✅ All rules loaded successfully!
   Loaded at: 2026-10-10T09:22:10.701630600
   Account types: [SALARY, SAVINGS, FIXEDDEPOSIT, CURRENT]
[TEST 1] Account Creation & Auto ID Generation:
  acc1 ID = 1001 (Expected 1001)
  acc2 ID = 1002 (Expected 1002)
  -> PASSED

[TEST 2] Deposit via Service:
  [TXN-1791604330738-1] DEPOSIT | Rs. 10000.0 | Balance After: Rs. 60000.0 | Status: SUCCESS | Deposit of Rs. 10000.0
  -> PASSED

[TEST 3] Withdraw via Service:
  [TXN-1791604330769-2] WITHDRAW | Rs. 5000.0 | Balance After: Rs. 55000.0 | Status: SUCCESS | Withdrawal of Rs. 5000.0
  -> PASSED

[TEST 4] Transfer via Service:
  [TXN-1791604330772-3] TRANSFER | Rs. 10000.0 | Balance After: Rs. 45000.0 | Status: SUCCESS | Transfer of Rs. 10000.0 to Account #1002
  -> PASSED

[TEST 5] Repository Queries:
  Total accounts in repo: 2 (Expected 2)
  Total transactions in repo: 3 (Expected 3)
  Transactions for account 1001: 3 (Expected 3)
  -> PASSED

[TEST 6] RepositoryFactory Mode:
  Persistence mode: memory
  -> PASSED

============================================================
  ALL ACTIVITY 21 IN-MEMORY REPOSITORY TESTS PASSED!
============================================================
```

---

## Verification Checklist
- [x] Project compiles with no errors.
- [x] `AccountRepository` and `TransactionRepository` interfaces define persistence contracts.
- [x] `InMemoryAccountRepository` implements CRUD and synchronized auto-increment account numbers.
- [x] `InMemoryTransactionRepository` provides list storage and filtering by account.
- [x] `RepositoryFactory` reads `persistence.mode` from `config/persistence.properties`.
- [x] `AccountService` delegates entity persistence to `AccountRepository` and `TransactionRepository`.
- [x] `TestRepositoryInMemory` passes with 100% assertions satisfied.
