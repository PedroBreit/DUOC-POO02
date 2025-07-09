package safevotesystem.Primes;

public class PrimesBuffer {
    private final PrimesList buffer = new PrimesList();

    public synchronized void put(Integer prime, int maxSize) throws InterruptedException {
        while (buffer.size() >= maxSize) {
            wait();
        }
        buffer.add(prime);
        notifyAll();
    }

    public synchronized Integer take() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        Integer prime = buffer.remove(0);
        notifyAll();
        return prime;
    }

    public synchronized int getPrimesCount() {
        return buffer.getPrimesCount();
    }
}
