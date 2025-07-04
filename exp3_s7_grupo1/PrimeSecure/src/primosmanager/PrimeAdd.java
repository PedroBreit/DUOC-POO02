package primosmanager;

// Clase "hilo" encargado de agregar un número primo a la lista
public class PrimeAdd extends Thread {
    private final PrimesList primesList;
    private final int num;

    public PrimeAdd(PrimesList primesList, int num) {
        this.primesList = primesList;
        this.num = num;
    }

    // Tarea a realizar por el "hilo"
    @Override
    public void run() {
        try {
            primesList.add(num);
            System.out.println("Agregado: " + num);
        } catch (IllegalArgumentException e) {
            System.out.println("No es primo: " + num);
        }
    }
}

