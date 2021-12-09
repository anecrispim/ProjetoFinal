package projeto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Exemplar extends Livro {
	private int numero;
	private boolean disponivel;
	
	public Exemplar(int id, String titulo, String autor, String isbn, int numero, boolean disponivel) {
		super(id, titulo, autor, isbn);
		setNumero(numero);
		setDisponivel(disponivel);
	}
	public Exemplar () {
		
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		if (numero > 0) {
			this.numero = numero;
		}
	}
	public boolean isDisponivel() {
		return disponivel;
	}
	public void setDisponivel(boolean disponivel) {
		this.disponivel = disponivel;
	}
	
	public int geraId() {
		List<Exemplar> listE = ler();
		int cont = 0;
		if (listE != null) {
			cont = listE.size();
		}
		return (cont + 1);
	}
	
	public void gravar() {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
	    List<Exemplar> list = ler();
	    if (list == null) {
	    	list = new ArrayList<Exemplar>();
		    list.add(this);
	    } else {
	    	list.add(this);
	    }
		try {
			writer = new FileWriter("json/Exemplar.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void restaurarLista(List<Exemplar> list) {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/Exemplar.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<Exemplar> ler() {
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
							 new FileReader("json/Exemplar.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    Type listType = new TypeToken<ArrayList<Exemplar>>(){}.getType();
	    List<Exemplar> lista = new ArrayList<Exemplar>();
	    lista = new Gson().fromJson(bufferedReader, listType);
	    return lista;
	}
	
	public Exemplar buscaExemplar(int id) {
		List<Exemplar> listE = ler();
		if (listE != null) {
			for (Exemplar e : listE) {
				if (id == e.getId()) {
					return e;
				}
			}
		}
		return null;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Exemplar [numero=");
		builder.append(numero);
		builder.append(", disponivel=");
		builder.append(disponivel);
		builder.append(", toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
