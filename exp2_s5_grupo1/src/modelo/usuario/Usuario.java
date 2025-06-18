package modelo.usuario;

import java.util.Objects;

public class Usuario implements Comparable<Usuario> {
    private String rut;
    private String clave;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String comuna;
    private String telefono;

    public Usuario(String rut, String clave, String nombre, String apellidos,
                   String direccion, String comuna, String telefono) {
        this.rut = rut;
        this.clave = clave;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.comuna = comuna;
        this.telefono = telefono;
    }

    public String getRut() { return rut; }
    public String getClave() { return clave; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDireccion() { return direccion; }
    public String getComuna() { return comuna; }
    public String getTelefono() { return telefono; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return rut.equals(usuario.rut);
    }

    @Override
    public int hashCode() { return Objects.hash(rut); }

    @Override
    public int compareTo(Usuario o) {
        return this.nombre.compareToIgnoreCase(o.nombre);
    }

    public String mostrarInfo() {
        return "RUT: " + rut + "\nNombre: " + nombre + " " + apellidos +
               "\nDirección: " + direccion + ", " + comuna +
               "\nTeléfono: " + telefono;
    }
}
