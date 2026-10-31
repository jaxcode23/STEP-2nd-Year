package com.gdb.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

/**
 * Provides atomic, crash-resilient file write operations using temp files and atomic rename.
 */
public class AtomicFileWriter {

    // ============================================================
    // 📝 STEP 1: Implement write(String path, List<String> lines)
    // ============================================================
    public static void write(String path, List<String> lines) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        Path targetPath = Paths.get(path);
        if (targetPath.getParent() != null) {
            Files.createDirectories(targetPath.getParent());
        }

        Path tempPath = Paths.get(path + ".tmp");
        Files.write(tempPath, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

        try {
            Files.move(tempPath, targetPath, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
