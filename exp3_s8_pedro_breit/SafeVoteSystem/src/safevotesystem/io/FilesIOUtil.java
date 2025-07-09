package safevotesystem.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import safevotesystem.Primes.PrimesList;

public class FilesIOUtil {
    
    public static void loadPrimesFromCSV (String ruta, PrimesList list) throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(ruta))){
            String line;
            while((line = reader.readLine()) != null){
                int n = Integer.parseInt(line.trim());
                list.add(n);
            }
        }
    }
    
    public static void writePrimesToTXT (String ruta, PrimesList list) throws IOException{
        try (FileWriter writer = new FileWriter(ruta)){
            for(Integer line : list){
                writer.write(line + System.lineSeparator());
            }
            System.out.println("Votos encriptados guardados exitosamente en: " + ruta);
        }catch (IOException e){
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
