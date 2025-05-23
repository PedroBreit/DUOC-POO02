
package exp1_s1_grupo1;

public class Cuenta {
    private long numeroCuenta;
    private double saldo;

    // Constructor: solo genera número automáticamente
    public Cuenta() {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = 0.0;
        System.out.println("----------------------------\nNúmero de cuenta: " + this.numeroCuenta + "\n----------------------------");
    }

    // Método privado para generar un número de 9 dígitos
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

    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("---------------------------------------------\nEl monto a depositar debe ser mayor que cero.\n---------------------------------------------");
            return;
        }
        saldo += monto;
        System.out.println("\n-----------------------------------\nDepósito exitoso.\nNuevo saldo: $" + saldo);
    }

    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("-----------------------------------------\nEl monto a girar debe ser mayor que cero.");
            return;
        }
        if (saldo <= 0) {
            System.out.println("------------------------------------\nNo se puede girar: el saldo es cero.");
            return;
        }
        if (monto > saldo) {
            System.out.println("---------------------------------\nMonto excede el saldo disponible.");
            return;
        }
        saldo -= monto;
        System.out.println("\n-----------------------------------\nGiro realizado.\nSaldo restante: $" + saldo + "\n-----------------------------------");
    }

    public void consultarSaldo() {
        System.out.println("---------------------\nSaldo actual: $" + saldo + "\n---------------------");
    }

    @Override
    public String toString() {
        return "-----------------------------\n" +
               "Número de Cuenta = " + numeroCuenta +
               "\nSaldo = $" + saldo +
               "\n=============================";
    }
}