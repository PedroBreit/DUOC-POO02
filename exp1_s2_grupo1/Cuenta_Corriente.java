package exp1_s2_grupo1;

public class Cuenta_Corriente extends Cuenta implements Transacciones {
    private double limiteSobregiro;

    public Cuenta_Corriente(double limiteSobregiro) {
        super();
        this.limiteSobregiro = limiteSobregiro;
    }

    @Override
    public void girar(double monto) {
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a girar debe ser mayor que cero.");
            return;
        }
        if (monto > saldo + limiteSobregiro) {
            System.out.println("------------------------------------------\nLímite de sobregiro excedido. Giro rechazado.");
            return;
        }
        saldo -= monto;
        System.out.println("-----------------------------------\nGiro realizado.\nSaldo actual: $" + saldo + "\n-----------------------------------");
    }
    
    @Override
    public void depositar(double monto){
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nEl monto a depositar debe ser mayor que cero");
            return;
        }
        saldo += monto;
        System.out.println("--------------------------------------------------\nDepósito exitoso. Saldo actual: $" + saldo);        
    }
    @Override
    public String toString() {
        return "Cuenta Corriente #" + getNumeroCuenta() +
               "\nSaldo: $" + saldo +
               "\nLímite de sobregiro: $" + limiteSobregiro;
    }   
}
