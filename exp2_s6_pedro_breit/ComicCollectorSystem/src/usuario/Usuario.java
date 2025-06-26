package usuario;

import transacciones.Reserva;
import transacciones.Venta;

import java.util.ArrayList;

public class Usuario {
    private final String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String direccion;
    private String comuna;
    private String password;
    private final ArrayList<Reserva> reservas;
    private final ArrayList<Venta> compras;

    public Usuario(String rut, String nombre, String apellidoPaterno, String apellidoMaterno, String telefono, String direccion, String comuna, String password) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.direccion = direccion;
        this.comuna = comuna;
        this.password = password;
        this.reservas = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    public String getRut() { return rut; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    
    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public ArrayList<Reserva> getReservas() { return reservas; }
    
    public ArrayList<Venta> getCompras() { return compras; }
}