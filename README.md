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
    @{ Name="Activity 15"; Src="activity/activity15/activity15/src"; Bin="activity/activity15/activity15/bin"; CP="activity/activity15/activity15/bin;activity/activity15/activity15"; Main="com.gdb.tests.TestTransfer" }
)

foreach ($act in $activities) {
    Write-Host "`n============================================================" -ForegroundColor Cyan
    Write-Host " Building & Running $($act.Name)..." -ForegroundColor Yellow
    Write-Host "============================================================" -ForegroundColor Cyan
    if (-not (Test-Path $act.Bin)) { New-Item -ItemType Directory -Path $act.Bin | Out-Null }
    $files = Get-ChildItem -Path $act.Src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    & javac -encoding UTF-8 -d $act.Bin $files
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

    echo -e "\n============================================================"
    echo -e " Building & Running ${name}..."
    echo -e "============================================================"
    mkdir -p "${bin}"
    javac -encoding UTF-8 -d "${bin}" $(find "${src}" -name "*.java")
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

## 🏛️ Architectural Evolution & Design Patterns

| Design Pattern / Architectural Pattern | Implementation in GDB | Primary Benefit |
| :--- | :--- | :--- |
| **Factory Method Pattern** | `AccountFactory.createAccount(...)` | Centralizes object creation; caller interacts only with `IAccount` interface. |
| **Template Method Pattern** | `AbstractAccount.withdraw(...)` | Invariant security checks (PIN, status, amount) are enforced in the superclass; debit logic is delegated to `processDebit`. |
| **Singleton Pattern** | `AccountRulesEngine.getInstance()` | Guarantees a single point of truth for business rules and property configuration across the application. |
| **Strategy / Rules Engine Pattern** | `AccountRulesEngine` + `Rule` tiers | Separates business policy (interest rates, minimum balances, daily limits) from domain entities. |
| **Externalized Configuration** | `.properties` files + `AccountRulesPropertiesLoader` | Allows business teams to alter banking policy and limits without source code modifications. |
| **Service Layer Pattern** | `TransferService` | Encapsulates multi-entity transactional workflows (atomic transfer, audit logging, rollback recovery). |

---

## 📄 License & Author

- **Author:** [Jash Ajmera](https://github.com/jaxcode23)
- **Repository:** [STEP-2nd-Year](https://github.com/jaxcode23/STEP-2nd-Year)
- **License:** MIT License (see [LICENSE](LICENSE) for details).