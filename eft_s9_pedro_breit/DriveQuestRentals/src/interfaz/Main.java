package interfaz;

import datos.ArchivoVehiculos;
import datos.GestorVehiculos;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import modelo.Vehiculo;

public class Main {

    public static void main(String[] args) throws IOException {
        GestorVehiculos gestor = new GestorVehiculos();
        
        Thread cargarThread = new Thread(() -> {
           try {
               List<Vehiculo> lista = ArchivoVehiculos.cargarVehiculos("vehiculos.txt");
               for(Vehiculo v : lista) gestor.agregarVehiculo(v);
               System.out.println("Vehiculos cargados: " + gestor.listarVehiculos().size());
           } catch (IOException e) {
               System.out.println("No se pudieron cargar los vehículos: " + e.getMessage());
           }
        });
        cargarThread.start();
        try {
            cargarThread.join();
        } catch (InterruptedException e){
            System.out.println("Error en la carga concurrente.");
        }
        
        Usuario usuario = new Usuario(gestor);
        Administrador admin = new Administrador(gestor);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("\n===================================");
                System.out.println("   Bienvenido a DriveQuest Rentals ");
                System.out.println("===================================");
                System.out.println("1. Rentar vehículo");
                System.out.println("2. Administración");
                System.out.println("3. Salir");
                System.out.println("-----------------------------------");

                System.out.print("Seleccione una opción: ");

                String opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        System.out.println("Has seleccionado: Rentar vehículo\n");
                        usuario.menuUsuario(scanner);
                        break;
                    case "2":
                        System.out.println("Has seleccionado: Administración\n");
                        admin.menuAdministrador(scanner);
                        break;
                    case "3":
                        System.out.println("Gracias por usar DriveQuest Rentals. ¡Hasta luego!");
                        System.exit(0);
                    default:
                        System.out.println("Opción inválida. Intenta de nuevo.\n");
                }
            } catch (Exception e){
                    System.out.println("Error inesperado: " + e.getMessage());
            }
        }
    }
}