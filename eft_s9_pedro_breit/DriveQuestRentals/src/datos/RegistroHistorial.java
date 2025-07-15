package datos;

import modelo.Vehiculo;
import modelo.VehiculoCarga;
import java.io.FileWriter;
import java.io.PrintWriter;

public class RegistroHistorial {
    public static void guardarArriendo(Vehiculo v, int dias, double total) {
        if (dias >= 7) {
            try (PrintWriter pw = new PrintWriter(new FileWriter("historial_arriendos.csv", true))) {
                String tipo = v instanceof VehiculoCarga ? "Carga" : "Pasajero";
                String linea = v.getPatente() + "," +
                        tipo + "," +
                        v.getMarca() + "," +
                        v.getModelo() + "," +
                        v.getAnio() + "," +
                        dias + "," +
                        v.getPrecioPorDia() + "," +
                        total + "," +
                        java.time.LocalDate.now();
                pw.println(linea);
            } catch (Exception e) {
                System.out.println("Error al guardar en historial: " + e.getMessage());
            }
        }
    }
}