package modelo;

import negocio.Facturable;

public class VehiculoCarga extends Vehiculo implements Facturable {
    private double capacidadCarga;

    public VehiculoCarga() {
        super();
    }

    public VehiculoCarga(String patente, String marca, String modelo, int anio, double precioPorDia, double capacidadCarga) {
        super(patente, marca, modelo, anio, precioPorDia);
        this.capacidadCarga = capacidadCarga;
    }

    public double getCapacidadCarga() { return capacidadCarga; }
    public void setCapacidadCarga(double capacidadCarga) { this.capacidadCarga = capacidadCarga; }

    @Override
    public void mostrarDatos() {
        System.out.println("Vehículo de carga:");
        System.out.println("Patente: " + patente);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Año: " + anio);
        System.out.println("Capacidad de carga: " + capacidadCarga + " toneladas");
        System.out.println("Precio por día: " + precioPorDia);
        System.out.println("Estado: " + estado);
    }

    @Override
    public void mostrarBoleta() {
    }
}