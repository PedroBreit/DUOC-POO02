package safevotesystem.Primes;

import java.util.Random;

public class PrimesThread implements Runnable {
    
    private final PrimesBuffer buffer;
    private final int maxSize;
    private final Random rnd = new Random();
    private final int threadIndex;
    private Integer lastPrime = null;
    
    
    public PrimesThread (PrimesBuffer buffer, int maxSize, int threadIndex){
        this.buffer = buffer;
        this.maxSize = maxSize;
        this.threadIndex = threadIndex;
    }
    
    @Override
    public void run(){
        while(true){
            int random = rnd.nextInt(100000);
            if(PrimesList.isPrime(random)){
                try{
                    buffer.put(random, maxSize);
                    System.out.println("Thread-" + threadIndex + " agregó el número primo: " + random);
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();    
                }
                break;
                        
            }else {
                System.out.println("El " + Thread.currentThread().getName() + " descarto el número: " + random);
            }
        }
    }
    
    
    public Integer getLastPrime(){
        return lastPrime;
    }
}
