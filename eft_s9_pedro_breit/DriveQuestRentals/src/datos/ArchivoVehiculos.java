package datos;

import modelo.Vehiculo;
import modelo.VehiculoCarga;
import modelo.VehiculoPasajero;
import java.io.*;
import java.util.*;

public class ArchivoVehiculos {

    public static void guardarVehiculos(List<Vehiculo> lista, String archivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Vehiculo v : lista) {
                String estado = v.getEstado();
                if (v instanceof VehiculoCarga) {
                    VehiculoCarga c = (VehiculoCarga) v;
                    bw.write("CARGA," + c.getPatente() + "," + c.getMarca() + "," + c.getModelo() + "," + c.getAnio() + "," + c.getPrecioPorDia() + "," + c.getCapacidadCarga() + "," + estado);
                } else if (v instanceof VehiculoPasajero) {
                    VehiculoPasajero p = (VehiculoPasajero) v;
                    bw.write("PASAJERO," + p.getPatente() + "," + p.getMarca() + "," + p.getModelo() + "," + p.getAnio() + "," + p.getPrecioPorDia() + "," + p.getMaxPasajeros() + "," + estado);
                }
                bw.newLine();
            }
        }
    }

    public static List<Vehiculo> cargarVehiculos(String archivo) throws IOException {
        List<Vehiculo> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 8) continue;
                if (datos[0].equals("CARGA")) {
                    VehiculoCarga c = new VehiculoCarga(
                        datos[1],
                        datos[2],
                        datos[3],
                        Integer.parseInt(datos[4]),
                        Double.parseDouble(datos[5]),
                        Double.parseDouble(datos[6])
                    );
                    c.setEstado(datos[7]);
                    lista.add(c);
                } else if (datos[0].equals("PASAJERO")) {
                    VehiculoPasajero p = new VehiculoPasajero(
                        datos[1],
                        datos[2],
                        datos[3],
                        Integer.parseInt(datos[4]),
                        Double.parseDouble(datos[5]),
                        Integer.parseInt(datos[6])
                    );
                    p.setEstado(datos[7]);
                    lista.add(p);
                }
            }
        }
        return lista;
    }
}