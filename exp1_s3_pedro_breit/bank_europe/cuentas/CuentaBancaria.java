package bank_europe.cuentas;

public abstract class CuentaBancaria {
    private long numeroCuenta;
    protected double saldo;

    public CuentaBancaria() {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = 0.0;
        System.out.println("----------------------------");
    }

    private long generarNumeroCuenta() {
        long min = 100_000_000L;
        long max = 999_999_999L;
        return min + (long)(Math.random() * (max - min + 1));
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public double getSaldo() {
        return saldo;
    }

    public abstract void depositar(double monto);

    public void girar(double monto){
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a girar debe ser mayor que cero.");
            return;
        }
        if (monto > saldo){
            System.out.println("------------------------------------------\nFondos insuficientes.");
            return;
        }
        saldo -= monto;
        System.out.println("-----------------------------------\nGiro realizado.\nSaldo actual: $" + saldo + "\n-----------------------------------");
    }
    
    public abstract double calcularInteres();
        
    public void consultarSaldo() {
        System.out.println("---------------------\nSaldo actual: $" + saldo + "\n---------------------");
    }
    
    @Override
    public String toString() {
        return  "Número de Cuenta = " + numeroCuenta +
                "\nSaldo = $" + saldo +
                "\n-----------------------------";
    }
}
