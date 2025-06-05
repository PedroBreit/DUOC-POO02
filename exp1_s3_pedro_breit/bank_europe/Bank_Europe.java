package bank_europe;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import bank_europe.cuentas.*;
import bank_europe.validadores.*;

public class Bank_Europe {
    public static void main(String[] args) {
        Map<String, Clientes> clientesPorRut = new HashMap<>();
        Map<Long, Clientes> clientesPorCuenta = new HashMap<>();
        int opcion;
        Scanner scanner = new Scanner(System.in);
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
            opcion = ValidarEntradas.leerEnteroValido(scanner, "Seleccione una opción: ");
            
            switch (opcion) {
                case 1:
                    System.out.println("\n===========================\n-   Registro de Cliente   -\n===========================");
                    System.out.print("RUT (incluyendo puntos y guion): ");
                    String rut = scanner.nextLine();
                    
                    if (!ValidarFormato.validarFormatoRut(rut)) {
                        System.out.println("RUT inválido. Formato esperado: XX.XXX.XXX-X");
                        break;
                    }
                    
                    Clientes cliente = clientesPorRut.get(rut);
                    boolean nuevoCliente = false;
                    
                    if(cliente == null){
                        String nombre = ValidarEntradas.leerTextoNoVacio(scanner, "Nombre: ");
                        String apellidoPaterno = ValidarEntradas.leerTextoNoVacio(scanner, "Apellido paterno: ");
                        String apellidoMaterno = ValidarEntradas.leerTextoNoVacio(scanner, "Apellido materno: ");
                        String domicilio = ValidarEntradas.leerTextoNoVacio(scanner, "Domicilio: ");
                        String comuna = ValidarEntradas.leerTextoNoVacio(scanner, "Comuna: ");
                        String telefono = ValidarEntradas.leerTextoNoVacio(scanner, "Teléfono: ");
                        cliente = new Clientes(nombre, rut, apellidoPaterno, apellidoMaterno, domicilio, comuna, telefono);
                        nuevoCliente = true;
                    }
                    
                    if (cliente.tieneCuenta()){
                        System.out.println("El cliente ya tiene una cuenta asociada.");
                        break;
                    }
                    System.out.println("Seleccione tipo de cuenta:");
                    System.out.println("1. Cuenta de Ahorro");
                    System.out.println("2. Cuenta Corriente");
                    System.out.println("3. Cuenta Digital");
                    int tipoCuenta = ValidarEntradas.leerEnteroValido(scanner, "Seleccione una opción: ");
                    String tipoStr = switch(tipoCuenta){
                        case 1 -> "Ahorro";
                        case 2 -> "Corriente";
                        case 3 -> "Digital";
                        default -> null;
                    };
                    
                    if(tipoStr == null){
                        System.out.println("Tipo de cuenta no válido,");
                        break;
                    }
                    
                    CuentaBancaria nuevaCuenta = switch (tipoCuenta) {
                        case 1 -> new CuentaAhorros();
                        case 2 -> new CuentaCorriente();
                        case 3 -> new CuentaDigital();
                        default -> null;
                    };
                    
                    if(nuevaCuenta == null){
                        System.out.println("Error al crear la cuenta");
                        break;
                    }
                    
                    cliente.agregarCuenta(nuevaCuenta);
                    clientesPorCuenta.put(nuevaCuenta.getNumeroCuenta(), cliente);
                    
                    if(nuevoCliente){
                        clientesPorRut.put(rut, cliente);
                    }
                    System.out.println("Cuenta " + tipoStr + " registrada exitosamente. \nNúmero de cuenta: " +  nuevaCuenta.getNumeroCuenta());
                    break;

                case 2:
                    System.out.print("\n-------------------------------------");
                    String rutConsulta = ValidarEntradas.leerTextoNoVacio(scanner, "\nIngrese RUT del cliente: ");
                    Clientes clienteConsulta = clientesPorRut.get(rutConsulta);
                    System.out.println("-------------------------------------");
                    
                    if (clienteConsulta != null) {
                        clienteConsulta.mostrarInformacionCliente();
                    } else {
                        System.out.println("Cliente no encontrado.");
                    }
                    break;

                case 3:
                    System.out.print("\n-----------------------------------");
                    long cuentaDeposito = ValidarEntradas.leerLongValido(scanner, "\nIngrese número de cuenta: ");
                    Clientes clienteDeposito = clientesPorCuenta.get(cuentaDeposito);
                    
                    if (clienteDeposito == null){
                        System.out.println("Cuenta no encontrada.");
                        break;
                    }
                    CuentaBancaria cuentaDep = clienteDeposito.getCuenta();
                    cuentaDep.consultarSaldo();
                    double monto = ValidarEntradas.leerDoubleValido(scanner, "\nIngrese monto a depositar: $");
                    cuentaDep.depositar(monto);
                    break;

                case 4:
                    System.out.print("\n-----------------------------------");
                    long cuentaGiro = ValidarEntradas.leerLongValido(scanner, "\nIngrese número de cuenta: ");
                    Clientes clienteGiro = clientesPorCuenta.get(cuentaGiro);
                    
                    if (clienteGiro == null) {
                        System.out.print("Cuenta no encontrada");
                        break;
                    }
                    
                    CuentaBancaria cuentaGr = clienteGiro.getCuenta();
                    cuentaGr.consultarSaldo();
                    double montoGiro = ValidarEntradas.leerDoubleValido(scanner, "\nIngrese monto a retirar: $");
                    cuentaGr.girar(montoGiro);
                    break;

                case 5:
                    System.out.print("\n-----------------------------------");
                    long cuentaSaldo = ValidarEntradas.leerLongValido(scanner, "\nIngrese número de cuenta: ");
                    Clientes clienteSaldo = clientesPorCuenta.get(cuentaSaldo);
                    if(clienteSaldo == null){
                        System.out.print("Cunta no encontrada");
                        break;
                    }
                    
                    CuentaBancaria cuentaSld = clienteSaldo.getCuenta();
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


