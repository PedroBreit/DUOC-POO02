package modelo;

public abstract class Vehiculo {
    protected String patente;
    protected String marca;
    protected String modelo;
    protected int anio;
    protected double precioPorDia;
    protected String estado; // Disponible, Rentado, Eliminado

    public Vehiculo() {
        this.estado = "Disponible";
    }

    public Vehiculo(String patente, String marca, String modelo, int anio, double precioPorDia) {
        this.patente = patente;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precioPorDia = precioPorDia;
        this.estado = "Disponible";
    }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }
    public double getPrecioPorDia() { return precioPorDia; }
    public void setPrecioPorDia(double precioPorDia) { this.precioPorDia = precioPorDia; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public abstract void mostrarDatos();
}