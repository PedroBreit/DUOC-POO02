package transacciones;

public class Venta {
    private final int idComic;
    private final String tituloComic;
    private final String fecha;

    public Venta(int idComic, String tituloComic, String fecha) {
        this.idComic = idComic;
        this.tituloComic = tituloComic;
        this.fecha = fecha;
    }

    public int getIdComic() { return idComic; }
    
    public String getTituloComic() { return tituloComic; }
    
    public String getFecha() { return fecha; }

    @Override
    public String toString() {
        return "Comic: " + tituloComic + " | Fecha: " + fecha;
    }
}