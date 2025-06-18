package app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import modelo.biblioteca.Biblioteca;
import modelo.biblioteca.Libro;
import modelo.usuario.RegistroUsuario;
import modelo.usuario.Usuario;
import servicio.ServiciosBiblioteca;

public class Main {
    
    private static final String ADMIN_PASS = "ADMIN";
    private static final String ARCHIVO_USUARIOS = "registro_usuario.txt";
    private static final String ARCHIVO_LIBROS = "libros_espanol_csv(100 Disponible).txt";

    public static void main(String[] args) {
        
        Biblioteca biblioteca = new Biblioteca();
        RegistroUsuario registroUsuarios = new RegistroUsuario();
        ServiciosBiblioteca servicios = new ServiciosBiblioteca(biblioteca, registroUsuarios);
        registroUsuarios.cargarUsuarios(ARCHIVO_USUARIOS);
        biblioteca.cargarLibros(ARCHIVO_LIBROS);
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Biblioteca DUOC UC ===");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Registrarse");
            System.out.println("3. Administrar");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    iniciarSesion(scanner, registroUsuarios, servicios, biblioteca);
                    break;
                case 2:
                    registrarUsuario(scanner, registroUsuarios);
                    registroUsuarios.guardarUsuarios(ARCHIVO_USUARIOS);
                    break;
                case 3:
                    administrar(scanner, biblioteca, servicios, registroUsuarios);
                    registroUsuarios.guardarUsuarios(ARCHIVO_USUARIOS);
                    biblioteca.guardarLibros(ARCHIVO_LIBROS);
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
        scanner.close();
        registroUsuarios.guardarUsuarios(ARCHIVO_USUARIOS);
        biblioteca.guardarLibros(ARCHIVO_LIBROS);
    }
        
    static void iniciarSesion(Scanner scanner, RegistroUsuario reg, ServiciosBiblioteca servicios, Biblioteca biblioteca) {
        System.out.print("Ingrese RUT: ");
        String rut = scanner.nextLine().trim();
        System.out.print("Ingrese clave: ");
        String clave = scanner.nextLine().trim();
        if (!reg.validarLogin(rut, clave)) {
            System.out.println("Credenciales incorrectas.");
            return;
        }
        Usuario usuario = reg.buscarUsuarioPorRut(rut);
        int opcion;
        do {
            System.out.println("\n--- Menú Usuario ---");
            System.out.println("1. Ver perfil");
            System.out.println("2. Ver arriendos");
            System.out.println("3. Arrendar libro");
            System.out.println("4. Buscar libro/autor");
            System.out.println("5. Devolver libro");
            System.out.println("6. Salir");
            System.out.print("Seleccione opción: ");
            opcion = leerEntero(scanner);
            switch (opcion) {
                case 1:
                    System.out.println("--- Perfil ---");
                    System.out.println(usuario.mostrarInfo());
                    break;
                case 2:
                    ArrayList<Libro> misArriendos = servicios.obtenerArriendosUsuario(rut);
                    if (misArriendos.isEmpty()) {
                        System.out.println("No tienes libros arrendados.");
                    } else {
                        System.out.println("Tus arriendos:");
                        for (Libro libro : misArriendos) {
                            System.out.printf("%s - %s (%s)\n", libro.getTitulo(), libro.getAutor(), libro.getIsbn());
                        }
                    }
                    break;
                case 3:
                    System.out.print("ISBN del libro a arrendar: ");
                    String isbnA = scanner.nextLine().trim();
                    if (servicios.arrendarLibro(isbnA, rut)) {
                        System.out.println("Libro arrendado exitosamente.");
                        biblioteca.guardarLibros(ARCHIVO_LIBROS);
                    } else {
                        System.out.println("No se pudo arrendar el libro (verifique disponibilidad).");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese título o autor a buscar: ");
                    String consulta = scanner.nextLine().trim();
                    ArrayList<Libro> encontrados = biblioteca.buscarPorTituloOAutor(consulta);
                    if (encontrados.isEmpty()) {
                        System.out.println("No se encontraron coincidencias.");
                    } else {
                        for (Libro libro : encontrados) {
                            System.out.printf("%s - %s (%s) %s\n", libro.getTitulo(), libro.getAutor(), libro.getIsbn(),
                                    libro.isPrestado() ? "[Prestado]" : "");
                        }
                    }
                    break;
                case 5:
                    System.out.print("ISBN del libro a devolver: ");
                    String isbnDev = scanner.nextLine().trim();
                    if (servicios.devolverLibro(isbnDev, rut)) {
                        System.out.println("Libro devuelto correctamente.");
                        biblioteca.guardarLibros(ARCHIVO_LIBROS);
                    } else {
                        System.out.println("No tienes ese libro arrendado.");
                    }
                    break;
                case 6:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 6);
    }

    static void registrarUsuario(Scanner scanner, RegistroUsuario reg) {
        System.out.print("RUT (sin puntos ni guion): ");
        String rut = scanner.nextLine().trim();
        if (reg.buscarUsuarioPorRut(rut) != null) {
            System.out.println("Ya existe un usuario con ese RUT.");
            return;
        }
        System.out.print("Clave: ");
        String clave = scanner.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine().trim();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine().trim();
        System.out.print("Comuna: ");
        String comuna = scanner.nextLine().trim();
        System.out.print("Teléfono (solo números): ");
        String telefono = scanner.nextLine().trim();

        Usuario nuevo = new Usuario(rut, clave, nombre, apellidos, direccion, comuna, telefono);
        reg.agregarUsuario(nuevo);
        System.out.println("Usuario registrado:");
        System.out.println(nuevo.mostrarInfo());
    }

    static void administrar(Scanner scanner, Biblioteca biblioteca, ServiciosBiblioteca servicios, RegistroUsuario reg) {
        System.out.print("Ingrese clave de administrador: ");
        String pass = scanner.nextLine().trim();
        if (!ADMIN_PASS.equals(pass)) {
            System.out.println("Clave incorrecta.");
            return;
        }
        int opcion;
        do {
            System.out.println("\n--- Menú Administrador ---");
            System.out.println("1. Agregar libro");
            System.out.println("2. Eliminar libro");
            System.out.println("3. Ver libros prestados y quién los tiene");
            System.out.println("4. Salir");
            System.out.print("Seleccione opción: ");
            opcion = leerEntero(scanner);
            switch (opcion) {
                case 1:
                    System.out.print("Título: ");
                    String titulo = scanner.nextLine().trim();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine().trim();
                    System.out.print("Genero: ");
                    String genero = scanner.nextLine().trim();
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine().trim();
                    if (biblioteca.agregarLibro(new Libro(titulo, autor, genero, isbn, false))) {
                        System.out.println("Libro agregado correctamente.");
                        biblioteca.guardarLibros(ARCHIVO_LIBROS);
                    } else {
                        System.out.println("Ya existe un libro con ese ISBN.");
                    }
                    break;
                case 2:
                    System.out.print("ISBN del libro a eliminar: ");
                    String isbnDel = scanner.nextLine().trim();
                    if (biblioteca.eliminarLibro(isbnDel)) {
                        System.out.println("Libro eliminado.");
                        biblioteca.guardarLibros(ARCHIVO_LIBROS);
                    } else {
                        System.out.println("No se pudo eliminar (puede estar prestado o no existir).");
                    }
                    break;
                case 3:
                    System.out.println("--- Libros prestados ---");
                    HashMap<String, String> prestamos = servicios.getPrestamos();
                    if (prestamos.isEmpty()) {
                        System.out.println("No hay libros prestados actualmente.");
                    } else {
                        for (String iSBN : prestamos.keySet()) {
                            Libro libro = biblioteca.buscarLibroPorIsbn(iSBN);
                            Usuario usuario = reg.buscarUsuarioPorRut(prestamos.get(iSBN));
                            System.out.printf("%s - %s (%s) => %s %s (RUT: %s)\n",
                                    libro.getTitulo(), libro.getAutor(), libro.getIsbn(),
                                    usuario.getNombre(), usuario.getApellidos(), usuario.getRut());
                        }
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del menú administrador...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 4);
    }

    static int leerEntero(Scanner scanner) {
        int valor = -1;
        try {
            valor = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            valor = -1;
        }
        return valor;
    }
}
