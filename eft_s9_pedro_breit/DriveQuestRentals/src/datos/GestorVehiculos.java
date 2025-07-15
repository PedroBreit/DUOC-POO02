package datos;

import java.io.IOException;
import modelo.Vehiculo;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GestorVehiculos {
    Map<String, Vehiculo> mapaVehiculos = Collections.synchronizedMap(new HashMap<>());

    public boolean agregarVehiculo(Vehiculo v) throws IOException {
        if (!mapaVehiculos.containsKey(v.getPatente())) {
            mapaVehiculos.put(v.getPatente(), v);
            ArchivoVehiculos.guardarVehiculos(this.listarVehiculos(), "vehiculos.txt");
            return true;
        }
        return false;
    }
    
    public boolean eliminarVehiculo(String patente) throws IOException {
        Vehiculo eliminado = mapaVehiculos.remove(patente);
        if (eliminado != null) {
            ArchivoVehiculos.guardarVehiculos(this.listarVehiculos(), "vehiculos.txt");
            return true;
        }
        return false;
    }

    public Vehiculo getVehiculoPorPatente(String patente) {
        return mapaVehiculos.get(patente);
    }

    public boolean existePatente(String patente) {
        return mapaVehiculos.containsKey(patente);
    }

    public List<Vehiculo> listarVehiculos() {
        synchronized (mapaVehiculos){
            return new ArrayList<>(mapaVehiculos.values());
        }
    }
}