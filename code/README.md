# Global Digital Bank (GDB) — Enterprise Core Application

## Overview
This directory contains the **cumulative, production-grade core banking application** developed across the GDB architectural curriculum. It incorporates all domain enhancements, design patterns, external configuration mechanisms, transaction audit trails, repository abstractions, and relational database connectivity.

---

## 📂 Directory Structure

```text
code/
├── src/                                  # Modular Java Source Code & Resources
│   ├── com/gdb/
│   │   ├── domain/                       # Core domain entities, Factory, Rules Engine
│   │   ├── exceptions/                   # Checked banking exception hierarchy
│   │   ├── command/                      # Command Pattern (Deposit, Withdraw, Transfer)
│   │   ├── logging/                      # Bridge Pattern loggers (File, DB, Memory)
│   │   ├── repository/                   # Repository Pattern (AccountRepo, TxnRepo, Factory)
│   │   ├── db/                           # JDBC Connection Provider & Schema Initializer
│   │   ├── service/                      # AccountService & TransferService orchestration
│   │   ├── ui/                           # Interactive Console Presentation (AccountUI)
│   │   ├── tests/                        # Full regression & feature verification suites
│   │   └── Main.java                     # Application bootstrap entry point
│   └── main/resources/
│       ├── schema.sql                    # Relational database DDL definitions
│       └── config/
│           ├── persistence.properties    # Persistence mode configuration
│           └── rules/                    # Tenure-tiered properties rule definitions
│
├── lib/                                  # Runtime Libraries & Dependencies
│   ├── sqlite-jdbc-3.44.1.0.jar          # SQLite JDBC Driver
│   ├── slf4j-api-2.0.9.jar               # Logging API
│   └── slf4j-simple-2.0.9.jar            # Simple logger backend
│
├── docs/                                 # Architectural & Requirement Specifications
│   ├── AccountEnhanced.docx              # Enhanced account specifications
│   ├── AccountException.docx             # Exception design document
│   └── TestAccountEnhanced.docx          # Baseline test suite notes
│
├── legacy/                               # Baseline Procedural Models (Historical Reference)
│   ├── Account.java                      # Monolithic procedural account class
│   ├── AccountEnhanced.java              # Intermediate enhanced model
│   ├── AccountException.java             # Baseline exception
│   ├── InsufficientBalanceException.java # Baseline insufficient balance
│   ├── MinimumBalanceViolationException.java
│   ├── CurrentAccount.java               # Baseline current account
│   ├── SavingsAccount.java               # Baseline savings account
│   ├── SalaryAccount.java                # Baseline salary account
│   ├── FixedDepositAccount.java          # Baseline fixed deposit account
│   ├── TestAccount.java                  # Baseline CLI menu
│   ├── TestAccountEnhanced.java          # Baseline test driver
│   └── TestAccountSubclasses.java        # Baseline inheritance tests
│
├── .classpath                            # Eclipse & VS Code Java classpath configuration
├── .project                              # Eclipse project metadata
└── README.md                             # Application documentation (this file)
```

---

## 💻 How to Compile & Run

### 1. Build the Application

**Windows (PowerShell):**
```powershell
New-Item bin -ItemType Directory -Force | Out-Null
javac -encoding UTF-8 -d bin -cp "bin;lib/*" (Get-ChildItem -Recurse -Filter *.java src).FullName
```

**macOS / Linux (Bash):**
```bash
mkdir -p bin
javac -encoding UTF-8 -d bin -cp "bin:lib/*" $(find src -name "*.java")
```

---

### 2. Launch the Interactive Banking Console

To launch the interactive, menu-driven CLI application:

**Windows (PowerShell):**
```powershell
java -cp "bin;lib/*;src/main/resources" com.gdb.Main
```

**macOS / Linux (Bash):**
```bash
java -cp "bin:lib/*:src/main/resources" com.gdb.Main
```

**Menu Options Available:**
1. Open New Account (`Savings`, `Current`, `Salary`, `FixedDeposit`)
2. Deposit Funds
3. Withdraw Funds (PIN protected)
4. Transfer Funds (Atomic transfer with rollback recovery)
5. Close Account
6. View Account Details & Balance
7. View Transaction History (Audit Trail)
8. Exit System

---

### 3. Run Automated Test Suites

You can execute any individual verification test suite:

| Test Suite | Purpose | Execution Command (PowerShell) |
|---|---|---|
| **JDBC Foundation** | Verifies SQLite connectivity and schema DDL | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestJdbcConnection` |
| **In-Memory Repositories** | Tests CRUD, auto ID, and RepositoryFactory | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestRepositoryInMemory` |
| **Console UI Flow** | Tests AccountUI headless service integration | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestAccountUI` |
| **Service Layer** | Tests AccountService orchestrator & commands | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestAccountService` |
| **Bridge Logging** | Tests hot-swapping File, DB, and Memory logs | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestBridgeLogging` |
| **Command Pattern** | Tests command serialization and replay | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestCommandLogging` |
| **Transaction Model** | Tests immutable transaction value objects | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestTransactionModel` |
| **External Rules Engine**| Tests hot-reloadable properties business rules | `java -ea -cp "bin;lib/*;src/main/resources" com.gdb.tests.TestAccountRulesEngineProperties` |

---

### 4. Running Legacy Baseline Files (Reference)

To compile and run the original baseline procedural files in `legacy/`:

```powershell
javac -d bin legacy/*.java
java -cp bin TestAccount
```
