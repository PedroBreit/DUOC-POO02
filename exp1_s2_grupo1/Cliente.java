package exp1_s2_grupo1;

import java.util.HashMap;
import java.util.Map;

public class Cliente {
    private String nombre;
    private String rut;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private Map<String, Cuenta> cuentas;

    public Cliente(String nombre, String rut, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono) {
        if (!Validadores.validarFormatoRut(rut)) {
            throw new IllegalArgumentException("El Rut no es valido.");
        }

        this.nombre = nombre;
        this.rut = rut;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.domicilio = domicilio;
        this.comuna = comuna;
        this.telefono = telefono;
        this.cuentas = new HashMap<>();
    }
    
    public void agregarCuenta(String tipo , Cuenta cuenta){
        cuentas.put(tipo, cuenta);
    }
     public boolean tieneCuenta(String tipo){
         return cuentas.containsKey(tipo);
     }

    public Cuenta getCuenta(String tipo) {
        return cuentas.get(tipo);
    }
    
    public String getRut(){
        return rut;
    }

    public Map<String, Cuenta> getCuentas(){
        return cuentas;
    }
    
    public void mostrarDatos() {
        System.out.println("\n=============================\n-     Datos del Cliente     -\n=============================");
        System.out.println("Nombre: " + nombre);
        System.out.println("RUT: " + rut);
        System.out.println("Apellido Paterno: " + apellidoPaterno);
        System.out.println("Apellido Materno: " + apellidoMaterno);
        System.out.println("Domicilio: " + domicilio);
        System.out.println("Comuna: " + comuna);
        System.out.println("Teléfono: " + telefono);
        System.out.println("-----------------------------\n-    Cuentas Asociadas      -\n-----------------------------");
        if (cuentas.isEmpty()) {
            System.out.println("Este cliente no tiene cuentas aún.");
        } else {
            for (Map.Entry<String, Cuenta> entry : cuentas.entrySet()) {
                System.out.println("Tipo de cuenta: " + entry.getKey());
                System.out.println(entry.getValue());
            }
        }
    }
    
}
