import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Q2 {

    public static String resolvePath(String fileName) {
        File dir = new File("Assignment-6");
        if (dir.exists() && dir.isDirectory()) {
            return "Assignment-6" + File.separator + fileName;
        }
        return fileName;
    }

    public static void generateSampleLogFile(String logFilePath) {
        String[] logs = {
            "2026-11-09 10:00:01 [INFO] [AuthService] User 'admin' logged in successfully.",
            "2026-11-09 10:00:15 [DEBUG] [CacheManager] Cache hit ratio at 94.2%.",
            "2026-11-09 10:01:23 [WARNING] [Database] Connection pool utilization exceeded 80%.",
            "2026-11-09 10:02:05 [INFO] [OrderService] Order #10492 created by customer C-281.",
            "2026-11-09 10:03:45 [ERROR] [PaymentGateway] Payment processing failed for Order #10492: Gateway Timeout (504).",
            "2026-11-09 10:04:10 [INFO] [NotificationService] SMS alert dispatched to +91-9876543210.",
            "2026-11-09 10:05:00 [WARNING] [DiskMonitor] Free disk space on /var/log below 15%.",
            "2026-11-09 10:06:12 [DEBUG] [SessionManager] Session session_92f expired and cleaned.",
            "2026-11-09 10:07:30 [ERROR] [Database] Deadlock detected during concurrent transaction on table 'Inventory'.",
            "2026-11-09 10:08:18 [INFO] [BackupService] Hourly snapshot created successfully.",
            "2026-11-09 10:09:44 [WARNING] [ApiGateway] High latency detected on /api/v1/checkout (1450ms).",
            "2026-11-09 10:10:02 [ERROR] [SecurityFilter] Unauthorized access attempt detected from IP 192.168.1.105."
        };

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(logFilePath));
            for (String line : logs) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Sample log file generated successfully at: " + logFilePath + " (" + logs.length + " lines)");
        } catch (IOException e) {
            System.err.println("Error generating sample log file: " + e.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignored) {}
            }
        }
    }

    public static void analyzeLogs(String inputLogFile, String outputFilteredFile) {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        int totalEntries = 0;
        int errorCount = 0;
        int warningCount = 0;
        int infoCount = 0;
        int debugCount = 0;

        List<String> filteredEntries = new ArrayList<>();

        System.out.println("Starting log analysis on: " + inputLogFile);

        try {
            File source = new File(inputLogFile);
            if (!source.exists()) {
                throw new FileNotFoundException("Input log file does not exist: " + inputLogFile);
            }

            reader = new BufferedReader(new FileReader(source));
            String line;

            while ((line = reader.readLine()) != null) {
                totalEntries++;
                String upper = line.toUpperCase();

                boolean isError = upper.contains("ERROR");
                boolean isWarning = upper.contains("WARNING");

                if (isError) {
                    errorCount++;
                    filteredEntries.add("[LINE " + totalEntries + "] " + line);
                } else if (isWarning) {
                    warningCount++;
                    filteredEntries.add("[LINE " + totalEntries + "] " + line);
                }

                if (upper.contains("INFO")) {
                    infoCount++;
                } else if (upper.contains("DEBUG")) {
                    debugCount++;
                }
            }

            System.out.println("\nLog Parsing Summary:");
            System.out.println("  - Total Log Entries : " + totalEntries);
            System.out.println("  - ERROR Entries     : " + errorCount);
            System.out.println("  - WARNING Entries   : " + warningCount);
            System.out.println("  - INFO Entries      : " + infoCount);
            System.out.println("  - DEBUG Entries     : " + debugCount);

            writer = new BufferedWriter(new FileWriter(outputFilteredFile));

            writer.write("================================================================================");
            writer.newLine();
            writer.write("                       FILTERED LOG REPORT (ERRORS & WARNINGS)                 ");
            writer.newLine();
            writer.write("================================================================================");
            writer.newLine();
            writer.write("Source File       : " + inputLogFile);
            writer.newLine();
            writer.write("Total Log Entries : " + totalEntries);
            writer.newLine();
            writer.write("ERROR Count       : " + errorCount);
            writer.newLine();
            writer.write("WARNING Count     : " + warningCount);
            writer.newLine();
            writer.write("Total Filtered    : " + filteredEntries.size());
            writer.newLine();
            writer.write("================================================================================");
            writer.newLine();
            writer.newLine();

            writer.write("--- Filtered Log Entries ---");
            writer.newLine();
            for (String entry : filteredEntries) {
                writer.write(entry);
                writer.newLine();
            }

            writer.flush();
            System.out.println("\nFiltered results successfully written to: " + outputFilteredFile);

        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException during log processing: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("BufferedReader closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close BufferedReader: " + e.getMessage());
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                    System.out.println("BufferedWriter closed successfully.");
                } catch (IOException e) {
                    System.err.println("Failed to close BufferedWriter: " + e.getMessage());
                }
            }
        }
    }

    public static void displayFileContents(String filePath) {
        System.out.println("\n--- Displaying Contents of " + filePath + " ---");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Could not read file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     QUESTION 2: FILE-BASED LOG ANALYZER          ");
        System.out.println("==================================================");

        String sampleLogFile = resolvePath("application_activities.log");
        String filteredOutputFile = resolvePath("filtered_log_entries.txt");

        System.out.println("\n[Step 1: Generating Sample Log File]");
        generateSampleLogFile(sampleLogFile);

        System.out.println("\n[Step 2: Analyzing Logs & Filtering ERROR / WARNING Entries]");
        analyzeLogs(sampleLogFile, filteredOutputFile);

        System.out.println("\n[Step 3: Verification of Filtered Output File]");
        displayFileContents(filteredOutputFile);

        System.out.println("\n==================================================");
        System.out.println("        EXCEPTION HANDLING DEMONSTRATION          ");
        System.out.println("==================================================");
        System.out.println("[Attempting to analyze a non-existent log file]");
        analyzeLogs(resolvePath("non_existent_log.log"), resolvePath("unused_output.txt"));
    }
}
