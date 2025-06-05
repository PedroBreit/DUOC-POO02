package bank_europe.cuentas;

import bank_europe.interfaces.Consultas;
import bank_europe.interfaces.Interes;
import bank_europe.interfaces.Transacciones;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
 
public class CuentaDigital extends CuentaBancaria implements Transacciones, Consultas, Interes {
    private static final double tasaInteres = 5.5; // Aquí se puede modificar el % de interés
    private LocalDate fechaUltimoDeposito;

    public CuentaDigital() {
        super();
        this.fechaUltimoDeposito = LocalDate.now();
    }
        
    @Override
    public double calcularInteres(){
        long dias = ChronoUnit.DAYS.between(fechaUltimoDeposito, LocalDate.now());
        double tasaDiaria = tasaInteres / 100 / 365;
        return saldo * (Math.pow(1 + tasaDiaria, dias) - 1);
    }

    @Override
    public void depositar(double monto){
        if(monto <= 0){
            System.out.println("--------------------------------------------------\nMonto Inválido.");
            return;
        }
        saldo += monto;
        fechaUltimoDeposito = LocalDate.now();
        System.out.println("--------------------------------------------------\nDepósito exitoso.\nSaldo: $" + saldo);
    }
      
    @Override
    public void consultarSaldo(){
        
        
        long dias = ChronoUnit.DAYS.between(fechaUltimoDeposito, LocalDate.now());
        double interesGanado = calcularInteres();
        System.out.println("-----------------------------------\nSaldo base: $" + saldo + 
                           "\nInterés acumulado (" + dias + " días): $" + interesGanado +
                           "\nSaldo total: $" + (saldo + interesGanado) +
                           "\n-----------------------------------");
        
    }
}