package bank_europe.cuentas;

import bank_europe.interfaces.Consultas;
import bank_europe.interfaces.Interes;
import bank_europe.interfaces.Transacciones;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
 
public class CuentaAhorros extends CuentaBancaria implements Transacciones, Consultas, Interes {
    private static final double tasaInteres = 4.7; // Aquí se puede modificar el % de interés
    private LocalDate fechaUltimoDeposito;

    public CuentaAhorros() {
        super();
        this.fechaUltimoDeposito = LocalDate.now();
    }
        
    @Override
    public double calcularInteres(){
        long dias = ChronoUnit.DAYS.between(fechaUltimoDeposito, LocalDate.now());
        return saldo * (tasaInteres / 100) * dias / 365.0;
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
