package modelo.usuario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.TreeSet;

public class RegistroUsuario {
    private HashMap<String, Usuario> usuarios;

    public RegistroUsuario() {
        usuarios = new HashMap<>();
    }

    public boolean agregarUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getRut())) return false;
        usuarios.put(usuario.getRut(), usuario);
        return true;
    }

    public Usuario buscarUsuarioPorRut(String rut) {
        return usuarios.get(rut);
    }

    public boolean validarLogin(String rut, String clave) {
        Usuario usuario = usuarios.get(rut);
        return usuario != null && usuario.getClave().equals(clave);
    }

    public TreeSet<Usuario> getUsuariosOrdenados() {
        return new TreeSet<>(usuarios.values());
    }

    public void cargarUsuarios(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(", ");
                String rut = partes[0].split(": ")[1];
                String nombre = partes[1].split(": ")[1];
                String apellidos = partes[2].split(": ")[1];
                String direccion = partes[3].split(": ")[1];
                String comuna = partes[4].split(": ")[1];
                String telefono = partes[5].split(": ")[1].replace("+569 ", "");
                String clave = partes[6].split(": ")[1];
                Usuario usuario = new Usuario(rut, clave, nombre, apellidos, direccion, comuna, telefono);
                agregarUsuario(usuario);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    public void guardarUsuarios(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Usuario usuario : usuarios.values()) {
                pw.printf("Rut: %s, Nombre: %s, Apellido Paterno: %s, Domicilio: %s, Comuna: %s, Telefono: +569 %s, Clave Acceso: %s\n",
                          usuario.getRut(), usuario.getNombre(), usuario.getApellidos(), usuario.getDireccion(),
                          usuario.getComuna(), usuario.getTelefono(), usuario.getClave());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
}
