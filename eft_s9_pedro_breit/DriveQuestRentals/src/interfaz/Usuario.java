package interfaz;

import datos.*;
import java.io.IOException;
import java.util.*;
import modelo.*;

public class Usuario {
    private final GestorVehiculos gestor;
    private Vehiculo carrito;
    private int diasCarrito = 0;

    public Usuario(GestorVehiculos gestor) {
        this.gestor = gestor;
        this.carrito = null;
    }

    public void menuUsuario(Scanner scanner) throws IOException {
        while (true) {
            System.out.println("\n===== Menu Usuario =====");
            System.out.println("1. Listar vehiculos");
            System.out.println("2. Rentar vehiculo");
            System.out.println("3. Carrito");
            System.out.println("4. Devolver vehiculo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> listarVehiculos(scanner);
                case "2" -> rentarVehiculo(scanner);
                case "3" -> verCarrito(scanner);
                case "4" -> devolverVehiculo(scanner);
                case "5" -> {
                    return;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void listarVehiculos(Scanner scanner) {
        try {
            System.out.println("\nFiltrar por tipo:");
            System.out.println("1. Carga");
            System.out.println("2. Pasajero");
            System.out.print("Seleccione tipo: ");
            String tipo = scanner.nextLine().trim();

            List<Vehiculo> listaFiltrada = new ArrayList<>();

            if (tipo.equals("1")) {
                System.out.print("Ingrese capacidad minima de carga (en toneladas): ");
                String inputCap = scanner.nextLine();
                double capacidad;
                try {
                    capacidad = Double.parseDouble(inputCap);
                } catch (NumberFormatException e) {
                    System.out.println("Valor invalido.");
                    return;
                }
                for (Vehiculo v : gestor.listarVehiculos()) {
                    if (v instanceof VehiculoCarga
                            && v.getEstado().equals("Disponible")
                            && ((VehiculoCarga) v).getCapacidadCarga() >= capacidad) {
                        listaFiltrada.add(v);
                    }
                }
            } else if (tipo.equals("2")) {
                System.out.print("Ingrese cantidad minima de pasajeros: ");
                String inputPas = scanner.nextLine();
                int pasajeros;
                try {
                    pasajeros = Integer.parseInt(inputPas);
                } catch (NumberFormatException e) {
                    System.out.println("Valor invalido.");
                    return;
                }
                for (Vehiculo v : gestor.listarVehiculos()) {
                    if (v instanceof VehiculoPasajero
                            && v.getEstado().equals("Disponible")
                            && ((VehiculoPasajero) v).getMaxPasajeros() >= pasajeros) {
                        listaFiltrada.add(v);
                    }
                }
            } else {
                System.out.println("Tipo no valido.");
                return;
            }

            if (listaFiltrada.isEmpty()) {
                System.out.println("No se encontraron vehiculos disponibles con ese filtro.");
            } else {
                // Encabezado de tabla
                String header;
                if (tipo.equals("1")) {
                    header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s",
                            "Patente", "Marca", "Modelo", "Año", "Capacidad", "Precio");
                } else {
                    header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s",
                            "Patente", "Marca", "Modelo", "Año", "Pasajeros", "Precio");
                }
                System.out.println("\n" + header);
                System.out.println("-----------------------------------------------------------------------");

                for (Vehiculo v : listaFiltrada) {
                    if (tipo.equals("1")) {
                        VehiculoCarga vc = (VehiculoCarga) v;
                        System.out.printf("%-10s %-15s %-15s %-6d %-12.1f $%-10.0f\n",
                                vc.getPatente(), vc.getMarca(), vc.getModelo(),
                                vc.getAnio(), vc.getCapacidadCarga(), vc.getPrecioPorDia());
                    } else {
                        VehiculoPasajero vp = (VehiculoPasajero) v;
                        System.out.printf("%-10s %-15s %-15s %-6d %-12d $%-10.0f\n",
                                vp.getPatente(), vp.getMarca(), vp.getModelo(),
                                vp.getAnio(), vp.getMaxPasajeros(), vp.getPrecioPorDia());
                    }
                }
                System.out.println("-----------------------------------------------------------------------");
            }
        } catch (Exception e){
            System.out.println("Error al listar vehículos: " + e.getMessage());
        }
    }

    private void rentarVehiculo(Scanner scanner) {
        try {
            System.out.print("\nIngrese la patente del vehiculo a rentar: ");
            String patente = scanner.nextLine().toUpperCase();
            Vehiculo seleccionado = gestor.getVehiculoPorPatente(patente);
            if (seleccionado == null || !seleccionado.getEstado().equals("Disponible")) {
                System.out.println("No existe un vehiculo disponible con esa patente.");
                return;
            }
            // TABLA con los datos
            String header;
            if (seleccionado instanceof VehiculoCarga) {
                header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s",
                        "Patente", "Marca", "Modelo", "Año", "Capacidad", "Precio");
                System.out.println("\n" + header);
                System.out.println("-----------------------------------------------------------------------");
                VehiculoCarga vc = (VehiculoCarga) seleccionado;
                System.out.printf("%-10s %-15s %-15s %-6d %-12.1f $%-10.0f\n",
                        vc.getPatente(), vc.getMarca(), vc.getModelo(),
                        vc.getAnio(), vc.getCapacidadCarga(), vc.getPrecioPorDia());
            } else {
                header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s",
                        "Patente", "Marca", "Modelo", "Año", "Pasajeros", "Precio");
                System.out.println("\n" + header);
                System.out.println("-----------------------------------------------------------------------");
                VehiculoPasajero vp = (VehiculoPasajero) seleccionado;
                System.out.printf("%-10s %-15s %-15s %-6d %-12d $%-10.0f\n",
                        vp.getPatente(), vp.getMarca(), vp.getModelo(),
                        vp.getAnio(), vp.getMaxPasajeros(), vp.getPrecioPorDia());
            }
            System.out.println("-----------------------------------------------------------------------");

            System.out.print("Desea agregar este vehiculo al carrito? (s/n): ");
            String conf = scanner.nextLine();
            if (conf.equalsIgnoreCase("s")) {
                int dias = 0;
                while (dias < 1) {
                    System.out.print("Cuantos dias desea arrendar este vehiculo?: ");
                    try {
                        dias = Integer.parseInt(scanner.nextLine());
                        if (dias < 1) System.out.println("Debe ser al menos 1 dia.");
                    } catch (Exception e) {
                        System.out.println("Ingrese un numero valido.");
                    }
                }
                this.carrito = seleccionado;
                this.diasCarrito = dias;
                System.out.println("Vehiculo agregado al carrito por " + dias + " dias.");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (Exception e) {
            System.out.println("Error al rentar vehiculo: " + e.getMessage());
        }
    }   

    private void verCarrito(Scanner scanner) {
        if (carrito == null) {
            System.out.println("El carrito esta vacio.");
            return;
        }

        System.out.println("\n=== Carrito ===");
        String header;
        if (carrito instanceof VehiculoCarga) {
            header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s %-10s",
                    "Patente", "Marca", "Modelo", "Año", "Capacidad", "Precio", "Dias");
            System.out.println(header);
            System.out.println("-------------------------------------------------------------------------------");
            VehiculoCarga vc = (VehiculoCarga) carrito;
            System.out.printf("%-10s %-15s %-15s %-6d %-12.1f $%-9.0f %-10d\n",
                    vc.getPatente(), vc.getMarca(), vc.getModelo(),
                    vc.getAnio(), vc.getCapacidadCarga(), vc.getPrecioPorDia(), diasCarrito);
        } else {
            header = String.format("%-10s %-15s %-15s %-6s %-12s %-10s %-10s",
                    "Patente", "Marca", "Modelo", "Año", "Pasajeros", "Precio", "Dias");
            System.out.println(header);
            System.out.println("-------------------------------------------------------------------------------");
            VehiculoPasajero vp = (VehiculoPasajero) carrito;
            System.out.printf("%-10s %-15s %-15s %-6d %-12d $%-9.0f %-10d\n",
                    vp.getPatente(), vp.getMarca(), vp.getModelo(),
                    vp.getAnio(), vp.getMaxPasajeros(), vp.getPrecioPorDia(), diasCarrito);
        }
        System.out.println("-------------------------------------------------------------------------------");

        double subtotal = diasCarrito * carrito.getPrecioPorDia();
        double iva = 0;
        double descuento = 0;
        double total = 0;

        if (carrito instanceof VehiculoCarga) {
            iva = subtotal * negocio.Facturable.IVA;
            descuento = subtotal * negocio.Facturable.DESCUENTO_CARGA;
        } else if (carrito instanceof VehiculoPasajero) {
            iva = subtotal * negocio.Facturable.IVA;
            descuento = subtotal * negocio.Facturable.DESCUENTO_PASAJEROS;
        }
        total = subtotal + iva - descuento;
        System.out.println("\nSubtotal: $" + subtotal);
        System.out.println("IVA (19%): $" + iva);
        System.out.println("Descuento: -$" + descuento);
        System.out.println("Total a pagar: $" + total);

        System.out.println("\n1. Confirmar renta");
        System.out.println("2. Borrar carrito");
        System.out.println("3. Volver");
        System.out.print("Seleccione una opcion: ");
        String opcion = scanner.nextLine();
        switch (opcion) {
            case "1":
                System.out.println("Vehiculo rentado exitosamente.");
                System.out.println("\n===================== Boleta =====================");
                String headerB = String.format("%-15s %-15s %-10s %-10s", "Marca", "Modelo", "Patente", "Dias");
                System.out.println(headerB);
                System.out.println("--------------------------------------------------");
                System.out.printf("%-15s %-15s %-10s %-10d\n", carrito.getMarca(), carrito.getModelo(), carrito.getPatente(), diasCarrito);
                System.out.println("--------------------------------------------------");
                System.out.printf("%-25s %15.0f\n", "Subtotal:", subtotal);
                System.out.printf("%-25s %15.0f\n", "IVA (19%):", iva);
                System.out.printf("%-25s %15.0f\n", "Descuento:", -descuento);
                System.out.printf("%-25s %15.0f\n", "Total a pagar:", total);
                RegistroHistorial.guardarArriendo(carrito, diasCarrito, total);
                carrito.setEstado("Rentado");
                try {
                    ArchivoVehiculos.guardarVehiculos(gestor.listarVehiculos(), "vehiculos.txt");
                } catch (Exception e) {
                    System.out.println("Error al actualizar archivo: " + e.getMessage());
                }
                carrito = null;
                diasCarrito = 0;
                break;
            case "2":
                carrito = null;
                diasCarrito = 0;
                System.out.println("Carrito eliminado.");
                break;
            case "3":
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }
    
    private void devolverVehiculo (Scanner scanner) {
        try {
            System.out.print("\nIngrese la patente del vehiculo a devolver: ");
            String patente = scanner.nextLine().toUpperCase();
            Vehiculo v = gestor.getVehiculoPorPatente(patente);
            if (v == null) {
                System.out.println("No existe un vehiculo con esa patente.");
                return;
            }
            if (!v.getEstado().equals("Rentado")) {
                System.out.println("Ese vehiculo no esta rentado.");
                return;
            }
            v.setEstado("Disponible");
            try {
                ArchivoVehiculos.guardarVehiculos(gestor.listarVehiculos(), "vehiculos.txt");
            } catch (IOException e) {
                System.out.println("Error actualizando archivo: " + e.getMessage());
            }
            System.out.println("Vehiculo devuelto exitosamente. Estado actualizado a Disponible.");
        } catch (Exception e){
            System.out.println("Error en la devolución: " + e.getMessage());
        }
    }
}