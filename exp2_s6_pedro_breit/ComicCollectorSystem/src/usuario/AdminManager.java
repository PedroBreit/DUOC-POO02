package usuario;

import comic.Comic;
import comic.ComicManager;
import transacciones.ReservaManager;
import transacciones.VentaManager;
import transacciones.Reserva;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class AdminManager {
    private final String adminPassword = "admin123"; //<--- CAMBIAR CONTRASEÑA
    private final Scanner scanner = new Scanner(System.in);

    public void menuAdministracion(ComicManager comicManager, ReservaManager reservaManager, VentaManager ventaManager, UserManager userManager) {
        System.out.print("Ingrese contraseña de administración: ");
        String pass = scanner.nextLine();
        if (!adminPassword.equals(pass)) {
            System.out.println("Contraseña incorrecta.");
            return;
        }
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ ADMINISTRACIÓN ---");
            System.out.println("1. Ver stock");
            System.out.println("2. Agregar Comic");
            System.out.println("3. Eliminar Comic");
            System.out.println("4. Historial de ventas");
            System.out.println("5. Ver reservas");
            System.out.println("6. Salir");
            System.out.print("Seleccione opción: ");
            String op = scanner.nextLine();
            switch (op) {
                case "1" -> verStock(comicManager);
                case "2" -> agregarComic(comicManager);
                case "3" -> eliminarComic(comicManager);
                case "4" -> ventaManager.verHistorialVentas();
                case "5" -> verReservas(reservaManager, userManager);
                case "6" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void verStock(ComicManager comicManager) {
        System.out.println("\nListado de Comics:");
        for (Comic c : comicManager.getComics()) {
            System.out.println(c);
        }
        System.out.println("Opciones:\n1. Editar stock\n2. Atrás\nSeleccione opción: ");
        String op = scanner.nextLine();
        if ("1".equals(op)) {
            System.out.print("Ingrese ID de Comic: ");
            int id = Integer.parseInt(scanner.nextLine());
            Comic c = comicManager.buscarPorId(id);
            if (c == null) {
                System.out.println("No encontrado.");
                return;
            }
            System.out.print("Cantidad a agregar: ");
            int cant = Integer.parseInt(scanner.nextLine());
            c.setStock(c.getStock() + cant);
            comicManager.guardarComics();
            System.out.println("Stock actualizado.");
        }
    }

    private void agregarComic(ComicManager comicManager) {
        try {
            System.out.print("Ingrese Título: ");
            String titulo = scanner.nextLine();
            System.out.print("Ingrese Autor: ");
            String autor = scanner.nextLine();
            System.out.print("Ingrese Editorial: ");
            String editorial = scanner.nextLine();
            System.out.print("Ingrese Año: ");
            int anio = Integer.parseInt(scanner.nextLine());
            System.out.print("Ingrese N° de serie: ");
            String serie = scanner.nextLine();
            System.out.print("Ingrese Género: ");
            String genero = scanner.nextLine();
            System.out.print("Ingrese Idioma: ");
            String idioma = scanner.nextLine();
            System.out.print("Ingrese Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());
            int nuevoId = comicManager.getComics().isEmpty() ? 1 : comicManager.getComics().get(comicManager.getComics().size() - 1).getId() + 1;
            Comic nuevo = new Comic(nuevoId, titulo, autor, editorial, anio, serie, genero, idioma, stock);
            comicManager.agregarComic(nuevo);
            System.out.println("Comic agregado.");
        } catch (NumberFormatException e) {
            System.out.println("Error al agregar comic: " + e.getMessage());
        }
    }
    
    private void eliminarComic(ComicManager comicManager) {
        System.out.print("Ingrese ID de Comic a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (comicManager.eliminarComic(id)) {
            System.out.println("Eliminado correctamente.");
        } else {
            System.out.println("No se encontró el comic.");
        }
    }
    
    private void verReservas(ReservaManager reservaManager, UserManager userManager) {
        System.out.println("\n--- Reservas por comic ---");
        HashMap<Integer, LinkedList<Reserva>> todas = reservaManager.getReservasTodas();
        if (todas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        for (var entry : todas.entrySet()) {
            int idComic = entry.getKey();
            LinkedList<transacciones.Reserva> cola = entry.getValue();
            if (!cola.isEmpty()) {
                String tituloComic = cola.peek().getTituloComic();
                System.out.println("\nID: " + idComic + " | Título: " + tituloComic);
                for (transacciones.Reserva reserva : cola) {
                    String nombre = "-";
                    String apellidoP = "-";
                    String apellidoM = "-";
                    String telefono = "-";
                    var usuario = userManager.getUsuario(reserva.getRutUsuario());
                    if (usuario != null) {
                        nombre = usuario.getNombre();
                        apellidoP = usuario.getApellidoPaterno();
                        apellidoM = usuario.getApellidoMaterno();
                        telefono = usuario.getTelefono();
                    }
                    System.out.println(" " + reserva.getPosicionCola()
                            + " | " + nombre + " " + apellidoP + " " + apellidoM
                            + " | Teléfono: " + telefono
                            + " | RUT: " + reserva.getRutUsuario());
                }
            }
        }
        System.out.print("\n¿Desea eliminar una reserva? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            try {
                System.out.print("Ingrese ID del cómic: ");
                int idComic = Integer.parseInt(scanner.nextLine());
                System.out.print("Ingrese posición en la cola a eliminar: ");
                int pos = Integer.parseInt(scanner.nextLine());
                if (reservaManager.eliminarReserva(idComic, pos)) {
                    userManager.sincronizarReservasConUsuarios(reservaManager);
                    System.out.println("Reserva eliminada y cola actualizada.");
                } else {
                    System.out.println("No se pudo eliminar la reserva.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error al eliminar reserva: " + e.getMessage());
            }
        }
    }
}