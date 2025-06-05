package bank_europe;

import bank_europe.validadores.ValidarFormato;
import bank_europe.cuentas.CuentaBancaria;
import bank_europe.interfaces.InfoCliente;

public class Clientes implements InfoCliente {
    private String nombre;
    private String rut;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private CuentaBancaria cuenta;

    public Clientes(String nombre, String rut, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        this.nombre = nombre;
        this.rut = rut;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuenta = cuenta;
    }
    
    public void agregarCuenta(CuentaBancaria cuenta){
        this.cuenta = cuenta;
    }
     public boolean tieneCuenta(){
         return cuenta != null;
     }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }
    
    public String getRut(){
        return rut;
    }
    
    @Override
    public void mostrarInformacionCliente() {
        System.out.println("\n=============================\n-     Datos del Cliente     -\n=============================");
        System.out.println("Nombre: " + nombre);
        System.out.println("RUT: " + rut);
        System.out.println("Apellido paterno: " + apellidoPaterno);
        System.out.println("Apellido materno: " + apellidoMaterno);
        System.out.println("Domicilio: " + domicilio);
        System.out.println("Comuna: " + comuna);
        System.out.println("Teléfono: " + telefono);
        System.out.println("-----------------------------\n-     Cuenta Asociada       -\n-----------------------------");
        if (cuenta == null) {
            System.out.println("Este cliente no tiene cuentas aún.");
        } else {
            System.out.println(cuenta + "\n-----------------------------");
            }
        }
    }

