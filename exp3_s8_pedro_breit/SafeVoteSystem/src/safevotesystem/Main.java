package safevotesystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import safevotesystem.Primes.PrimesBuffer;
import safevotesystem.Primes.PrimesList;
import safevotesystem.Primes.PrimesThread;
import safevotesystem.io.FilesIOUtil;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numThreads = 100;
        int maxBuffer = numThreads / 5 ;
        
        PrimesList primesAdd = new PrimesList();
        PrimesList primesCSV =new PrimesList();
        PrimesBuffer buffer = new PrimesBuffer();
        
        try {
            FilesIOUtil.loadPrimesFromCSV("primes.csv", primesCSV);
            System.out.println("Primos cargados desde archivo: " + primesCSV.getPrimesCount());
        } catch (IOException e) {
            System.out.println("Error cargando archivo: " + e.getMessage());
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(new PrimesThread(buffer, maxBuffer, i));
            threads.add(t);
            t.start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                Integer prime = buffer.take(); // Espera si el buffer está vacío
                primesAdd.add(prime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Main interrumpido.");
            }
        }

        // 3. Esperar que terminen los productores
        for (Thread t : threads) {
            t.join();
        }

        // 4. Mostrar los resultados
        int newPrimes = primesAdd.getPrimesCount() - primesCSV.getPrimesCount();
        System.out.println("Primos encontrados: " + newPrimes);
        System.out.println();
        
        try {
            FilesIOUtil.writePrimesToTXT("primescodes.txt", primesAdd);
        } catch (Exception e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}
