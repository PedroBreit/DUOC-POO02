package exp1_s2_grupo1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Cuenta_Ahorro extends Cuenta implements Transacciones, Consultas, Interes {
    private double tasaInteres;
    private LocalDate fechaUltimoDeposito;
    private int girosEsteMes;
    private int mesUltimoGiro;

    public Cuenta_Ahorro(double tasaInteres) {
        super();
        this.tasaInteres = tasaInteres;
        this.fechaUltimoDeposito = LocalDate.now();
        this.girosEsteMes = 0;
        this.mesUltimoGiro = LocalDate.now().getMonthValue();
    }
    
    @Override
    public void depositar(double monto){
        if(monto <= 0){
            System.out.println("--------------------------------------------------\nMonto Invalido.");
            return;
        }
        saldo += monto;
        fechaUltimoDeposito = LocalDate.now();
        System.out.println("--------------------------------------------------\nDeposito exitoso.\n Saldo: $" + saldo);
    }
    
    @Override
    public void consultarSaldo(){
        long dias = ChronoUnit.DAYS.between(fechaUltimoDeposito, LocalDate.now());
        double interesGanado = saldo * (tasaInteres / 100) * dias / 365.0;
        System.out.println("-----------------------------------\nSaldo base: $" + saldo + 
                           "\nInterés acumulado (" + dias + " días): $" + interesGanado +
                           "\nSaldo total: $" + (saldo + interesGanado) +
                           "\n-----------------------------------");
    }
    
    @Override
    public double calcularInteres(){
        long dias = ChronoUnit.DAYS.between(fechaUltimoDeposito, LocalDate.now());
        return saldo * (tasaInteres / 100) * dias / 365.0;
    }

    @Override
    public void girar(double monto) {
        int mesActual = LocalDate.now().getMonthValue();
        if (mesActual != mesUltimoGiro) {
            girosEsteMes = 0;
            mesUltimoGiro = mesActual;
        }
        
        if (girosEsteMes >= 2) {
            System.out.println("-----------------------------------\nLimite de 2 giros mensuales alcanzado.");
            return;
        }
        
        if (monto <= 0) {
            System.out.println("--------------------------------------------------\nMonto inválido.");
            return;
        }
        
        if (monto > saldo) {
            System.out.println("--------------------------------------------------\nFondos insuficientes.");
            return;
        }
        
        saldo -= monto;
        girosEsteMes ++;
        System.out.println("--------------------------------------------------\nGiro realizado. Nuevo saldo: $" + saldo);
    }
    @Override
    public String toString() {
        return "Cuenta Ahorro #" + getNumeroCuenta() +
               "\nSaldo: $" + saldo +
               "\nTasa de interés: " + tasaInteres + "%" +
               "\nÚltimo depósito: " + fechaUltimoDeposito;
    }
}