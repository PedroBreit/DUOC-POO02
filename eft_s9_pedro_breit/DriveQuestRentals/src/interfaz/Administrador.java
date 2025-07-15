package interfaz;

import datos.ArchivoVehiculos;
import datos.GestorVehiculos;
import java.util.*;
import java.io.*;
import modelo.*;

public class Administrador {
    private final GestorVehiculos gestor;

    public Administrador(GestorVehiculos gestor) {
        this.gestor = gestor;
    }

    public void menuAdministrador(Scanner scanner) throws IOException {
        while (true) {
            System.out.println("\n===== Menú Administrador =====");
            System.out.println("1. Agregar vehículo");
            System.out.println("2. Eliminar vehículo");
            System.out.println("3. Ver vehículos");
            System.out.println("4. Historial de rentas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> agregarVehiculo(scanner);
                case "2" -> eliminarVehiculo(scanner);
                case "3" -> verVehiculos(scanner);
                case "4" -> mostrarHistorial();
                case "5" -> {
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void agregarVehiculo(Scanner scanner) {
        try {
            System.out.println("\nSeleccione tipo de vehículo:");
            System.out.println("1. Carga");
            System.out.println("2. Pasajero");
            System.out.print("Opción: ");
            String tipo = scanner.nextLine();

            System.out.print("Patente: ");
            String patente = scanner.nextLine().toUpperCase();

            if (gestor.existePatente(patente)) {
                System.out.println("Esa patente ya existe.");
                return;
            }

            System.out.print("Marca: ");
            String marca = scanner.nextLine();
            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();
            System.out.print("Año: ");
            int anio = Integer.parseInt(scanner.nextLine());
            System.out.print("Precio por día: ");
            double precio = Double.parseDouble(scanner.nextLine());

            Vehiculo nuevo = null;
            switch (tipo) {
                case "1" -> {
                    System.out.print("Capacidad de carga (toneladas): ");
                    double capacidad = Double.parseDouble(scanner.nextLine());
                    nuevo = new VehiculoCarga(patente, marca, modelo, anio, precio, capacidad);
                }
                case "2" -> {
                    System.out.print("Máx. pasajeros: ");
                    int pasajeros = Integer.parseInt(scanner.nextLine());
                    nuevo = new VehiculoPasajero(patente, marca, modelo, anio, precio, pasajeros);
                }
                default -> {
                    System.out.println("Tipo inválido.");
                    return;
                }
            }
            System.out.print("¿Desea agregar el vehículo? (s/n): ");
            String confirmar = scanner.nextLine();
            if (confirmar.equalsIgnoreCase("s")) {
                gestor.agregarVehiculo(nuevo);
                System.out.println("Vehículo agregado.");
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (Exception e){
            System.out.println("Error al agregar vehículo: " + e.getMessage());
        }
    }

    public void eliminarVehiculo(Scanner scanner) throws IOException {
        System.out.print("\nIngrese la patente del vehículo a eliminar: ");
        String patente = scanner.nextLine().toUpperCase();
        Vehiculo v = gestor.getVehiculoPorPatente(patente);
        if (v == null) {
            System.out.println("No existe un vehículo con esa patente.");
            return;
        }

        
        System.out.println("\nVehículo a eliminar:");
        String header = String.format("%-10s %-15s %-15s %-6s %-10s %-10s %-10s", 
            "Patente", "Marca", "Modelo", "Año", "Tipo", "Extra", "Estado");
        System.out.println(header);
        System.out.println("-------------------------------------------------------------------------------");

        if (v instanceof VehiculoCarga carga) {
            System.out.printf("%-10s %-15s %-15s %-6d %-10s %-10.1f %-10s\n",
                carga.getPatente(), carga.getMarca(), carga.getModelo(),
                carga.getAnio(), "Carga", carga.getCapacidadCarga(), carga.getEstado());
        } else if (v instanceof VehiculoPasajero pasajero) {
            System.out.printf("%-10s %-15s %-15s %-6d %-10s %-10d %-10s\n",
                pasajero.getPatente(), pasajero.getMarca(), pasajero.getModelo(),
                pasajero.getAnio(), "Pasajero", pasajero.getMaxPasajeros(), pasajero.getEstado());
        }

        System.out.println("-------------------------------------------------------------------------------");
        System.out.print("¿Desea eliminar este vehículo? (s/n): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("s")) {
            if (gestor.eliminarVehiculo(patente)) {
                System.out.println("Vehículo eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar (ya no existe).");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private void verVehiculos(Scanner scanner) {
        System.out.println("\nSeleccione tipo de vehiculo para ver:");
        System.out.println("1. Carga");
        System.out.println("2. Pasajero");
        System.out.print("Opcion: ");
        String tipo = scanner.nextLine();

        List<Vehiculo> lista = gestor.listarVehiculos();
        List<Vehiculo> filtrados = new ArrayList<>();
        for (Vehiculo v : lista) {
            if (tipo.equals("1") && v instanceof VehiculoCarga) {
                filtrados.add(v);
            } else if (tipo.equals("2") && v instanceof VehiculoPasajero) {
                filtrados.add(v);
            }
        }

        if (filtrados.isEmpty()) {
            System.out.println("No hay vehiculos de ese tipo.");
            return;
        }

        String header;
        if (tipo.equals("1")) {
            header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s %-12s",
                    "Patente", "Marca", "Modelo", "Año", "Capacidad", "Precio", "Estado");
        } else {
            header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s %-12s",
                    "Patente", "Marca", "Modelo", "Año", "Pasajeros", "Precio", "Estado");
        }
        System.out.println("\n" + header);
        System.out.println("-------------------------------------------------------------------------------");

        for (Vehiculo v : filtrados) {
            if (tipo.equals("1")) {
                VehiculoCarga vc = (VehiculoCarga) v;
                System.out.printf("%-10s %-15s %-15s %-6d %-12.1f $%-10.0f %-12s\n",
                        vc.getPatente(), vc.getMarca(), vc.getModelo(),
                        vc.getAnio(), vc.getCapacidadCarga(), vc.getPrecioPorDia(), vc.getEstado());
            } else {
                VehiculoPasajero vp = (VehiculoPasajero) v;
                System.out.printf("%-10s %-15s %-15s %-6d %-12d $%-10.0f %-12s\n",
                        vp.getPatente(), vp.getMarca(), vp.getModelo(),
                        vp.getAnio(), vp.getMaxPasajeros(), vp.getPrecioPorDia(), vp.getEstado());
            }
        }
        System.out.println("-------------------------------------------------------------------------------");
    }

    private void mostrarHistorial() {
        File file = new File("historial_arriendos.csv");
        if (!file.exists()) {
            System.out.println("No hay historial registrado.");
            return;
        }
        System.out.println("\n========================================== Historial de Arriendos ========================================");
        
        String header = String.format("%-10s %-8s %-15s %-15s %-6s %-8s %-10s %-12s %-12s",
                "Patente", "Tipo", "Marca", "Modelo", "Año", "Dias", "Precio", "Total", "Fecha");
        System.out.println(header);
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            boolean hayDatos = false;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 9) continue;
                System.out.printf("%-10s %-8s %-15s %-15s %-6s %-8s $%-9s $%-11s %-12s\n",
                        datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7], datos[8]);
                hayDatos = true;
            }
            if (!hayDatos) System.out.println("Sin arriendos para mostrar.");
        } catch (Exception e) {
            System.out.println("Error al leer el historial: " + e.getMessage());
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }
}