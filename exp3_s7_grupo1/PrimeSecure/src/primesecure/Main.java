package primesecure;

import primosmanager.PrimesList;
import primosmanager.PrimeAdd;

public class Main {
    public static void main(String[] args) {
        PrimesList primesList = new PrimesList();

        // Se crean varios hilos para verificar y agregar números
        Thread t1 = new PrimeAdd(primesList, 7);
        Thread t2 = new PrimeAdd(primesList, 10);
        Thread t3 = new PrimeAdd(primesList, 13);
        Thread t4 = new PrimeAdd(primesList, 18);
        Thread t5 = new PrimeAdd(primesList, 17);

        // Se inician los hilos
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Se espera a que todos los hilos terminen
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Total de primos guardados: " + primesList.getPrimesCount());
        System.out.println("Lista de primos: " + primesList);
    }
}
