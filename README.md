# Global Digital Bank (GDB) — Core Banking Domain System
> **STEP 2nd Year — Object-Oriented Software Engineering & Design Patterns Progression**

[![Java](https://img.shields.io/badge/Java-8%20%7C%2011%20%7C%2017%20%7C%2021-orange.svg)](#prerequisites)
[![Architecture](https://img.shields.io/badge/Architecture-Modular%20Domain%20Model-blue.svg)](#architectural-evolution--design-patterns)
[![Design Patterns](https://img.shields.io/badge/Patterns-Factory%20%7C%20Template%20%7C%20Singleton%20%7C%20Service-green.svg)](#architectural-evolution--design-patterns)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](#how-to-run-on-your-local-desktop)

---

## 📌 Table of Contents
1. [Project Overview](#-project-overview)
2. [Commit-to-Activity History](#-commit-to-activity-history)
3. [Folder & File Structure Matrix](#-folder--file-structure-matrix)
4. [How to Run on Your Local Desktop](#-how-to-run-on-your-local-desktop)
   - [Prerequisites](#prerequisites)
   - [Option A: Command-Line (PowerShell & Bash)](#option-a-command-line-powershell--bash)
   - [Option B: Visual Studio Code](#option-b-visual-studio-code)
   - [Option C: Eclipse IDE / IntelliJ IDEA](#option-c-eclipse-ide--intellij-idea)
5. [Activity-by-Activity Walkthrough](#-activity-by-activity-walkthrough)
6. [Architectural Evolution & Design Patterns](#-architectural-evolution--design-patterns)
7. [License & Author](#-license--author)

---

## 🏦 Project Overview

The **Global Digital Bank (GDB)** repository encapsulates a progressive curriculum in Object-Oriented Programming (OOP) and Software Architecture. Across 10+ hands-on activities, the codebase transitions from a simple, monolithic account class into an enterprise-grade banking engine featuring:

- **Defensive Error Handling:** Custom checked exception hierarchy preventing illegal transactions and state transitions.
- **Polymorphic Domain Modeling:** Specialized account types (`Savings`, `Current`, `FixedDeposit`, `Salary`) with dynamic runtime dispatch.
- **Design Patterns:** Creational (`Factory`), Structural (`Interface Abstraction`), and Behavioral (`Template Method`, `Singleton`, `Strategy`).
- **Externalized Configuration:** Dynamic `.properties`-driven business rules engine with tiering based on customer tenure and hot reload support.
- **Transactional Integrity:** A thread-safe `TransferService` with PIN authentication, daily transfer limits tracking, and atomic rollback upon failure.

---

## 📜 Commit-to-Activity History

Every activity is isolated and tagged with a dedicated Git commit so you can inspect the exact code changes and evolution step-by-step:

| Commit Hash | Commit Message | Activity | Core Architectural Milestone |
| :--- | :--- | :---: | :--- |
| `83376b8` | `Initial commit` | **Baseline** | Baseline banking domain models, interactive CLI console menu in `code/` |
| `fda376b` | `feat(activity6): complete Activity 6 exception handling and recovery suite` | **Activity 6** | Custom checked exceptions (`AccountException` hierarchy), defensive validation |
| `dfc3f73` | `feat(activity7): complete Activity 7 account subclasses inheritance` | **Activity 7** | Subclass inheritance hierarchy (`SavingsAccount`, `CurrentAccount`, `FixedDepositAccount`, `SalaryAccount`) |
| `29a1adf` | `feat(activity8): complete Activity 8 polymorphism and dynamic method dispatch` | **Activity 8** | Dynamic method dispatch and polymorphic debit routines |
| `44ee457` | `feat(activity9): complete Activity 9 abstract classes and template method pattern` | **Activity 9** | `AbstractAccount` base class and the **Template Method Pattern** for debit workflows |
| `fbc5cf2` | `feat(activity10): complete Activity 10 banking operations with abstract accounts` | **Activity 10** | Standardized banking operations operating purely against abstract account models |
| `02030e0` | `feat(activity11): complete Activity 11 interface and factory pattern` | **Activity 11** | Contract interface (`IAccount`) and decoupled creational **Factory Pattern** (`AccountFactory`) |
| `3fbef33` | `feat(activity12): complete Activity 12 factory-driven banking system` | **Activity 12** | Factory-driven architectural refactor decoupling client code from concrete implementations |
| `2ed418b` | `feat(activity13): complete Activity 13.1 and 13.2 account rules engine and dynamic integration` | **Activity 13** | **Singleton** `AccountRulesEngine` with tenure-based tiering (13.1 static, 13.2 dynamic binding) |
| `c41f459` | `feat(activity14): complete Activity 14 external properties rules engine` | **Activity 14** | External `.properties` file loader, configuration externalization, and runtime hot reload |
| `bf6fab1` | `feat(activity15): complete Activity 15 funds transfer with daily limits` | **Activity 15** | Transactional `TransferService`, daily transfer limit tracking, and atomic rollback |
| `5eaab79` | `chore: isolate activity modules with Eclipse and VS Code project configuration` | **Tooling** | Eclipse `.project` / `.classpath` metadata and VS Code workspace configuration |
| `f39c9c9` | `feat(activity16): complete Activity 16 transaction model and audit records` | **Activity 16** | Immutable `Transaction` model, `TransactionType` enum, and overloaded audit methods |
| `bbc11dd` | `feat(activity17): complete Activity 17 command pattern and file logging` | **Activity 17** | Command Pattern (`TransactionCommand`), serialized audit log (`TransactionLog`) |
| `c3c79ee` | `feat(activity18): complete Activity 18 bridge pattern logging` | **Activity 18** | **Bridge Pattern** decoupling `TransactionLogger` abstraction from File, DB, and Memory implementors |
| `5dfc105` | `feat(activity19): complete Activity 19 account service orchestrator` | **Activity 19** | Unified `AccountService` application layer coordinating commands, accounts, and logging |
| `46edc91` | `feat(activity20): complete Activity 20 interactive console application` | **Activity 20** | Interactive menu-driven console UI (`AccountUI`) decoupled from domain logic |
| `1d0f1b5` | `feat(activity21): complete Activity 21 in-memory repository pattern` | **Activity 21** | Repository pattern interfaces, in-memory collection backends, and configurable `RepositoryFactory` |
| `f9cca78` | `feat(activity22): complete Activity 22 JDBC connection and schema initialization` | **Activity 22** | Relational SQLite JDBC connection management, schema initialization, and table DDL |

---

## 📂 Folder & File Structure Matrix

The repository is structured into distinct, self-contained activity modules located under `activity/` along with the baseline interactive application in `code/`:

```
STEP-2nd-Year/
├── README.md                                 # Root project documentation (this file)
├── LICENSE                                   # Project license
├── .gitignore                                # Git ignore configuration
├── .vscode/                                  # VS Code workspace settings and launch profiles
│
├── code/                                     # Interactive Standalone Banking Console Application
│   ├── Account.java                          # Baseline procedural Account class
│   ├── AccountEnhanced.java                  # Enhanced Account model
│   ├── AccountException.java                 # Base exception
│   ├── InsufficientBalanceException.java     # Overdraw exception
│   ├── MinimumBalanceViolationException.java # Minimum balance exception
│   ├── CurrentAccount.java                   # Current account subclass with overdraft
│   ├── SavingsAccount.java                   # Savings account subclass with interest
│   ├── SalaryAccount.java                    # Zero-balance salary account
│   ├── FixedDepositAccount.java              # Term deposit account
│   ├── TestAccount.java                      # Interactive CLI terminal menu (Deposit/Withdraw/Create)
│   ├── TestAccountEnhanced.java              # Enhanced test driver
│   └── TestAccountSubclasses.java            # Subclass test driver
│
└── activity/                                 # Progressive Learning Activities (Isolated Modules)
    ├── activity6/activity6/                  # Activity 6: Exception Handling Suite
    │   ├── src/com/gdb/domain/Account.java
    │   ├── src/com/gdb/exceptions/           # Custom exception hierarchy (6 exception classes)
    │   ├── src/com/gdb/tests/TestAccountExceptions.java
    │   └── README.md
    │
    ├── activity7/activity7/                  # Activity 7: Account Subclasses Inheritance
    │   ├── src/com/gdb/domain/               # Account, Savings, Current, Salary, FixedDeposit
    │   ├── src/com/gdb/exceptions/
    │   ├── src/com/gdb/tests/TestAccountSubclasses.java
    │   └── README.md
    │
    ├── activity8/activity8/                  # Activity 8: Polymorphism & Dynamic Dispatch
    │   ├── src/com/gdb/domain/               # Overridden business methods per account type
    │   ├── src/com/gdb/exceptions/
    │   ├── src/com/gdb/tests/TestAccountSubclasses.java
    │   └── README.md
    │
    ├── activity9/activity9/                  # Activity 9: Abstract Classes & Template Method
    │   ├── src/com/gdb/domain/AbstractAccount.java  # Template method withdraw()
    │   ├── src/com/gdb/domain/               # SavingsAccount, CurrentAccount, FixedDeposit, Salary
    │   ├── src/com/gdb/exceptions/
    │   ├── src/com/gdb/tests/TestAbstractAccount.java
    │   └── README.md
    │
    ├── activity10/activity10/                # Activity 10: Banking Operations with Abstract Accounts
    │   ├── src/com/gdb/domain/
    │   ├── src/com/gdb/exceptions/
    │   ├── src/com/gdb/tests/TestAbstractAccount.java
    │   └── README.md
    │
    ├── activity11/activity11/                # Activity 11: Interface & Factory Pattern
    │   ├── src/com/gdb/domain/IAccount.java          # Banking contract interface
    │   ├── src/com/gdb/domain/AccountFactory.java    # Simple Factory implementation
    │   ├── src/com/gdb/domain/AbstractAccount.java
    │   ├── src/com/gdb/tests/TestInterfaceFactory.java
    │   └── README.md
    │
    ├── activity12/activity12/                # Activity 12: Factory-Driven Banking Architecture
    │   ├── src/com/gdb/domain/
    │   ├── src/com/gdb/exceptions/
    │   ├── src/com/gdb/tests/TestInterfaceFactory.java
    │   └── README.md
    │
    ├── activity13/activity13/                # Activity 13: Dynamic Account Rules Engine
    │   ├── 13.1/                             # 13.1: Static Singleton AccountRulesEngine
    │   │   ├── src/com/gdb/domain/AccountRulesEngine.java
    │   │   └── src/com/gdb/tests/TestAccountRulesEngine.java
    │   └── 13.2/                             # 13.2: Dynamic Rule Binding via Customer Tenure
    │       ├── src/com/gdb/domain/
    │       └── src/com/gdb/tests/TestAccountRulesEngine.java
    │
    ├── activity14/activity14/                # Activity 14: External Properties Rules Engine
    │   ├── src/main/resources/config/rules/  # External .properties configuration files
    │   │   ├── savings.properties            # Tiered rules: NEW, STANDARD, PREMIUM, PRIVILEGE
    │   │   ├── current.properties
    │   │   ├── fixeddeposit.properties
    │   │   └── salary.properties
    │   ├── src/com/gdb/domain/AccountRulesPropertiesLoader.java # Config parser & loader
    │   ├── src/com/gdb/domain/AccountRulesEngine.java           # Rules Engine + Hot Reload
    │   ├── src/com/gdb/tests/TestAccountRulesEngineProperties.java
    │   └── README.md
    │
    └── activity15/activity15/                # Activity 15: Funds Transfer with Daily Limits
        ├── docs/ACTIVITY_15.md               # Step-by-step Activity 15 specification
        ├── src/main/resources/config/rules/  # Properties files with dailyTransferLimit
        ├── src/com/gdb/service/TransferService.java # Transactional transfer coordinator
        ├── src/com/gdb/domain/Account.java   # Real-time daily tracking & rollover
        ├── src/com/gdb/domain/AccountFactory.java
        ├── src/com/gdb/domain/AccountRulesEngine.java
        ├── src/com/gdb/tests/TestTransfer.java # Transfer, limits & rollback test suite
        └── src/com/gdb/tests/TestAccountRulesEngineProperties.java
    │
    ├── activity16/activity16/                # Activity 16: Transaction Model
        ├── docs/ACTIVITY_16.md               # Activity 16 specification
        ├── src/main/resources/config/rules/  # Properties configuration files
        ├── src/com/gdb/domain/Transaction.java # Immutable transaction audit record
        ├── src/com/gdb/domain/TransactionType.java # DEPOSIT, WITHDRAW, TRANSFER enum
        ├── src/com/gdb/domain/Account.java   # depositWithTransaction & withdrawWithTransaction
        ├── src/com/gdb/service/TransferService.java # transferWithTransaction
        └── src/com/gdb/tests/TestTransactionModel.java # Transaction model test suite
    │
    ├── activity17/activity17/                # Activity 17: Command Pattern + File Logging
        ├── docs/ACTIVITY_17.md               # Activity 17 specification
        ├── src/com/gdb/command/              # Command hierarchy (Deposit, Withdraw, Transfer)
        ├── src/com/gdb/logging/TransactionLog.java # ObjectOutputStream binary file logger
        └── src/com/gdb/tests/TestCommandLogging.java # Replay & persistence verification suite
    │
    ├── activity18/activity18/                # Activity 18: Bridge Pattern (File + DB)
        ├── docs/ACTIVITY_18.md               # Activity 18 specification
        ├── src/com/gdb/logging/LogDestination.java # Bridge implementor contract
        ├── src/com/gdb/logging/TransactionLogger.java # Bridge abstraction
        ├── src/com/gdb/logging/FileLogDestination.java # File log implementor
        ├── src/com/gdb/logging/DatabaseLogDestination.java # Database log implementor
        ├── src/com/gdb/logging/MemoryLogDestination.java # In-memory implementor
        └── src/com/gdb/tests/TestBridgeLogging.java # Multi-backend isolation test suite
    │
    ├── activity19/activity19/                # Activity 19: AccountService (Service Layer)
        ├── docs/ACTIVITY_19.md               # Activity 19 specification
        ├── src/com/gdb/service/AccountService.java # Unified orchestration service
        ├── src/com/gdb/Main.java             # Entry point & bootstrap workflow
        └── src/com/gdb/tests/TestAccountService.java # Comprehensive service testing suite
    │
    ├── activity20/activity20/                # Activity 20: AccountUI (Interactive Console Application)
        ├── docs/ACTIVITY_20.md               # Activity 20 specification
        ├── src/com/gdb/ui/AccountUI.java     # Interactive console UI layer
        ├── src/com/gdb/Main.java             # Entry point bootstrapping UI with file logging
        └── src/com/gdb/tests/TestAccountUI.java # Test suite for UI operations
    │
    ├── activity21/activity21/                # Activity 21: Repository Pattern (InMemory + Factory)
        ├── docs/ACTIVITY_21.md               # Activity 21 specification
        ├── src/com/gdb/repository/           # AccountRepository, TransactionRepository, InMemory implementations
        ├── src/com/gdb/repository/RepositoryFactory.java # Configurable repository factory
        ├── src/main/resources/config/persistence.properties # Persistence mode configuration
        └── src/com/gdb/tests/TestRepositoryInMemory.java # In-memory repository & service test suite
    │
    └── activity22/activity22/                # Activity 22: JDBC Foundation (Connection + Schema)
        ├── docs/ACTIVITY_22.md               # Activity 22 specification
        ├── lib/                              # SQLite JDBC driver & SLF4J libraries
        ├── src/com/gdb/db/ConnectionProvider.java # Connection acquisition contract
        ├── src/com/gdb/db/JdbcConnectionProvider.java # SQLite DriverManager connection provider
        ├── src/com/gdb/db/SchemaInitializer.java # DDL statement runner (schema.sql)
        ├── src/main/resources/schema.sql     # Relational schema (accounts & transactions tables)
        └── src/com/gdb/tests/TestJdbcConnection.java # Database connection & table verification suite
```

---

## 💻 How to Run on Your Local Desktop

### Prerequisites

- **Java Development Kit (JDK):** Version 8, 11, 17, or 21+
  Check your installation by running:
  ```bash
  javac -version
  java -version
  ```
- **Git:** (Optional, for cloning and inspecting branch history)
- **Terminal:** PowerShell (Windows), Bash (Linux/macOS), or Zsh

---

### Option A: Command-Line (PowerShell & Bash)

#### 1. Clone or Open the Repository
```bash
git clone https://github.com/jaxcode23/STEP-2nd-Year.git
cd STEP-2nd-Year
```

#### 2. Run All Tests Across All Activities (Automated Test Runner)

**On Windows (PowerShell):**
```powershell
$activities = @(
    @{ Name="Activity 6";  Src="activity/activity6/activity6/src";  Bin="activity/activity6/activity6/bin";  Main="com.gdb.tests.TestAccountExceptions" },
    @{ Name="Activity 7";  Src="activity/activity7/activity7/src";  Bin="activity/activity7/activity7/bin";  Main="com.gdb.tests.TestAccountSubclasses" },
    @{ Name="Activity 8";  Src="activity/activity8/activity8/src";  Bin="activity/activity8/activity8/bin";  Main="com.gdb.tests.TestAccountSubclasses" },
    @{ Name="Activity 9";  Src="activity/activity9/activity9/src";  Bin="activity/activity9/activity9/bin";  Main="com.gdb.tests.TestAbstractAccount" },
    @{ Name="Activity 10"; Src="activity/activity10/activity10/src"; Bin="activity/activity10/activity10/bin"; Main="com.gdb.tests.TestAbstractAccount" },
    @{ Name="Activity 11"; Src="activity/activity11/activity11/src"; Bin="activity/activity11/activity11/bin"; Main="com.gdb.tests.TestInterfaceFactory" },
    @{ Name="Activity 12"; Src="activity/activity12/activity12/src"; Bin="activity/activity12/activity12/bin"; Main="com.gdb.tests.TestInterfaceFactory" },
    @{ Name="Activity 13.1"; Src="activity/activity13/activity13/13.1/src"; Bin="activity/activity13/activity13/13.1/bin"; Main="com.gdb.tests.TestAccountRulesEngine" },
    @{ Name="Activity 13.2"; Src="activity/activity13/activity13/13.2/src"; Bin="activity/activity13/activity13/13.2/bin"; Main="com.gdb.tests.TestAccountRulesEngine" },
    @{ Name="Activity 14"; Src="activity/activity14/activity14/src"; Bin="activity/activity14/activity14/bin"; CP="activity/activity14/activity14/bin;activity/activity14/activity14"; Main="com.gdb.tests.TestAccountRulesEngineProperties" },
    @{ Name="Activity 15"; Src="activity/activity15/activity15/src"; Bin="activity/activity15/activity15/bin"; CP="activity/activity15/activity15/bin;activity/activity15/activity15"; Main="com.gdb.tests.TestTransfer" },
    @{ Name="Activity 16"; Src="activity/activity16/activity16/src"; Bin="activity/activity16/activity16/bin"; CP="activity/activity16/activity16/bin;activity/activity16/activity16"; Main="com.gdb.tests.TestTransactionModel" },
    @{ Name="Activity 17"; Src="activity/activity17/activity17/src"; Bin="activity/activity17/activity17/bin"; CP="activity/activity17/activity17/bin;activity/activity17/activity17"; Main="com.gdb.tests.TestCommandLogging" },
    @{ Name="Activity 18"; Src="activity/activity18/activity18/src"; Bin="activity/activity18/activity18/bin"; CP="activity/activity18/activity18/bin;activity/activity18/activity18"; Main="com.gdb.tests.TestBridgeLogging" },
    @{ Name="Activity 19"; Src="activity/activity19/activity19/src"; Bin="activity/activity19/activity19/bin"; CP="activity/activity19/activity19/bin;activity/activity19/activity19"; Main="com.gdb.tests.TestAccountService" },
    @{ Name="Activity 20"; Src="activity/activity20/activity20/src"; Bin="activity/activity20/activity20/bin"; CP="activity/activity20/activity20/bin;activity/activity20/activity20"; Main="com.gdb.tests.TestAccountUI" },
    @{ Name="Activity 21"; Src="activity/activity21/activity21/src"; Bin="activity/activity21/activity21/bin"; CP="activity/activity21/activity21/bin;activity/activity21/activity21/src/main/resources"; Main="com.gdb.tests.TestRepositoryInMemory" },
    @{ Name="Activity 22"; Src="activity/activity22/activity22/src"; Bin="activity/activity22/activity22/bin"; CompileCP="activity/activity22/activity22/lib/*"; CP="activity/activity22/activity22/bin;activity/activity22/activity22/lib/*;activity/activity22/activity22/src/main/resources"; Main="com.gdb.tests.TestJdbcConnection" }
)

foreach ($act in $activities) {
    Write-Host "`n============================================================" -ForegroundColor Cyan
    Write-Host " Building & Running $($act.Name)..." -ForegroundColor Yellow
    Write-Host "============================================================" -ForegroundColor Cyan
    if (-not (Test-Path $act.Bin)) { New-Item -ItemType Directory -Path $act.Bin | Out-Null }
    $files = Get-ChildItem -Path $act.Src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    if ($act.CompileCP) {
        & javac -encoding UTF-8 -d $act.Bin -cp $act.CompileCP $files
    } else {
        & javac -encoding UTF-8 -d $act.Bin $files
    }
    $cp = if ($act.CP) { $act.CP } else { $act.Bin }
    & java -cp $cp $act.Main
}
```

**On macOS / Linux (Bash):**
```bash
#!/bin/bash
compile_and_run() {
    local name="$1"
    local src="$2"
    local bin="$3"
    local cp="$4"
    local main="$5"
    local compile_cp="${6:-}"

    echo -e "\n============================================================"
    echo -e " Building & Running ${name}..."
    echo -e "============================================================"
    mkdir -p "${bin}"
    if [ -n "${compile_cp}" ]; then
        javac -encoding UTF-8 -d "${bin}" -cp "${compile_cp}" $(find "${src}" -name "*.java")
    else
        javac -encoding UTF-8 -d "${bin}" $(find "${src}" -name "*.java")
    fi
    java -cp "${cp}" "${main}"
}

compile_and_run "Activity 6"  "activity/activity6/activity6/src"  "activity/activity6/activity6/bin"  "activity/activity6/activity6/bin"  "com.gdb.tests.TestAccountExceptions"
compile_and_run "Activity 7"  "activity/activity7/activity7/src"  "activity/activity7/activity7/bin"  "activity/activity7/activity7/bin"  "com.gdb.tests.TestAccountSubclasses"
compile_and_run "Activity 8"  "activity/activity8/activity8/src"  "activity/activity8/activity8/bin"  "activity/activity8/activity8/bin"  "com.gdb.tests.TestAccountSubclasses"
compile_and_run "Activity 9"  "activity/activity9/activity9/src"  "activity/activity9/activity9/bin"  "activity/activity9/activity9/bin"  "com.gdb.tests.TestAbstractAccount"
compile_and_run "Activity 10" "activity/activity10/activity10/src" "activity/activity10/activity10/bin" "activity/activity10/activity10/bin" "com.gdb.tests.TestAbstractAccount"
compile_and_run "Activity 11" "activity/activity11/activity11/src" "activity/activity11/activity11/bin" "activity/activity11/activity11/bin" "com.gdb.tests.TestInterfaceFactory"
compile_and_run "Activity 12" "activity/activity12/activity12/src" "activity/activity12/activity12/bin" "activity/activity12/activity12/bin" "com.gdb.tests.TestInterfaceFactory"
compile_and_run "Activity 13.1" "activity/activity13/activity13/13.1/src" "activity/activity13/activity13/13.1/bin" "activity/activity13/activity13/13.1/bin" "com.gdb.tests.TestAccountRulesEngine"
compile_and_run "Activity 13.2" "activity/activity13/activity13/13.2/src" "activity/activity13/activity13/13.2/bin" "activity/activity13/activity13/13.2/bin" "com.gdb.tests.TestAccountRulesEngine"
compile_and_run "Activity 14" "activity/activity14/activity14/src" "activity/activity14/activity14/bin" "activity/activity14/activity14/bin:activity/activity14/activity14" "com.gdb.tests.TestAccountRulesEngineProperties"
compile_and_run "Activity 15" "activity/activity15/activity15/src" "activity/activity15/activity15/bin" "activity/activity15/activity15/bin:activity/activity15/activity15" "com.gdb.tests.TestTransfer"
compile_and_run "Activity 16" "activity/activity16/activity16/src" "activity/activity16/activity16/bin" "activity/activity16/activity16/bin:activity/activity16/activity16" "com.gdb.tests.TestTransactionModel"
compile_and_run "Activity 17" "activity/activity17/activity17/src" "activity/activity17/activity17/bin" "activity/activity17/activity17/bin:activity/activity17/activity17" "com.gdb.tests.TestCommandLogging"
compile_and_run "Activity 18" "activity/activity18/activity18/src" "activity/activity18/activity18/bin" "activity/activity18/activity18/bin:activity/activity18/activity18" "com.gdb.tests.TestBridgeLogging"
compile_and_run "Activity 19" "activity/activity19/activity19/src" "activity/activity19/activity19/bin" "activity/activity19/activity19/bin:activity/activity19/activity19" "com.gdb.tests.TestAccountService"
compile_and_run "Activity 20" "activity/activity20/activity20/src" "activity/activity20/activity20/bin" "activity/activity20/activity20/bin:activity/activity20/activity20" "com.gdb.tests.TestAccountUI"
compile_and_run "Activity 21" "activity/activity21/activity21/src" "activity/activity21/activity21/bin" "activity/activity21/activity21/bin:activity/activity21/activity21/src/main/resources" "com.gdb.tests.TestRepositoryInMemory"
compile_and_run "Activity 22" "activity/activity22/activity22/src" "activity/activity22/activity22/bin" "activity/activity22/activity22/bin:activity/activity22/activity22/lib/*:activity/activity22/activity22/src/main/resources" "com.gdb.tests.TestJdbcConnection" "activity/activity22/activity22/lib/*"




```

#### 3. How to Run an Individual Activity (e.g. Activity 15)

```powershell
# Navigate into Activity 15
cd activity/activity15/activity15

# Compile all source files into bin/
javac -encoding UTF-8 -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

# Run the Funds Transfer Test Suite (including property configs in classpath)
java -cp "bin;." com.gdb.tests.TestTransfer

# Run the External Properties Rules Engine Test Suite
java -cp "bin;." com.gdb.tests.TestAccountRulesEngineProperties
```

#### 4. How to Run the Interactive Banking CLI (`code/`)

The `code/` folder contains an interactive console banking application:
```powershell
# Compile the code directory
javac -d code/bin code/*.java

# Run the interactive menu
java -cp code/bin TestAccount
```

**Interactive Menu Interface:**
```text
==================================================
       GLOBAL DIGITAL BANK - ACCOUNT MENU
==================================================
1. Create Account
2. Deposit Money
3. Withdraw Money
4. Display Account
5. Display All Accounts
6. Exit
==================================================
Enter your choice: 
```

---

### Option B: Visual Studio Code

1. Install the **Extension Pack for Java** in VS Code (by Microsoft / Red Hat).
2. Open the `STEP-2nd-Year` folder in VS Code (`File > Open Folder...`).
3. The workspace is preconfigured with isolated project modules via `.project` and `.classpath` descriptors.
4. Open any test file (e.g. `activity/activity15/activity15/src/com/gdb/tests/TestTransfer.java`).
5. Click the **Run** or **Debug** CodeLens button located directly above `public static void main(String[] args)`.

---

### Option C: Eclipse IDE / IntelliJ IDEA

- **In Eclipse:**
  1. `File > Import... > General > Existing Projects into Workspace`.
  2. Browse to the root repository folder.
  3. Select all discovered activity subprojects (`activity6`, `activity7`, ..., `activity15`).
  4. Right-click any test file > `Run As > Java Application`.

- **In IntelliJ IDEA:**
  1. `File > Open...` and select `STEP-2nd-Year`.
  2. Mark each `src` directory as a **Sources Root** (Right click `src` > `Mark Directory as` > `Sources Root`).
  3. Click the green play icon next to the `main` method in any `Test*.java` file.

---

## 🔍 Activity-by-Activity Walkthrough

### 🛡️ Activity 6 — Defensive Exception Handling Suite
- **Directory:** `activity/activity6/activity6/`
- **Key Concepts:** Checked exceptions hierarchy, custom error messaging, validation guards for PIN matching, active account states, non-negative deposits, and minimum balance protection.
- **Test Class:** `com.gdb.tests.TestAccountExceptions`

---

### 🧬 Activity 7 & 8 — Subclass Inheritance & Polymorphism
- **Directories:** `activity/activity7/activity7/`, `activity/activity8/activity8/`
- **Key Concepts:** Domain inheritance using specialized classes:
  - `SavingsAccount`: Requires minimum balance and accrues interest.
  - `CurrentAccount`: Supports overdraft limit (balance can go negative up to overdraft limit).
  - `FixedDepositAccount`: Lock-in periods, term interest, blocks premature debits.
  - `SalaryAccount`: Zero minimum balance requirement.
- **Test Class:** `com.gdb.tests.TestAccountSubclasses`

---

### 📐 Activity 9 & 10 — Abstract Classes & Template Method Pattern
- **Directories:** `activity/activity9/activity9/`, `activity/activity10/activity10/`
- **Key Concepts:** `AbstractAccount` defines a final template method:
  ```java
  public void withdraw(double amount, int pin) throws AccountException {
      validateActiveStatus();
      validatePin(pin);
      validateAmount(amount);
      processDebit(amount); // Primitive method deferred to concrete subclasses
  }
  ```
  This standardizes security checks across all account variants while allowing customized debit logic.
- **Test Class:** `com.gdb.tests.TestAbstractAccount`

---

### 🏭 Activity 11 & 12 — Contract Interface & Factory Pattern
- **Directories:** `activity/activity11/activity11/`, `activity/activity12/activity12/`
- **Key Concepts:** `IAccount` interface abstraction completely decouples consumers from concrete domain classes. `AccountFactory` acts as a centralized creational gateway:
  ```java
  IAccount acc = AccountFactory.createAccount("SAVINGS", 1001, "Rajesh Sharma", 30, 50000.0);
  ```
- **Test Class:** `com.gdb.tests.TestInterfaceFactory`

---

### ⚙️ Activity 13 — Account Rules Engine (Singleton)
- **Directory:** `activity/activity13/activity13/`
- **Key Concepts:**
  - `13.1`: Implements `AccountRulesEngine` as a thread-safe Singleton defining minimum balance and interest rates across tenure tiers (`NEW`, `STANDARD`, `PREMIUM`, `PRIVILEGE`).
  - `13.2`: Modifies account subclasses to dynamically query the Singleton rules engine based on customer tenure.
- **Test Class:** `com.gdb.tests.TestAccountRulesEngine`

---

### 📄 Activity 14 — External Properties Rules Engine & Hot Reload
- **Directory:** `activity/activity14/activity14/`
- **Key Concepts:** Configuration is externalized into `.properties` files located in `src/main/resources/config/rules/`.
  - `AccountRulesPropertiesLoader` parses configuration at runtime.
  - Supports live **Hot Reloading** (`engine.reloadRules()`) to modify business rules without recompiling or redeploying code.
- **Test Class:** `com.gdb.tests.TestAccountRulesEngineProperties`

---

### 💸 Activity 15 — Funds Transfer Service with Daily Limits & Rollback
- **Directory:** `activity/activity15/activity15/`
- **Key Concepts:**
  - `TransferService`: Orchestrates funds transfer between two accounts with PIN verification and atomic debit/credit operations.
  - **Automatic Rollback:** If the credit operation fails after debiting the source, the debit is immediately reversed.
  - **Daily Limit Tracking:** Tracks `dailyTransferTotal` per calendar day, auto-resets when a new date begins, and enforces daily caps configured in properties.
- **Test Class:** `com.gdb.tests.TestTransfer`

---

### 📝 Activity 16 — Transaction Model & Audit Records
- **Directory:** `activity/activity16/activity16/`
- **Key Concepts:**
  - `Transaction`: Immutable, serializable audit record capturing `transactionId`, `timestamp`, `accountNumber`, `type`, `amount`, `balanceAfter`, `status`, `description`, `fromAccount`, `toAccount`.
  - `TransactionType`: Enum representing supported money movements (`DEPOSIT`, `WITHDRAW`, `TRANSFER`).
  - **Overloaded Audit Methods:** `depositWithTransaction` and `withdrawWithTransaction` on `Account`, and `transferWithTransaction` on `TransferService`, retaining 100% backward compatibility with legacy void methods.
- **Test Class:** `com.gdb.tests.TestTransactionModel`

---

### ⚡ Activity 17 — Command Pattern + File Logging
- **Directory:** `activity/activity17/activity17/`
- **Key Concepts:**
  - **Command Pattern Hierarchy:** Encapsulates operations in `DepositCommand`, `WithdrawCommand`, and `TransferCommand` implementing `TransactionCommand`.
  - `TransactionLog`: Appends executed commands to `data/transactions.ser` using Java native object serialization (`ObjectOutputStream` and custom `AppendableObjectOutputStream` avoiding corrupted headers).
  - **Command Replay & Deserialization:** Reads back persisted audit logs until `EOFException` and reconstructs historical timeline.
- **Test Class:** `com.gdb.tests.TestCommandLogging`

---

### 🌉 Activity 18 — Bridge Pattern (Pluggable Log Backends)
- **Directory:** `activity/activity18/activity18/`
- **Key Concepts:**
  - **Bridge Pattern Architecture:** Decouples the `TransactionLogger` abstraction from concrete storage implementors defined by the `LogDestination` interface contract.
  - **Pluggable Backends:**
    - `FileLogDestination`: Binary object serialization logger.
    - `DatabaseLogDestination`: Relational/table-based logger utilizing `SimulatedDatabase`.
    - `MemoryLogDestination`: Ultra-fast collection-based test destination.
  - **Dynamic Runtime Re-binding:** `logger.setDestination(...)` allows changing log storage destinations on the fly without interrupting client workflows while preserving strict data isolation.
- **Test Class:** `com.gdb.tests.TestBridgeLogging`

---

### 🏛️ Activity 19 — AccountService (Service Layer Orchestrator)
- **Directory:** `activity/activity19/activity19/`
- **Key Concepts:**
  - `AccountService`: Single point of orchestration managing the lifecycle of banking entities, in-memory accounts map, and sequential account number generation.
  - **Command Encapsulation:** Transforms raw inputs into concrete `DepositCommand`, `WithdrawCommand`, and `TransferCommand` objects before delegating to `TransactionLogger`.
  - **Simplified Client Interface:** Clients interact solely with `service.openAccount()`, `service.deposit()`, `service.withdraw()`, `service.transfer()`, and `service.closeAccount()`.
- **Test Class:** `com.gdb.tests.TestAccountService`

---

### 🖥️ Activity 20 — AccountUI (Interactive Console Application)
- **Directory:** `activity/activity20/activity20/`
- **Key Concepts:**
  - `AccountUI`: Presentation layer offering an 8-option terminal menu (`Open Account`, `Deposit`, `Withdraw`, `Transfer`, `Close Account`, `View Account`, `Transaction History`, `Exit`).
  - **Model-View Separation:** The UI interacts solely with `AccountService` and handles input formatting, error display, and defensive retry loops without business logic leaks.
  - `Main.java`: Dependency injection bootstrap wiring `FileLogDestination` → `TransactionLogger` → `AccountService` → `AccountUI`.
- **Test Class:** `com.gdb.tests.TestAccountUI`

---

### 📦 Activity 21 — Repository Pattern (Interfaces + InMemory + Factory)
- **Directory:** `activity/activity21/activity21/`
- **Key Concepts:**
  - **Repository Abstraction:** `AccountRepository` and `TransactionRepository` interfaces completely isolate domain entities and services from persistence technologies.
  - **In-Memory Implementations:** `InMemoryAccountRepository` (indexed map storage with synchronized sequential ID generator starting at 1001) and `InMemoryTransactionRepository` (historical list ledger with account-based query filtering).
  - **Pluggable Factory:** `RepositoryFactory` parses `config/persistence.properties` (`memory`, with future extensibility for `jdbc` and `file`) and provides cached repository singletons.
  - **Decoupled Service Layer:** `AccountService` constructor accepts repository dependencies, enabling effortless mock injection and alternative storage backends.
- **Test Class:** `com.gdb.tests.TestRepositoryInMemory`

---

### 🗄️ Activity 22 — JDBC Foundation (Connection Management & Schema DDL)
- **Directory:** `activity/activity22/activity22/`
- **Key Concepts:**
  - **Connection Provider Abstraction:** `ConnectionProvider` interface standardizes connection lifecycle and disposal, implemented by `JdbcConnectionProvider` connecting to SQLite via `DriverManager`.
  - **Automated Schema Migration:** `SchemaInitializer` reads and executes DDL statements from `schema.sql` idempotently on startup.
  - **Relational Tables:** Creates `accounts` (with constraints, types, and primary key) and `transactions` (with foreign key referencing `accounts`).
  - **Configurable Database Mode:** `RepositoryFactory` configures JDBC connection properties (`persistence.db.url`, `persistence.db.driver`) and initializes database schema before repository operations.
- **Test Class:** `com.gdb.tests.TestJdbcConnection`

---

## 🏛️ Architectural Evolution & Design Patterns

| Design Pattern / Architectural Pattern | Implementation in GDB | Primary Benefit |
| :--- | :--- | :--- |
| **Factory Method Pattern** | `AccountFactory.createAccount(...)` | Centralizes object creation; caller interacts only with `IAccount` interface. |
| **Template Method Pattern** | `AbstractAccount.withdraw(...)` | Invariant security checks (PIN, status, amount) are enforced in the superclass; debit logic is delegated to `processDebit`. |
| **Singleton Pattern** | `AccountRulesEngine.getInstance()` | Guarantees a single point of truth for business rules and property configuration across the application. |
| **Strategy / Rules Engine Pattern** | `AccountRulesEngine` + `Rule` tiers | Separates business policy (interest rates, minimum balances, daily limits) from domain entities. |
| **Externalized Configuration** | `.properties` files + `AccountRulesPropertiesLoader` | Allows business teams to alter banking policy and limits without source code modifications. |
| **Service Layer Pattern** | `TransferService` | Encapsulates multi-entity transactional workflows (atomic transfer, audit logging, rollback recovery). |
| **Audit Log / Value Object Pattern** | `Transaction` + `TransactionType` | Captures immutable historical snapshots of financial operations for compliance and reconciliation. |
| **Command Pattern** | `TransactionCommand`, `DepositCommand`, etc. | Encapsulates financial operations as standalone executable objects enabling replay, undo, and decoupled logging. |
| **Bridge Pattern** | `TransactionLogger` + `LogDestination` hierarchy | Decouples logging abstraction from storage engines, allowing hot-swapping between File, DB, and Memory. |
| **Application Service Orchestrator** | `AccountService` | Unifies domain operations and infrastructure logging behind a clean transactional facade. |
| **Presentation / CLI View Pattern** | `AccountUI` | Decouples terminal I/O and user interaction from core domain and service logic. |
| **Repository Pattern** | `AccountRepository` + `TransactionRepository` | Completely abstracts data access and storage mechanisms from business services and domain logic. |
| **Provider Pattern** | `ConnectionProvider` + `JdbcConnectionProvider` | Decouples physical JDBC connection management and lifecycle from consumer repositories. |
| **Schema Migration / Initializer** | `SchemaInitializer` + `schema.sql` | Idempotently creates and prepares relational database tables and foreign keys upon startup. |

---

## 📄 License & Author

- **Author:** [Jash Ajmera](https://github.com/jaxcode23)
- **Repository:** [STEP-2nd-Year](https://github.com/jaxcode23/STEP-2nd-Year)
- **License:** MIT License (see [LICENSE](LICENSE) for details).