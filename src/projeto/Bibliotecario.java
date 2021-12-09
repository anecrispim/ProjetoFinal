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

public class Bibliotecario extends Usuario {
	
	public Bibliotecario() {
		
	}
	public Bibliotecario(int id, String nome, String usuario, String senha) {
		super(id, nome, usuario, senha);
	}
	public boolean aceitarEmprestimo(Usuario u) {
		if (u.totalMulta() == 0 && u.getQuantidadeEmps() <= u.getLIMITE()) {
			return true;
		}
		return false;
	}
	
	public boolean aceitarDevolucao(Emprestimo e) {
		if (e.getMulta() == 0) {
			return true;
		}
		return false;
	}
	
	public void incluirExemplar (int id, String titulo, String autor, String isbn, int numero, boolean disponivel) {
		Exemplar ex = new Exemplar(id, titulo, autor, isbn, numero, disponivel);
		ex.gravar();
	}
	
	public boolean alterarExemplar (int id, String titulo, String autor, String isbn, int numero, boolean disponivel) {
		if (deletarExemplar(id, new Exemplar())) {
			Exemplar ex = new Exemplar(id, titulo, autor, isbn, numero, disponivel);
			ex.gravar();
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean deletarExemplar(int id, Exemplar ex) {
		List<Exemplar> listE = ex.ler();
		if (listE != null) {
			for (Exemplar e : listE) {
				if (e.getId() == id) {
					listE.remove(e);
					ex.restaurarLista(listE);
					return true;
				}
			}
		}
		return false;
	}
	
	public int geraIdB() {
		List<Bibliotecario> listB = lerB();
		int cont = 0;
		if (listB != null) {
			cont = listB.size();
		}
		return (cont + 1);
	}
	
	public Bibliotecario loginB(String usuario, String senha) {
		List<Bibliotecario> listB = lerB();
		if (listB != null) {
			for (Bibliotecario b : listB) {
				if (usuario.equals(b.getUsuario()) && criptografar(senha).equals(b.getSenha()) && b.isAtivado()) {
					return b;
				}
			}
		}
		return null;
	}
	
	public void gravarB() {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
	    List<Bibliotecario> list = lerB();
	    if (list == null) {
	    	list = new ArrayList<Bibliotecario>();
		    list.add(this);
	    } else {
	    	list.add(this);
	    }
		try {
			writer = new FileWriter("json/Bibliotecario.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Bibliotecario> lerB() {
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
							 new FileReader("json/Bibliotecario.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    Type listType = new TypeToken<ArrayList<Bibliotecario>>(){}.getType();
	    List<Bibliotecario> lista = new ArrayList<Bibliotecario>();
	    lista = new Gson().fromJson(bufferedReader, listType);
	    return lista;
	}
	
	public void ativarBibliotecario(int id) {
		List<Bibliotecario> list = lerB();
		List<Bibliotecario> list2 = new ArrayList<Bibliotecario>();
		for (Bibliotecario u : list) {
			if (u.getId() == id) {
				u.setAtivado(true);
			}
			list2.add(u);
		}
		for (Bibliotecario u : list2) {
			list.remove(u);
		}
		restaurarListaB(list);
		restaurarListaB(list2);
	}

	public void desativarBibliotecario(int id) {
		List<Bibliotecario> list = lerB();
		List<Bibliotecario> list2 = new ArrayList<Bibliotecario>();
		for (Bibliotecario u : list) {
			if (u.getId() == id) {
				u.setAtivado(false);
			}
			list2.add(u);
		}
		for (Bibliotecario u : list2) {
			list.remove(u);
		}
		restaurarListaB(list);
		restaurarListaB(list2);
	}
	
	public void restaurarListaB(List<Bibliotecario> list) {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/Bibliotecario.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bibliotecario [toString()=");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
