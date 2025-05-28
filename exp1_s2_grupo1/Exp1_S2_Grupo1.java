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
            System.out.println("3. Depositar / Pagar Credito");
            System.out.println("4. Girar");
            System.out.println("5. Consultar saldo");
            System.out.println("6. Salir");
            System.out.println("===========================");
            opcion = Validadores.leerEnteroValido(scanner, "Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    System.out.println("\n===========================\n-   Registro de Cliente   -\n===========================");
                    System.out.print("RUT (incluyendo puntos y guion): ");
                    String rut = scanner.nextLine();
                    Cliente cliente = clientesPorRut.get(rut);
                    boolean nuevoCliente = false;
                    
                    if(cliente == null){
                        String nombre = Validadores.leerTextoNoVacio(scanner, "Nombre: ");
                        String apellidoPaterno = Validadores.leerTextoNoVacio(scanner, "Apellido paterno: ");
                        String apellidoMaterno = Validadores.leerTextoNoVacio(scanner, "Apellido materno: ");
                        String domicilio = Validadores.leerTextoNoVacio(scanner, "Domicilio: ");
                        String comuna = Validadores.leerTextoNoVacio(scanner, "Comuna: ");
                        String telefono = Validadores.leerTextoNoVacio(scanner, "Teléfono: ");
                        cliente = new Cliente(nombre, rut, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
                        nuevoCliente = true;
                    }
                    
                    System.out.println("Seleccione tipo de cuenta:");
                    System.out.println("1. Cuenta de Ahorro");
                    System.out.println("2. Cuenta Corriente");
                    System.out.println("3. Cuenta de Crédito");
                    int tipoCuenta = Validadores.leerEnteroValido(scanner, "Seleccione una opción: ");
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
                            double interes = Validadores.leerDoubleValido(scanner, "Ingrese tasa de interés anual (%): ");
                            nuevaCuenta = new Cuenta_Ahorro(interes/100);
                            break;
                        case 2:
                            double sobregiro = Validadores.leerDoubleValido(scanner, "Ingrese límite de sobregiro: $");
                            nuevaCuenta = new Cuenta_Corriente(sobregiro);
                            break;
                        case 3:
                            double limiteCredito = Validadores.leerDoubleValido(scanner, "Ingrese límite de crédito: $");
                            nuevaCuenta = new Cuenta_Credito(limiteCredito);
                            break;
                    }
                    
                    cliente.agregarCuenta(tipoStr, nuevaCuenta);
                    clientesPorCuenta.put(nuevaCuenta.getNumeroCuenta(), cliente);
                    if(nuevoCliente){
                        clientesPorRut.put(rut, cliente);
                    }
                    System.out.println("Cuenta " + tipoStr + " registrada exitosamente. \nNúmero de cuenta: " +  nuevaCuenta.getNumeroCuenta());
                    break;

                case 2:
                    System.out.print("\n-------------------------------------");
                    String rutConsulta = Validadores.leerTextoNoVacio(scanner, "Ingrese RUT del cliente: ");
                    Cliente clienteConsulta = clientesPorRut.get(rutConsulta);
                    System.out.println("-------------------------------------");
                    if (clienteConsulta != null) {
                        clienteConsulta.mostrarDatos();
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("\n-----------------------------------");
                    long cuentaDeposito = Validadores.leerLongValido(scanner, "Ingrese número de cuenta: ");
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
                    cuentaDep.consultarSaldo();
                    double monto = Validadores.leerDoubleValido(scanner, "Ingrese monto a depositar: $");
                    cuentaDep.depositar(monto);
                    break;

                case 4:
                    System.out.print("\n-----------------------------------");
                    long cuentaGiro = Validadores.leerLongValido(scanner, "Ingrese número de cuenta: ");
                    Cliente clienteGiro = clientesPorCuenta.get(cuentaGiro);
                    if (clienteGiro == null) {
                        System.out.print("Cunta no encontrada");
                        break;
                    }
                    
                    Cuenta cuentaGr = clienteGiro.getCuentas().values().stream().filter(c -> c.getNumeroCuenta() == cuentaGiro).findFirst().orElse(null);
                    if(cuentaGr == null){
                        System.out.println("No se encontro la cuenta asosciada");
                    }
                    cuentaGr.consultarSaldo();
                    double montoGiro = Validadores.leerDoubleValido(scanner, "Ingrese monto a retirar: $");
                    cuentaGr.girar(montoGiro);
                    break;

                case 5:
                    System.out.print("\n-----------------------------------");
                    long cuentaSaldo = Validadores.leerLongValido(scanner, "Ingrese número de cuenta: ");
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
