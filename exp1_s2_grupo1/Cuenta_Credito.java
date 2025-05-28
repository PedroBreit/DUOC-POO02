package exp1_s2_grupo1;

public class Cuenta_Credito extends Cuenta implements Transacciones {
    private double limiteCredito;

    public Cuenta_Credito(double limiteCredito) {
        super();
        this.limiteCredito = limiteCredito;
        this.saldo = limiteCredito;
    }

    @Override
    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a girar debe ser mayor que cero.");
            return;
        }
        if (monto > saldo) {
            System.out.println("------------------------------------------\nCrédito insuficiente. Giro rechazado.");
            return;
        }
        saldo -= monto;
        double utilizado = limiteCredito - saldo;
        System.out.println("-----------------------------------\nCrédito utilizado.\nDisponible: $" + saldo + "\nDeuda: $"+ utilizado + "\n-----------------------------------");
    }

    @Override
    public void depositar(double monto) {
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a pagar debe ser mayor que cero.");
            return;
        }
        if (saldo + monto > limiteCredito) {
            System.out.println("--------------------------------------------------\nUsted esta ingresando mas dinero del que deber, vuelva a intentar.");
            return;
        }
        saldo += monto;
        System.out.println("-----------------------------------\nPago realizado.\nCrédito disponible: $" + saldo + "\n-----------------------------------");
    }

    public double obtenerCreditoDisponible() {
        return limiteCredito - saldo;
    }
    
    @Override
    public void consultarSaldo() {
        double deuda = limiteCredito - saldo;
        System.out.println("-----------------------------------");
        System.out.println("Deuda actual: $" + deuda);
        System.out.println("Credito disponible: $" + saldo);
        System.out.println("-----------------------------------");
    }
    @Override
    public String toString() {
        return "Cuenta Crédito #" + getNumeroCuenta() +
               "\nLímite de crédito: $" + limiteCredito +
               "\nCrédito disponible: $" + saldo +
               "\nDeuda actual: $" + (limiteCredito - saldo);
    }
    
}
