package com.gdb.logging;

import com.gdb.command.TransactionCommand;
import java.util.Collections;
import java.util.List;

/**
 * Bridge abstraction class decoupling high-level logger API from pluggable storage backends.
 */
public class TransactionLogger {
    // ============================================================
    // 📝 STEP 17: Declare Field
    // ============================================================
    protected LogDestination destination;

    // ============================================================
    // 📝 STEP 18: Constructor
    // ============================================================
    public TransactionLogger(LogDestination destination) {
        this.destination = destination;
    }

    // ============================================================
    // 📝 STEP 19: setDestination()
    // ============================================================
    public void setDestination(LogDestination destination) {
        this.destination = destination;
    }

    // ============================================================
    // 📝 STEP 20: log(cmd)
    // ============================================================
    public void log(TransactionCommand cmd) {
        if (destination != null) {
            destination.write(cmd);
        }
    }

    // ============================================================
    // 📝 STEP 21: readAll()
    // ============================================================
    public List<TransactionCommand> readAll() {
        if (destination != null) {
            return destination.readAll();
        }
        return Collections.emptyList();
    }

    // ============================================================
    // 📝 STEP 22: clear()
    // ============================================================
    public void clear() {
        if (destination != null) {
            destination.clear();
        }
    }

    // ============================================================
    // 📝 STEP 23: getDestinationName()
    // ============================================================
    public String getDestinationName() {
        return destination != null ? destination.getDestinationName() : "UNKNOWN";
    }
}
