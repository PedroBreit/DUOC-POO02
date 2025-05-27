package exp1_s2_grupo1;

public class Cuenta_Credito extends Cuenta {
    private double limiteCredito;

    public Cuenta_Credito(double limiteCredito) {
        super();
        this.limiteCredito = limiteCredito;
    }

    @Override
    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a girar debe ser mayor que cero.");
            return;
        }
        if (saldo + monto > limiteCredito) {
            System.out.println("------------------------------------------\nCrédito insuficiente. Giro rechazado.");
            return;
        }
        saldo += monto; // aumenta la deuda
        System.out.println("-----------------------------------\nCrédito utilizado.\nDeuda acumulada: $" + saldo + "\n-----------------------------------");
    }

    public void pagarCredito(double monto) {
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a pagar debe ser mayor que cero.");
            return;
        }
        if (monto > saldo) {
            monto = saldo; // no se puede pagar más de lo que se debe
        }
        saldo -= monto;
        System.out.println("-----------------------------------\nPago realizado.\nDeuda restante: $" + saldo + "\n-----------------------------------");
    }

    public double obtenerCreditoDisponible() {
        return limiteCredito - saldo;
    }
}
