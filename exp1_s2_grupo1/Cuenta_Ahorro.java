package exp1_s2_grupo1;

public class Cuenta_Ahorro extends Cuenta {
    private double tasaInteres;

    public Cuenta_Ahorro(double tasaInteres) {
        super();
        this.tasaInteres = tasaInteres;
    }

    public void aplicarInteres() {
        double interes = saldo * tasaInteres;
        saldo += interes;
        System.out.println("----------------------------------\nInterés aplicado: $" + interes + "\nNuevo saldo: $" + saldo + "\n----------------------------------");
    }

    @Override
    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a girar debe ser mayor que cero.");
            return;
        }
        if (monto > saldo) {
            System.out.println("-----------------------------------------\nFondos insuficientes. No se puede girar.");
            return;
        }
        saldo -= monto;
        System.out.println("-----------------------------------\nGiro realizado.\nSaldo restante: $" + saldo + "\n-----------------------------------");
    }
}
