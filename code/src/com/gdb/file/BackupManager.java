package com.gdb.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

/**
 * Manages timestamped backups of data files and maintains retention limits.
 */
public class BackupManager {

    // ============================================================
    // 📝 STEP 6: Implement backup(String path, int maxBackups)
    // ============================================================
    public static void backup(String path, int maxBackups) throws IOException {
        if (path == null) return;
        Path src = Paths.get(path);
        if (!Files.exists(src)) {
            return;
        }

        Path parent = src.getParent();
        if (parent == null) {
            parent = Paths.get(".");
        }
        String fileName = src.getFileName().toString();
        String timestamp = System.currentTimeMillis() + "_" + (System.nanoTime() % 1_000_000);
        Path backupPath = parent.resolve(fileName + ".bak." + timestamp);
        Files.copy(src, backupPath, StandardCopyOption.REPLACE_EXISTING);

        File[] backups = parent.toFile().listFiles((dir, name) -> name.startsWith(fileName + ".bak."));
        if (backups != null && backups.length > maxBackups) {
            Arrays.sort(backups, Comparator.comparingLong(File::lastModified));
            int excess = backups.length - maxBackups;
            for (int i = 0; i < excess; i++) {
                backups[i].delete();
            }
        }
    }
}
