package servicio;

import java.util.HashMap;
import java.util.ArrayList;
import modelo.biblioteca.Biblioteca;
import modelo.biblioteca.Libro;
import modelo.usuario.RegistroUsuario;
import modelo.usuario.Usuario;

public class ServiciosBiblioteca {
    private Biblioteca biblioteca;
    private RegistroUsuario registroUsuarios;
    private HashMap<String, String> prestamos;

    public ServiciosBiblioteca(Biblioteca biblioteca, RegistroUsuario registroUsuarios) {
        this.biblioteca = biblioteca;
        this.registroUsuarios = registroUsuarios;
        this.prestamos = new HashMap<>();
    }

    public boolean arrendarLibro(String isbn, String rutUsuario) {
        Libro libro = biblioteca.buscarLibroPorIsbn(isbn);
        Usuario usuario = registroUsuarios.buscarUsuarioPorRut(rutUsuario);

        if (libro == null || usuario == null) return false;
        if (libro.isPrestado()) return false;

        libro.setPrestado(true);
        prestamos.put(isbn, rutUsuario);
        return true;
    }

    public boolean devolverLibro(String isbn, String rutUsuario) {
        Libro libro = biblioteca.buscarLibroPorIsbn(isbn);
        if (libro == null || !libro.isPrestado()) return false;
        String rutPrestamo = prestamos.get(isbn);
        if (rutPrestamo == null || !rutPrestamo.equals(rutUsuario)) return false;

        libro.setPrestado(false);
        prestamos.remove(isbn);
        return true;
    }

    public ArrayList<Libro> obtenerArriendosUsuario(String rutUsuario) {
        ArrayList<Libro> resultado = new ArrayList<>();
        for (String isbn : prestamos.keySet()) {
            if (prestamos.get(isbn).equals(rutUsuario)) {
                Libro libro = biblioteca.buscarLibroPorIsbn(isbn);
                if (libro != null) resultado.add(libro);
            }
        }
        return resultado;
    }

    public String consultarPrestamo(String isbn) {
        return prestamos.get(isbn);
    }

    public HashMap<String, String> getPrestamos() {
        return prestamos;
    }
}