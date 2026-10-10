package com.gdb.logging;

import com.gdb.command.TransactionCommand;
import com.gdb.db.SimulatedDatabase;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseLogDestination implements LogDestination {
    private static final String TABLE = "transaction_log";

    // ============================================================
    // 📝 STEP 11: Declare Field
    // ============================================================
    private final SimulatedDatabase db;

    // ============================================================
    // 📝 STEP 12: Constructor
    // ============================================================
    public DatabaseLogDestination(SimulatedDatabase db) {
        this.db = db;
    }

    // ============================================================
    // 📝 STEP 13: write(cmd)
    // ============================================================
    @Override
    public void write(TransactionCommand cmd) {
        if (db != null) {
            db.insert(TABLE, cmd);
        }
    }

    // ============================================================
    // 📝 STEP 14: readAll()
    // ============================================================
    @Override
    public List<TransactionCommand> readAll() {
        if (db == null) return Collections.emptyList();
        return db.selectAll(TABLE).stream()
                .map(o -> (TransactionCommand) o)
                .collect(Collectors.toList());
    }

    // ============================================================
    // 📝 STEP 15: clear()
    // ============================================================
    @Override
    public void clear() {
        if (db != null) {
            db.deleteAll(TABLE);
        }
    }

    // ============================================================
    // 📝 STEP 16: getDestinationName()
    // ============================================================
    @Override
    public String getDestinationName() {
        return "DATABASE";
    }
}
