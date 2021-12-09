package projeto;

public class Livro {
	private int id;
	private String titulo;
	private String autor;
	private String isbn;
	
	public Livro (int id, String titulo, String autor, String isbn) {
		setId(id);
		setTitulo(titulo);
		setAutor(autor);
		setIsbn(isbn);
	}
	public Livro () {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		if (titulo.length() > 0) {
			this.titulo = titulo;
		}
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		if (titulo.length() > 0) {
			this.autor = autor;
		}
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		if (isbn.length() == 17) {
			this.isbn = isbn;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Livro [id=");
		builder.append(id);
		builder.append(", titulo=");
		builder.append(titulo);
		builder.append(", autor=");
		builder.append(autor);
		builder.append(", isbn=");
		builder.append(isbn);
		builder.append("]");
		return builder.toString();
	}
}
