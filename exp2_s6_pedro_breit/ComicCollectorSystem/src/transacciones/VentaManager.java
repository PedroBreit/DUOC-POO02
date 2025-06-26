package transacciones;

import comic.Comic;
import usuario.Usuario;
import util.LoggerUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

public final class VentaManager {
    private final ArrayList<Venta> ventas;
    private final String ventasFile = "data/ventas.txt";

    public VentaManager() {
        this.ventas = new ArrayList<>();
        cargarVentas();
    }

    public void cargarVentas() {
        ventas.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ventasFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length != 4) continue;
                ventas.add(new Venta(Integer.parseInt(datos[1]), datos[2], datos[3]));
            }
        } catch (FileNotFoundException e) {
            LoggerUtil.logError("Archivo de ventas no encontrado al cargar", e);
            System.out.println("Archivo de ventas no encontrado. Detalle registrado en error_log.txt");
        } catch (Exception e) {
            LoggerUtil.logError("Error al cargar ventas", e);
            System.out.println("Error al cargar ventas. Detalle registrado en error_log.txt");
        }
    }

    public void guardarVentas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ventasFile))) {
            for (Venta v : ventas) {
                pw.println("x;" + v.getIdComic() + ";" + v.getTituloComic() + ";" + v.getFecha());
            }
        } catch (Exception e) {
            LoggerUtil.logError("Error al guardar ventas", e);
            System.out.println("Error al guardar ventas. Detalle registrado en error_log.txt");
        }
    }

    public void registrarVenta(Usuario usuario, Comic comic) {
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Venta venta = new Venta(comic.getId(), comic.getTitulo(), fecha);
        ventas.add(venta);
        usuario.getCompras().add(venta);
        guardarVentas();
    }

    public void verHistorialVentas() {
        System.out.println("\n--- Historial de ventas ---");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Venta v : ventas) {
            System.out.println(v);
        }
    }
}