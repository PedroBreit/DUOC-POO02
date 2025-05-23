package exp1_s1_grupo1;

public class Cliente {
    private String nombre;
    private String rut;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String domicilio;
    private String comuna;
    private String telefono;
    private Cuenta cuenta;

    public Cliente(String nombre, String rut, String apellidoPaterno, String apellidoMaterno, String domicilio, String comuna, String telefono, Cuenta cuenta) {
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
        this.cuenta = cuenta; // Se inicia con saldo 0
    }

    public String getRut() {
        return rut;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public long getNumeroCuenta() {
        return cuenta.getNumeroCuenta();
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
        System.out.println(cuenta);
    }
    
}
