package modelo.biblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class Biblioteca {
    private ArrayList<Libro> libros;
    private HashSet<Libro> setLibros;

    public Biblioteca() {
        libros = new ArrayList<>();
        setLibros = new HashSet<>();
    }

    public boolean agregarLibro(Libro libro) {
        if (setLibros.contains(libro)) return false;
        libros.add(libro);
        setLibros.add(libro);
        return true;
    }

    public boolean eliminarLibro(String isbn) {
        Libro libro = buscarLibroPorIsbn(isbn);
        if (libro != null && !libro.isPrestado()) {
            libros.remove(libro);
            setLibros.remove(libro);
            return true;
        }
        return false;
    }

    public Libro buscarLibroPorIsbn(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) return libro;
        }
        return null;
    }

    public TreeSet<Libro> getLibrosOrdenados() {
        return new TreeSet<>(libros);
    }

    public ArrayList<Libro> buscarPorTituloOAutor(String consulta) {
        ArrayList<Libro> resultado = new ArrayList<>();
        String query = consulta.toLowerCase();
        for (Libro libro : libros) {
            if (libro.getTitulo().toLowerCase().contains(query) ||
                libro.getAutor().toLowerCase().contains(query)) {
                resultado.add(libro);
            }
        }
        return resultado;
    }

    public void cargarLibros(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea = br.readLine(); // saltar cabecera
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String titulo = partes[0].trim();
                String autor = partes[1].trim();
                String genero = partes[2].trim();
                String isbn = partes[3].trim();
                String estado = partes[4].trim();
                boolean prestado = estado.equalsIgnoreCase("Prestado");
                Libro libro = new Libro(titulo, autor, genero, isbn, prestado);
                agregarLibro(libro);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar libros: " + e.getMessage());
        }
    }

    public void guardarLibros(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            pw.println("Titulo,Autor,Genero,ISBN,Estado");
            for (Libro libro : libros) {
                String estado = libro.isPrestado() ? "Prestado" : "Disponible";
                pw.printf("%s,%s,%s,%s,%s\n", libro.getTitulo(), libro.getAutor(),
                        libro.getGenero(), libro.getIsbn(), estado);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar libros: " + e.getMessage());
        }
    }
}