package comic;

public class Comic {
    private final int id;
    private final String titulo;
    private final String autor;
    private final String editorial;
    private final int anio;
    private final String numeroSerie;
    private final String genero;
    private final String idioma;
    private int stock;

    public Comic(int id, String titulo, String autor, String editorial, int anio, String numeroSerie, String genero, String idioma, int stock) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.numeroSerie = numeroSerie;
        this.genero = genero;
        this.idioma = idioma;
        this.stock = stock;
    }

    public int getId() { return id; }
    
    public String getTitulo() { return titulo; }
    
    public String getAutor() { return autor; }
    
    public String getEditorial() { return editorial; }
    
    public int getAnio() { return anio; }
    
    public String getNumeroSerie() { return numeroSerie; }
    
    public String getGenero() { return genero; }
    
    public String getIdioma() { return idioma; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return id + " | " + titulo + " | " + autor + " | " + editorial + " | " + anio + " | " + numeroSerie + " | " + genero + " | " + idioma + " | Stock: " + stock;
    }
}