package util;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoggerUtil {
    public static void logError(String mensaje, Exception e) {
        try (FileWriter writer = new FileWriter("error_log.txt", true)) {
            writer.write(LocalDateTime.now() + " - " + mensaje + ": " + e.toString() + "\n");
        } catch (IOException ex) {
            System.out.println("No se pudo escribir en el log de errores: " + ex.getMessage());
        }
    }
}