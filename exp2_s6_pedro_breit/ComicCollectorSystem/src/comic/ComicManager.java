package comic;

import util.LoggerUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.TreeSet;
import java.util.ArrayList;

public final class ComicManager {
    private final ArrayList<Comic> comics;
    private final String csvFile = "data/comics.csv";

    public ComicManager() {
        this.comics = new ArrayList<>();
        cargarComics();
    }

    public void guardarComics() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile))) {
            for (Comic c : comics) {
                pw.println(c.getId() + ";" + c.getTitulo() + ";" + c.getAutor() + ";" + c.getEditorial() + ";" +
                        c.getAnio() + ";" + c.getNumeroSerie() + ";" + c.getGenero() + ";" + c.getIdioma() + ";" + c.getStock());
            }
        } catch (Exception e) {
            LoggerUtil.logError("Error al guardar comics", e);
            System.out.println("Error al guardar comics. Detalle registrado en error_log.txt");
        }
    }
    public void cargarComics() {
        comics.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length != 9) continue;
                comics.add(new Comic(
                        Integer.parseInt(datos[0]), datos[1], datos[2], datos[3],
                        Integer.parseInt(datos[4]), datos[5], datos[6], datos[7],
                        Integer.parseInt(datos[8])
                ));
            }
        } catch (FileNotFoundException e) {
            LoggerUtil.logError("Archivo de comics no encontrado al cargar", e);
            System.out.println("Archivo de comics no encontrado. Detalle registrado en error_log.txt");
        } catch (Exception e) {
            LoggerUtil.logError("Error al cargar comics", e);
            System.out.println("Error al cargar comics. Detalle registrado en error_log.txt");
        }
    }
    
    public void agregarComic(Comic comic) {
        comics.add(comic);
        guardarComics();
    }
    public boolean eliminarComic(int id) {
        for (Comic c : comics) {
            if (c.getId() == id) {
                comics.remove(c);
                guardarComics();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Comic> buscarPorTitulo(String titulo) {
        ArrayList<Comic> resultado = new ArrayList<>();
        for (Comic c : comics) {
            if (c.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    public ArrayList<Comic> buscarPorAutor(String autor) {
        ArrayList<Comic> resultado = new ArrayList<>();
        for (Comic c : comics) {
            if (c.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    public ArrayList<Comic> buscarPorEditorial(String editorial) {
        ArrayList<Comic> resultado = new ArrayList<>();
        for (Comic c : comics) {
            if (c.getEditorial().toLowerCase().contains(editorial.toLowerCase())) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    public Comic buscarPorId(int id) {
        for (Comic c : comics) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public ArrayList<Comic> getComics() { return comics; }
    
    public TreeSet<String> getIdYTitulosUnicosOrdenados() {
        TreeSet<String> idTitulos = new TreeSet<>();
        for (Comic c : comics) {
            idTitulos.add(c.getId() + " - " +c.getTitulo());
        }
        return idTitulos;
    }
}
