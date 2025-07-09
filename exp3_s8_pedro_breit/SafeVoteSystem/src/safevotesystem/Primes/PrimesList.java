package safevotesystem.Primes;

import java.util.ArrayList;

public class PrimesList extends ArrayList<Integer> {

    // Método eficiente para verificar si un número es primo
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        int sqrt = (int)Math.sqrt(n);
        for (int i = 3; i <= sqrt; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public synchronized boolean add(Integer n) {
        if (!isPrime(n)) {
            throw new IllegalArgumentException("Solo se pueden agregar números primos.");
        }
        if (!this.contains(n)) {
            return super.add(n);
        } else {
            return false; // Ya estaba
        }
    }

    @Override
    public Integer remove(int index) {
        Integer value = super.get(index);
        if (!isPrime(value)) {
            throw new IllegalArgumentException("No se puede eliminar: el valor no es primo.");
        }
        return super.remove(index);
        }

    public synchronized int getPrimesCount() {
        return this.size();
    }
}
