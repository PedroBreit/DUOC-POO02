package modelo;

import negocio.Facturable;

public class VehiculoPasajero extends Vehiculo implements Facturable {
    private int maxPasajeros;

    public VehiculoPasajero() {
        super();
    }

    public VehiculoPasajero(String patente, String marca, String modelo, int anio, double precioPorDia, int maxPasajeros) {
        super(patente, marca, modelo, anio, precioPorDia);
        this.maxPasajeros = maxPasajeros;
    }

    public int getMaxPasajeros() { return maxPasajeros; }
    public void setMaxPasajeros(int maxPasajeros) { this.maxPasajeros = maxPasajeros; }

    @Override
    public void mostrarDatos() {
        System.out.println("Vehículo de pasajeros:");
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Año: " + anio);
        System.out.println("Máx. pasajeros: " + maxPasajeros);
        System.out.println("Precio por día: " + precioPorDia);
        System.out.println("Estado: " + estado);
    }

    @Override
    public void mostrarBoleta() {
    }
}