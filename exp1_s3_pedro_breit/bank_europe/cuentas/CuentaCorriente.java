package bank_europe.cuentas;

import bank_europe.interfaces.Consultas;
import bank_europe.interfaces.Interes;
import bank_europe.interfaces.Transacciones;
 
public class CuentaCorriente extends CuentaBancaria implements Transacciones, Consultas, Interes {

    public CuentaCorriente() {
        super();
    }
        
    @Override
    public double calcularInteres(){
        return 0.0;
    }

    @Override
    public void depositar(double monto){
        if(monto <= 0){
            System.out.println("--------------------------------------------------\nMonto Inválido.");
            return;
        }
        saldo += monto;
        System.out.println("--------------------------------------------------\nDepósito exitoso.\nSaldo: $" + saldo);
    }
      
    @Override
    public void consultarSaldo(){
            System.out.println("-----------------------------------\nSaldo: $" + saldo +
                               "\n-----------------------------------");
    }
}
