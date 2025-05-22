package exp1_s1_grupo1;
import java.util.*;

public class Exp1_S1_Grupo1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Cliente> clientesPorRut = new HashMap<>();
        Map<Long, Cliente> clientesPorCuenta = new HashMap<>();
        int opcion;

        System.out.println("Bienvenido a Bank Boston");
        do {
            System.out.println("\n===== MENÚ PRINCIPAL =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Ver datos de cliente");
            System.out.println("3. Depositar");
            System.out.println("4. Girar");
            System.out.println("5. Consultar saldo");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n--- Registro de Cliente ---");
                    System.out.print("RUT (incluyendo puntos y guion): ");
                    String rut = scanner.nextLine();

                    if (clientesPorRut.containsKey(rut)) {
                        System.out.println("Ya existe un cliente registrado con ese RUT.");
                        break;
                    }

                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Apellido Paterno: ");
                    String apellidoPaterno = scanner.nextLine();

                    System.out.print("Apellido Materno: ");
                    String apellidoMaterno = scanner.nextLine();

                    System.out.print("Domicilio: ");
                    String domicilio = scanner.nextLine();

                    System.out.print("Comuna: ");
                    String comuna = scanner.nextLine();

                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();

                    System.out.print("Número de cuenta corriente (9 dígitos): ");
                    long numeroCuenta = scanner.nextLong();
                    scanner.nextLine();

                    if (clientesPorCuenta.containsKey(numeroCuenta)) {
                        System.out.println("Ya existe un cliente con ese número de cuenta.");
                        break;
                    }

                    try {
                        Cliente cliente = new Cliente(nombre, rut, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono, numeroCuenta);
                        clientesPorRut.put(rut, cliente);
                        clientesPorCuenta.put(numeroCuenta, cliente);
                        System.out.println("Cliente registrado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error al registrar: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Ingrese RUT del cliente: ");
                    String rutConsulta = scanner.nextLine();
                    Cliente clienteConsulta = clientesPorRut.get(rutConsulta);
                    if (clienteConsulta != null) {
                        clienteConsulta.mostrarDatos();
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese número de cuenta: ");
                    long cuentaDeposito = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteDeposito = clientesPorCuenta.get(cuentaDeposito);
                    if (clienteDeposito != null) {
                        System.out.print("Ingrese monto a depositar: ");
                        double monto = scanner.nextDouble();
                        scanner.nextLine();
                        clienteDeposito.getCuenta().depositar(monto);
                    } else {
                        System.out.println("Cuenta no encontrada.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese número de cuenta: ");
                    long cuentaGiro = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteGiro = clientesPorCuenta.get(cuentaGiro);
                    if (clienteGiro != null) {
                        System.out.print("Ingrese monto a girar: ");
                        double monto = scanner.nextDouble();
                        scanner.nextLine();
                        clienteGiro.getCuenta().girar(monto);
                    } else {
                        System.out.println("Cuenta no encontrada.");
                    }
                    break;

                case 5:
                    System.out.print("Ingrese número de cuenta: ");
                    long cuentaSaldo = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteSaldo = clientesPorCuenta.get(cuentaSaldo);
                    if (clienteSaldo != null) {
                        clienteSaldo.getCuenta().consultarSaldo();
                    } else {
                        System.out.println("Cuenta no encontrada.");
                    }
                    break;

                case 6:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 6);

        scanner.close();
    }
}
