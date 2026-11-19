import java.io.*;

public class SalesReportGenerator {
    public static void generateSafe(String[] storeFiles, String summaryFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(summaryFile))) {
            for (String file : storeFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(file + ": " + line);
                        writer.newLine();
                    }
                }
            }
        }
    }
}
