package primosmanager;

import java.util.ArrayList;

public class PrimesList extends ArrayList<Integer> {

    // Método que verifica si un número es primo
    public boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }

    // Sobrescritura del metodo add
    @Override
    public synchronized boolean add(Integer number) {
        if (!isPrime(number)) {
            throw new IllegalArgumentException("Solo se pueden agregar números primos.");
        }
        return super.add(number);
    }

    // Sobrescritura del metodo remove
    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }

    // implementación del metodo getPrimesCount que devuelve la cantidad de numeros primos
    public synchronized int getPrimesCount() {
        return this.size();
    }
}

