package transacciones;

public class Reserva {
    private final int idComic;
    private final String tituloComic;
    private int posicionCola;
    private final String rutUsuario;

    public Reserva(int idComic, String tituloComic, int posicionCola, String rutUsuario) {
        this.idComic = idComic;
        this.tituloComic = tituloComic;
        this.posicionCola = posicionCola;
        this.rutUsuario = rutUsuario;
    }

    public int getIdComic() { return idComic; }
    
    public String getTituloComic() { return tituloComic; }
    
    public int getPosicionCola() { return posicionCola; }
    public void setPosicionCola(int posicionCola) { this.posicionCola = posicionCola; }
    
    public String getRutUsuario() { return rutUsuario; }

    @Override
    public String toString() {
        return "Comic: " + tituloComic + " | Posición en cola: " + posicionCola + " | RUT: " + rutUsuario;
    }
}