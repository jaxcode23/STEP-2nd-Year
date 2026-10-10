# Activity 20: AccountUI (Interactive Console Application)

## Overview
This activity introduces `AccountUI`, a menu-driven interactive console user interface built on top of `AccountService`.

---

## Files Included
| File | Description |
|---|---|
| `AccountUI.java` | Interactive menu-driven console UI |
| `TestAccountUI.java` | Test driver validating UI integration and service endpoints |
| `Main.java` | Application bootstrapper launching `AccountUI` |
| `docs/ACTIVITY_20.md` | Complete activity guide with instructions and architecture |

---

## How to Compile & Run

### Windows (PowerShell)
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
Copy-Item src\main\resources\config bin -Recurse -Force
javac -encoding UTF-8 -d bin (Get-ChildItem -Recurse -Filter *.java src).FullName
java -cp bin com.gdb.tests.TestAccountUI
```

To run the interactive console app:
```powershell
java -cp bin com.gdb.Main
```

### Linux & macOS (Terminal / Bash)
```bash
mkdir -p bin && cp -r src/main/resources/config bin/
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin com.gdb.tests.TestAccountUI
```

To run the interactive console app:
```bash
java -cp bin com.gdb.Main
```

---

## Expected Output (`TestAccountUI`)
```text
============================================================
  ACTIVITY 20 — ACCOUNT UI & INTEGRATION TEST
============================================================

📂 Loading account rules from properties files...
--------------------------------------------------
✅ Loaded rules for: SAVINGS (4 tenure buckets)
✅ Loaded rules for: CURRENT (3 tenure buckets)
✅ Loaded rules for: FIXEDDEPOSIT (4 tenure buckets)
✅ Loaded rules for: SALARY (4 tenure buckets)
--------------------------------------------------
✅ All rules loaded successfully!

[UI TEST] Opened: Account #1001 | Alice Cooper (28 yrs, Tenure: 0 yrs) | Savings | Rs. 20000.0 | Active
[UI TEST] Deposit Rs. 5000 | Balance: Rs. 25000.0
[UI TEST] Withdraw Rs. 3000 | Balance: Rs. 22000.0
[UI TEST] Total Logged Transactions: 2
All UI service endpoints validated successfully!
```

---

## Verification Checklist
- [x] Project compiles with no errors.
- [x] `AccountUI` implements full interactive menu (Open, Deposit, Withdraw, Transfer, Close, View, History, Exit).
- [x] Input handlers safely parse integers, doubles, and strings with retry on format error.
- [x] UI layer communicates strictly with `AccountService` without directly coupling to repositories or rules engine.
- [x] `Main.java` cleanly wires `FileLogDestination` → `TransactionLogger` → `AccountService` → `AccountUI`.
- [x] `TestAccountUI` validates all service endpoints headless and passes with 100% success.

