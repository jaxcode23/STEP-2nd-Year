package com.gdb.logging;

import com.gdb.command.TransactionCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileLogDestination implements LogDestination {
    // ============================================================
    // 📝 STEP 5: Declare Field
    // ============================================================
    private TransactionLog log;

    // ============================================================
    // 📝 STEP 6: Constructor
    // ============================================================
    public FileLogDestination() {
        this.log = new TransactionLog();
    }

    // ============================================================
    // 📝 STEP 7: write(cmd)
    // ============================================================
    @Override
    public void write(TransactionCommand cmd) {
        try {
            log.log(cmd);
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file log: " + e.getMessage(), e);
        }
    }

    // ============================================================
    // 📝 STEP 8: readAll()
    // ============================================================
    @Override
    public List<TransactionCommand> readAll() {
        try {
            return log.readAll();
        } catch (Exception e) {
            throw new RuntimeException("Error reading from file log: " + e.getMessage(), e);
        }
    }

    // ============================================================
    // 📝 STEP 9: clear()
    // ============================================================
    @Override
    public void clear() {
        log.clear();
    }

    // ============================================================
    // 📝 STEP 10: getDestinationName()
    // ============================================================
    @Override
    public String getDestinationName() {
        return "FILE";
    }
}
