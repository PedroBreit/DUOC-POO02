package negocio;

public interface Facturable {
    double IVA = 0.19;
    double DESCUENTO_CARGA = 0.07;
    double DESCUENTO_PASAJEROS = 0.12;

    void mostrarBoleta();
}