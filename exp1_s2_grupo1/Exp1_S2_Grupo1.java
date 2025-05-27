package exp1_s2_grupo1;

import java.util.*;

public class Exp1_S2_Grupo1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Cliente> clientesPorRut = new HashMap<>();
        Map<Long, Cliente> clientesPorCuenta = new HashMap<>();
        int opcion;
        System.out.println("Bienvenido a Bank Boston");
        do {
            System.out.println("\n===========================\n-     MENÚ PRINCIPAL     -\n===========================");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Ver datos de cliente");
            System.out.println("3. Depositar");
            System.out.println("4. Girar");
            System.out.println("5. Consultar saldo");
            System.out.println("6. Salir");
            System.out.println("===========================");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\n===========================\n-   Registro de Cliente   -\n===========================");
                    System.out.print("RUT (incluyendo puntos y guion): ");
                    String rut = scanner.nextLine();
                    Cliente cliente = clientesPorRut.get(rut);
                    boolean nuevoCliente = false;
                    
                    if(cliente == null){
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
                        cliente = new Cliente(nombre, rut, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
                        nuevoCliente = true;
                    }
                    
                    System.out.println("Seleccione tipo de cuenta:");
                    System.out.println("1. Cuenta de Ahorro");
                    System.out.println("2. Cuenta Corriente");
                    System.out.println("3. Cuenta de Crédito");
                    System.out.print("Opción: ");
                    int tipoCuenta = scanner.nextInt();
                    scanner.nextLine();

                    String tipoStr = switch(tipoCuenta){
                        case 1 -> "Ahorro";
                        case 2 -> "Corriente";
                        case 3 -> "Credito";
                        default -> null;
                    };
                    
                    if(tipoStr == null){
                        System.out.println("Tipo de cuenta no válido,");
                        break;
                    }
                    if(cliente.tieneCuenta(tipoStr)){
                        System.out.println("el cliente ya tuene una cuenta de tipo " + tipoStr + ".");
                        break;
                    }
                    
                    Cuenta nuevaCuenta = null;
                    switch (tipoCuenta) {
                        case 1:
                            System.out.print("Ingrese tasa de interés (%): ");
                            double interes = scanner.nextDouble();
                            scanner.nextLine();
                            nuevaCuenta = new Cuenta_Ahorro(interes/100);
                            break;
                        case 2:
                            System.out.print("Ingrese límite de sobregiro: $");
                            double sobregiro = scanner.nextDouble();
                            scanner.nextLine();
                            nuevaCuenta = new Cuenta_Corriente(sobregiro);
                            break;
                        case 3:
                            System.out.print("Ingrese límite de crédito: $");
                            double limiteCredito = scanner.nextDouble();
                            scanner.nextLine();
                            nuevaCuenta = new Cuenta_Credito(limiteCredito);
                            break;
                    }
                    
                    cliente.agregarCuenta(tipoStr, nuevaCuenta);
                    clientesPorCuenta.put(nuevaCuenta.getNumeroCuenta(), cliente);
                    if(nuevoCliente){
                        clientesPorRut.put(rut, cliente);
                    }
                    System.out.println("Cuenta " + tipoStr + "registrada exitosamente. \nNúmero de cuenta: " +  nuevaCuenta.getNumeroCuenta());
                    break;

                case 2:
                    System.out.print("\n-------------------------------------\nIngrese RUT del cliente: ");
                    String rutConsulta = scanner.nextLine();
                    Cliente clienteConsulta = clientesPorRut.get(rutConsulta);
                    System.out.println("-------------------------------------");
                    if (clienteConsulta != null) {
                        clienteConsulta.mostrarDatos();
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("\n-----------------------------------\nIngrese número de cuenta: ");
                    long cuentaDeposito = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteDeposito = clientesPorCuenta.get(cuentaDeposito);
                    if (clienteDeposito == null){
                        System.out.println("Cuenta no encontrada.");
                        break;
                    }
                    Cuenta cuentaDep = clienteDeposito.getCuentas().values().stream().filter(c -> c.getNumeroCuenta() == cuentaDeposito).findFirst().orElse(null);
                    if (cuentaDep == null){
                        System.out.println("No se encontro la cuenta asociada.");
                        break;
                    }
                    
                    System.out.print("Ingrese monto a depositar: $ ");
                    double monto =scanner.nextDouble();
                    scanner.nextLine();
                    cuentaDep.depositar(monto);
                    break;

                case 4:
                    System.out.print("\n-----------------------------------\nIngrese número de cuenta: ");
                    long cuentaGiro = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteGiro = clientesPorCuenta.get(cuentaGiro);
                    if (clienteGiro == null) {
                        System.out.print("Cunta no encontrada");
                        break;
                    }
                    
                    Cuenta cuentaGr = clienteGiro.getCuentas().values().stream().filter(c -> c.getNumeroCuenta() == cuentaGiro).findFirst().orElse(null);
                    if(cuentaGr == null){
                        System.out.println("No se encontro la cuenta asosciada");
                    }
                    
                    System.out.print("Ingrese monto a girar: $ ");
                    double montoGiro = scanner.nextDouble();
                    scanner.nextLine();
                    cuentaGr.girar(montoGiro);
                    break;

                case 5:
                    System.out.print("\n---------------------\nIngrese número de cuenta: ");
                    long cuentaSaldo = scanner.nextLong();
                    scanner.nextLine();
                    Cliente clienteSaldo = clientesPorCuenta.get(cuentaSaldo);
                    if(clienteSaldo == null){
                        System.out.print("Cunta no encontrada");
                        break;
                    }
                    
                    Cuenta cuentaSld = clienteSaldo.getCuentas().values().stream().filter(c -> c.getNumeroCuenta() == cuentaSaldo).findFirst().orElse(null);
                    if(cuentaSld == null){
                        System.out.println("No se encontro la cuenta asosciada");
                        break;
                    }
                    cuentaSld.consultarSaldo();
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
