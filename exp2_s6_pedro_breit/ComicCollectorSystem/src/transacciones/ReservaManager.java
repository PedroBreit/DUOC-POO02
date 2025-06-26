package transacciones;

import comic.Comic;
import usuario.Usuario;
import util.LoggerUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ReservaManager {
    private final HashMap<Integer, LinkedList<Reserva>> reservasPorComic;
    private final String reservasFile = "data/reservas.txt";

    public ReservaManager() {
        this.reservasPorComic = new HashMap<>();
        cargarReservas();
    }
    
    public void cargarReservas() {
        reservasPorComic.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(reservasFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if(datos.length != 4) continue;
                int idComic = Integer.parseInt(datos[0]);
                String tituloComic = datos[1];
                int posicionCola = Integer.parseInt(datos[2]);
                String rutUsuario = datos[3];
                Reserva reserva = new Reserva(idComic, tituloComic, posicionCola, rutUsuario);                
                LinkedList<Reserva> cola = reservasPorComic.getOrDefault(idComic, new LinkedList<>());
                cola.add(reserva);
                reservasPorComic.put(idComic, cola);
            }
        } catch (FileNotFoundException e) {
            LoggerUtil.logError("Archivo de reservas no encontrado al cargar", e);
            System.out.println("Archivo de reservas no encontrado. Detalle registrado en error_log.txt");
        } catch (Exception e) {
            LoggerUtil.logError("Error al cargar reservas", e);
            System.out.println("Error al cargar reservas. Detalle registrado en error_log.txt");
        }
    }
    public void guardarReservas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(reservasFile))) {
            for (Map.Entry<Integer, LinkedList<Reserva>> entry : reservasPorComic.entrySet()) {
                for (Reserva reserva : entry.getValue()) {
                    pw.println(reserva.getIdComic() + ";" + reserva.getTituloComic() + ";" + reserva.getPosicionCola() + ";" + reserva.getRutUsuario());
                }
            }
        } catch (Exception e) {
            LoggerUtil.logError("Error al guardar reservas", e);
            System.out.println("Error al guardar reservas. Detalle registrado en error_log.txt");
        }
    }
    
    public int hacerReserva(Usuario usuario, Comic comic) {
        int id = comic.getId();
        LinkedList<Reserva> cola = reservasPorComic.getOrDefault(id, new LinkedList<>());
        int pos = cola.size() + 1;
        Reserva reserva = new Reserva(id, comic.getTitulo(), pos, usuario.getRut());
        cola.add(reserva);
        reservasPorComic.put(id, cola);
        usuario.getReservas().add(reserva);
        guardarReservas();
        return pos;
    }

    public List<Reserva> getReservasPorComic(int idComic) {
        return reservasPorComic.getOrDefault(idComic, new LinkedList<>());
    }
    
    public HashMap<Integer, LinkedList<Reserva>> getReservasTodas() {
        return reservasPorComic;
    }
    public boolean eliminarReserva(int idComic, int posicionCola) {
        LinkedList<Reserva> cola = reservasPorComic.get(idComic);
        if (cola == null || cola.isEmpty()) return false;
        Reserva aEliminar = null;
        for (Reserva r : cola) {
            if (r.getPosicionCola() == posicionCola) {
                aEliminar = r;
                break;
            }
        }
        if (aEliminar == null) return false;
        cola.remove(aEliminar);
        int pos = 1;
        for (Reserva r : cola) {
            r.setPosicionCola(pos++);
        }
        guardarReservas();
        return true;
    }
}
