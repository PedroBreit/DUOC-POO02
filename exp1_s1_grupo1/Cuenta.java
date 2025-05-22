package exp1_s1_grupo1;

public class Cuenta {
    private long numeroCuenta;
    private double saldo;

    public Cuenta(long numeroCuenta) {
        if (String.valueOf(numeroCuenta).length() != 9) {
            throw new IllegalArgumentException("El número de cuenta debe tener exactamente 9 dígitos.");
        }
        this.numeroCuenta = numeroCuenta;
        this.saldo = 0.0; // se inicia en 0 pesos
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a depositar debe ser mayor que cero.");
            return;
        }
        saldo += monto;
        System.out.println("Depósito exitoso. Nuevo saldo: $" + saldo);
    }

    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("El monto a girar debe ser mayor que cero.");
            return;
        }
        if (saldo <= 0) {
            System.out.println("No se puede girar: el saldo es cero.");
            return;
        }
        if (monto > saldo) {
            System.out.println("Monto excede el saldo disponible.");
            return;
        }
        saldo -= monto;
        System.out.println("Giro realizado. Saldo restante: $" + saldo);
    }

    public void consultarSaldo() {
        System.out.println("Saldo actual: $" + saldo);
    }

    @Override
    public String toString() {
        return "Número de Cuenta=" + numeroCuenta +
                "\nSaldo= $" + saldo;
    }
}
