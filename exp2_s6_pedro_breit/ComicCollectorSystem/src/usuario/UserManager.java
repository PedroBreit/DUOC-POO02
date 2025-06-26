package usuario;

import comic.Comic;
import comic.ComicManager;
import transacciones.ReservaManager;
import transacciones.VentaManager;
import util.LoggerUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public final class UserManager {
    private final HashMap<String, Usuario> usuarios;
    private final String usuariosFile = "data/usuarios.txt";
    private final Scanner scanner;

    public UserManager() {
        this.usuarios = new HashMap<>();
        this.scanner = new Scanner(System.in);
        cargarUsuarios();
    }

    public void cargarUsuarios() {
        usuarios.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(usuariosFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length != 8) continue;
                Usuario u = new Usuario(datos[0], datos[1], datos[2], datos[3], datos[4], datos[5], datos[6], datos[7]);
                usuarios.put(u.getRut(), u);
            }
        } catch (FileNotFoundException e) {
            LoggerUtil.logError("Archivo de usuarios no encontrado al cargar", e);
            System.out.println("Archivo de usuarios no encontrado. Detalle registrado en error_log.txt");
        } catch (Exception e) {
            LoggerUtil.logError("Error al cargar usuarios", e);
            System.out.println("Error al cargar usuarios. Detalle registrado en error_log.txt");
        }
    }
    public void guardarUsuarios() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(usuariosFile))) {
            for (Usuario u : usuarios.values()) {
                pw.println(u.getRut() + ";" + u.getNombre() + ";" + u.getApellidoPaterno() + ";" +
                        u.getApellidoMaterno() + ";" + u.getTelefono() + ";" + u.getDireccion() + ";" +
                        u.getComuna() + ";" + u.getPassword());
            }
        } catch (Exception e) {
            LoggerUtil.logError("Error al guardar usuarios", e);
            System.out.println("Error al guardar usuarios. Detalle registrado en error_log.txt");
        }
    }

    public void crearCuenta() {
        String rut = "";
        while (true) {
            try {
                System.out.print("Ingrese su RUT (formato 12.345.678-9): ");
                rut = scanner.nextLine();
                if (!rutValido(rut)) throw new IllegalArgumentException("Formato de RUT incorrecto.");
                if (usuarios.containsKey(rut)) throw new Exception("El usuario ya existe.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.print("Ingrese su Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su Apellido paterno: ");
        String apellidoP = scanner.nextLine();
        System.out.print("Ingrese su Apellido materno: ");
        String apellidoM = scanner.nextLine();
        System.out.print("Ingrese su Teléfono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese su Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese su Comuna: ");
        String comuna = scanner.nextLine();
        System.out.print("Ingrese una Contraseña: ");
        String password = scanner.nextLine();

        Usuario nuevo = new Usuario(rut, nombre, apellidoP, apellidoM, telefono, direccion, comuna, password);
        usuarios.put(rut, nuevo);
        guardarUsuarios();
        System.out.println("Cuenta creada exitosamente.");
    }
    private void editarPerfil(Usuario usuario) {
            System.out.println("\nCampos editables:");
            System.out.println("1. Nombre");
            System.out.println("2. Apellido paterno");
            System.out.println("3. Apellido materno");
            System.out.println("4. Teléfono");
            System.out.println("5. Dirección");
            System.out.println("6. Comuna");
            System.out.print("Seleccione campo a editar: ");
            String op = scanner.nextLine();
            switch (op) {
                case "1" -> {
                    System.out.print("Nuevo nombre: ");
                    usuario.setNombre(scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Nuevo apellido paterno: ");
                    usuario.setApellidoPaterno(scanner.nextLine());
                }
                case "3" -> {
                    System.out.print("Nuevo apellido materno: ");
                    usuario.setApellidoMaterno(scanner.nextLine());
                }
                case "4" -> {
                    System.out.print("Nuevo teléfono: ");
                    usuario.setTelefono(scanner.nextLine());
                }
                case "5" -> {
                    System.out.print("Nueva dirección: ");
                    usuario.setDireccion(scanner.nextLine());
                }
                case "6" -> {
                    System.out.print("Nueva comuna: ");
                    usuario.setComuna(scanner.nextLine());
                }
                default -> System.out.println("Opción inválida.");
            }
            guardarUsuarios();
            System.out.println("Datos actualizados.");
        }
    private boolean eliminarCuenta(Usuario usuario) {
        System.out.println("¿Está seguro que desea eliminar la cuenta? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Ingrese contraseña para confirmar: ");
            String password = scanner.nextLine();
            if (usuario.getPassword().equals(password)) {
                usuarios.remove(usuario.getRut());
                eliminarReservasDeUsuario(usuario.getRut());
                guardarUsuarios();
                System.out.println("Cuenta eliminada exitosamente.");
                return true;
            } else {
                System.out.println("Contraseña incorrecta. No se eliminó la cuenta.");
            }
        }
        return false;
    }
    
    public void menuLogin(ComicManager comicManager, ReservaManager reservaManager, VentaManager ventaManager) {
        String rut = "";
        while (true) {
            try {
                System.out.print("Ingrese RUT (formato 12.345.678-9): ");
                rut = scanner.nextLine();
                if (!rutValido(rut)) throw new IllegalArgumentException("Formato de RUT incorrecto.");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.print("Ingrese Contraseña: ");
        String password = scanner.nextLine();
        Usuario usuario = usuarios.get(rut);
        if (usuario == null || !usuario.getPassword().equals(password)) {
            System.out.println("Usuario o clave incorrecta.");
            return;
        }
        menuUsuario(usuario, comicManager, reservaManager, ventaManager);
    }
    private void menuUsuario(Usuario usuario, ComicManager comicManager, ReservaManager reservaManager, VentaManager ventaManager) {
        boolean cerrarSesion = false;
        while (!cerrarSesion) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Buscar Comic");
            System.out.println("2. Comprar Comic");
            System.out.println("3. Ver Reservas");
            System.out.println("4. Ver Perfil");
            System.out.println("5. Cerrar sesión");
            System.out.print("Seleccione opción: ");
            String op = scanner.nextLine();

            switch (op) {
                case "1" -> menuBuscarComic(comicManager);
                case "2" -> comprarComic(usuario, comicManager, reservaManager, ventaManager);
                case "3" -> verReservas(usuario, reservaManager);
                case "4" -> {
                    boolean eliminado = menuPerfil(usuario);
                    if (eliminado) {
                        cerrarSesion = true;
                    }
                }
                case "5" -> cerrarSesion = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }
    private void menuBuscarComic(ComicManager comicManager) {
        while (true) {
            System.out.println("\nBuscar Comic por:");
            System.out.println("1. Nombre");
            System.out.println("2. Editorial");
            System.out.println("3. Autor");
            System.out.println("4. Ver lista completa");
            System.out.println("5. Atrás");
            System.out.print("Seleccione opción: ");
            String op = scanner.nextLine();

            switch (op) {
                case "1" -> {
                    System.out.print("Ingrese nombre: ");
                    for (Comic c : comicManager.buscarPorTitulo(scanner.nextLine())) {
                        System.out.println(c);
                    }
                }
                case "2" -> {
                    System.out.print("Ingrese editorial: ");
                    for (Comic c : comicManager.buscarPorEditorial(scanner.nextLine())) {
                        System.out.println(c);
                    }
                }
                case "3" -> {
                    System.out.print("Ingrese autor: ");
                    for (Comic c : comicManager.buscarPorAutor(scanner.nextLine())) {
                        System.out.println(c);
                    }
                }
                case "4" -> {
                    System.out.println("\n--- Lista de comics ---");
                    for (String idTitulo : comicManager.getIdYTitulosUnicosOrdenados()) {
                        System.out.println(idTitulo);                        
                    }
                }
                case "5" -> {
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }
    private boolean menuPerfil(Usuario usuario) {
        boolean atras = false;
        boolean eliminada = false;
        while (!atras) {
            System.out.println("\n--- PERFIL ---");
            System.out.println("RUT: " + usuario.getRut());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Apellido paterno: " + usuario.getApellidoPaterno());
            System.out.println("Apellido materno: " + usuario.getApellidoMaterno());
            System.out.println("Teléfono: " + usuario.getTelefono());
            System.out.println("Dirección: " + usuario.getDireccion());
            System.out.println("Comuna: " + usuario.getComuna());
            System.out.println("\n1. Editar perfil");
            System.out.println("2. Eliminar cuenta");
            System.out.println("3. Atrás");
            System.out.print("Seleccione opción: ");
            String op = scanner.nextLine();
            switch (op) {
                case "1" -> editarPerfil(usuario);
                case "2" -> {
                    eliminada = eliminarCuenta(usuario);
                    atras = true;
                }
                case "3" -> atras = true;
                default -> System.out.println("Opción inválida.");
            }
        }
        return eliminada;
    }
    
    private void comprarComic(Usuario usuario, ComicManager comicManager, ReservaManager reservaManager, VentaManager ventaManager) {
        System.out.print("Ingrese ID del comic a comprar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Comic comic = comicManager.buscarPorId(id);
        if (comic == null) {
            System.out.println("Comic no encontrado.");
            return;
        }
        if (comic.getStock() <= 0) {
            System.out.println("Actualmente no contamos con stock disponible.");
            System.out.print("¿Desea realizar una reserva para cuando se encuentre disponible? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                int posicionCola = reservaManager.hacerReserva(usuario, comic);
                System.out.println("Reserva realizada. Su número en la cola es: " + posicionCola +
                        ". Será notificado a su número: " + usuario.getTelefono());
            }
            return;
        }
        comic.setStock(comic.getStock() - 1);
        comicManager.guardarComics();
        ventaManager.registrarVenta(usuario, comic);
        System.out.println("Compra realizada con éxito.");
    }

    public void verReservas(Usuario usuario, transacciones.ReservaManager reservaManager) {
        System.out.println("\n--- Reservas ---");
        if (usuario.getReservas().isEmpty()) {
            System.out.println("No tiene reservas.");
            return;
        }
        int i = 1;
        for (transacciones.Reserva r : usuario.getReservas()) {
            System.out.println(i + ". " + r);
            i++;
        }
        System.out.print("¿Desea eliminar alguna reserva? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Ingrese el número de la reserva a eliminar: ");
            try {
                int num = Integer.parseInt(scanner.nextLine());
                if (num < 1 || num > usuario.getReservas().size()) {
                    System.out.println("Número inválido.");
                    return;
                }
                transacciones.Reserva rEliminar = usuario.getReservas().get(num - 1);
                if (reservaManager.eliminarReserva(rEliminar.getIdComic(), rEliminar.getPosicionCola())) {
                    usuario.getReservas().remove(rEliminar);
                    System.out.println("Reserva eliminada y cola actualizada.");
                } else {
                    System.out.println("No se pudo eliminar la reserva.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error al eliminar reserva: " + e.getMessage());
            }
        }
    }
    public void sincronizarReservasConUsuarios(transacciones.ReservaManager reservaManager) {
            for (var lista : reservaManager.getReservasTodas().values()) {
                for (transacciones.Reserva reserva : lista) {
                    Usuario usuario = getUsuario(reserva.getRutUsuario());
                    if (usuario != null && !usuario.getReservas().contains(reserva)) {
                        usuario.getReservas().add(reserva);
                    }
                }
            }
        }
    public void eliminarReservasDeUsuario(String rut) {
        String reservasFile = "data/reservas.txt";
        List<String> nuevasReservas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(reservasFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length != 4) continue;
                if (!datos[3].equals(rut)) { // Solo guarda las reservas de otros usuarios
                    nuevasReservas.add(linea);
                }
            }
        } catch (Exception e) {
            LoggerUtil.logError("Error al procesar reservas (lectura)", e);
            System.out.println("Error al procesar reservas. Detalle registrado en error_log.txt");
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(reservasFile))) {
            for (String r : nuevasReservas) {
                pw.println(r);
            }
        } catch (Exception e) {
            LoggerUtil.logError("Error al guardar reservas (escritura)", e);
            System.out.println("Error al guardar reservas. Detalle registrado en error_log.txt");
        }
    }
    
    public Usuario getUsuario(String rut) { return usuarios.get(rut); }
    
    public static boolean rutValido(String rut) {
        if (rut == null) return false;
        return rut.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$");
    }
}
