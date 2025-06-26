package comiccollectorsystem;

import comic.ComicManager;
import usuario.UserManager;
import usuario.AdminManager;
import transacciones.ReservaManager;
import transacciones.VentaManager;

import java.util.Scanner;

public class Menu {
    private final ComicManager comicManager;
    private final UserManager userManager;
    private final AdminManager adminManager;
    private final ReservaManager reservaManager;
    private final VentaManager ventaManager;
    private final Scanner scanner;

    public Menu() {
        this.comicManager = new ComicManager();
        this.userManager = new UserManager();
        this.adminManager = new AdminManager();
        this.reservaManager = new ReservaManager();
        this.ventaManager = new VentaManager();
        this.scanner = new Scanner(System.in);
        this.userManager.sincronizarReservasConUsuarios(this.reservaManager);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\nBienvenido a ComicCollectorSystem");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Crear cuenta");
            System.out.println("3. Administración");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1" -> userManager.menuLogin(comicManager, reservaManager, ventaManager);
                case "2" -> userManager.crearCuenta();
                case "3" -> adminManager.menuAdministracion(comicManager, reservaManager, ventaManager, userManager);
                case "4" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
        System.out.println("Gracias por usar ComicCollectorSystem.");
    }
}
