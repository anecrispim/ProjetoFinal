package projeto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Usuario {
	private int id;
	private String nome;
	private String usuario;
	private String senha;
	private boolean ativado = true;
	private final int LIMITE = 10;
	private int quantidadeEmps = 0;
	private List<Emprestimo> listaE = new ArrayList<Emprestimo>();
	private static MessageDigest md = null;
	
	public Usuario(int id, String nome, String usuario, String senha) {
		setId(id);
		setNome(nome);
		setUsuario(usuario);
		setSenha(criptografar(senha));
	}
	public Usuario() {
	}
	
	static {
		try {
		    md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
		    ex.printStackTrace();
		}
    }

	private static char[] hexCodes(byte[] text) {
        char[] hexOutput = new char[text.length * 2];
        String hexString;

        for (int i = 0; i < text.length; i++) {
            hexString = "00" + Integer.toHexString(text[i]);
            hexString.toUpperCase().getChars(hexString.length() - 2,
                                	hexString.length(), hexOutput, i * 2);
        }
        return hexOutput;
    }

	public static String criptografar(String pwd) {
        if (md != null) {
            return new String(hexCodes(md.digest(pwd.getBytes())));
        }
        return null;
    }

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAtivado() {
		return ativado;
	}
	public void setAtivado(boolean ativado) {
		this.ativado = ativado;
	}
	public int getLIMITE() {
		return LIMITE;
	}
	public int getQuantidadeEmps() {
		return quantidadeEmps;
	}
	public void setQuantidadeEmps(int quantidadeEmps) {
		this.quantidadeEmps = quantidadeEmps;
	}
	public List<Emprestimo> getListaE() {
		return listaE;
	}
	public void setListaE(List<Emprestimo> listaE) {
		this.listaE = listaE;
	}
	
	public boolean addEmprestimo(Bibliotecario b, Exemplar ex, String dataI, String dataF) {
		if (b.aceitarEmprestimo(this)) {
			b.alterarExemplar(ex.getId(), ex.getTitulo(), ex.getAutor(), ex.getIsbn(), ex.getNumero(), false);
			ex = new Exemplar(ex.getId(), ex.getTitulo(), ex.getAutor(), ex.getIsbn(), ex.getNumero(), false);
			
			Emprestimo e = new Emprestimo(dataI, dataF, ex, this.getId());
			listaE.add(e);
			gravarEU();
			setQuantidadeEmps(getQuantidadeEmps() + 1);
			alterarUsuario(this);
			return true;
		}
		return false;
	}
	
	public boolean removeEmprestimo(Bibliotecario b, Exemplar ex) {
		Emprestimo ei = null;
		if (listaE != null) {
			for (Emprestimo e : listaE) {
				if (e.getExemplar().getId() == ex.getId()) {
					ei = e;
				}
			}
			if (ei != null) {
				if (b.aceitarDevolucao(ei)) {
					b.alterarExemplar(ex.getId(), ex.getTitulo(), ex.getAutor(), ex.getIsbn(), ex.getNumero(), true);
					ex = new Exemplar(ex.getId(), ex.getTitulo(), ex.getAutor(), ex.getIsbn(), ex.getNumero(), true);
	
					listaE.remove(ei);
					gravarEU();
					setListaE(listaE);
					setQuantidadeEmps(getQuantidadeEmps() - 1);
					alterarUsuario(this);
					return true;
				}
			}else {
				return false;
			}
		}
		
		return false;
	}
	
	public boolean renovarEmprestimo(Bibliotecario b, Exemplar ex) {
		Emprestimo ei = null;
		if (listaE != null) {
			for (Emprestimo e : listaE) {
				if (e.getExemplar().getId() == ex.getId()) {
					ei = e;
				}
			}
			if (ei != null) {
				if (ei.getMulta() == 0) {
					String dataI = ei.getDataInicio();
					DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					LocalDate dtf = LocalDate.parse(ei.getDataFim(), dt);
					String dataF = dtf.plusDays(15).format(dt);
					removeEmprestimo(b, ex);
					addEmprestimo(b, ex, dataI, dataF);
					
					return true;
				}
			} else {
				return false;
			}
			
		}
		
		return false;
	}
	
	public void gravarEU() {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/EmprestimosUsuario.json");
			writer.write(gson.toJson(listaE));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Emprestimo> lerEU() {
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
							 new FileReader("json/EmprestimosUsuario.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    Type listType = new TypeToken<ArrayList<Emprestimo>>(){}.getType();
	    listaE = new ArrayList<Emprestimo>();
	    listaE = new Gson().fromJson(bufferedReader, listType);
	    return listaE;
	}
	
	public double totalMulta() {
		List<Emprestimo> list = lerEU();
		double totalMulta = 0;
		if (list != null) {
			for (Emprestimo e : list) {
				if (e.getUsuario() == this.id) {
					totalMulta = totalMulta + e.getMulta();
				}
			}
		}
		return totalMulta;
	}
	
	public boolean pagarMultas(double valor) {
		List<Emprestimo> listE = lerEU();
		List<Emprestimo> listE2 = new ArrayList<Emprestimo>();
		if (valor == totalMulta()) {
			for (Emprestimo e : listE) {
				e.setMulta(0);
				listE2.add(e);
			}
			for (Emprestimo e : listE2) {
				listE.remove(e);
			}
			restaurarListaEmp(listE);
			restaurarListaEmp(listE2);
			return true;
		}
		return false;
	}
	
	public void gravarU() {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
	    List<Usuario> list = lerU();
	    if (list == null) {
	    	list = new ArrayList<Usuario>();
		    list.add(this);
	    } else {
	    	list.add(this);
	    }
		try {
			writer = new FileWriter("json/Usuario.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Usuario> lerU() {
	    BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
							 new FileReader("json/Usuario.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    Type listType = new TypeToken<ArrayList<Usuario>>(){}.getType();
	    List<Usuario> lista = new ArrayList<Usuario>();
	    lista = new Gson().fromJson(bufferedReader, listType);
	    return lista;
	}
	
	public int geraId() {
		List<Usuario> listU = lerU();
		int cont = 0;
		if (listU != null) {
			cont = listU.size();
		}
		return cont + 1;
	}
	
	public Usuario loginU(String usuario, String senha) {
		List<Usuario> listU = lerU();
		if (listU != null) {
			for (Usuario u : listU) {
				if (usuario.equals(u.getUsuario()) && criptografar(senha).equals(u.getSenha()) && u.isAtivado()) {
					return u;
				}
			}
		}
		return null;
	}
	
	public void restaurarLista(List<Usuario> list) {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/Usuario.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean alterarUsuario (Usuario u) {
		if (deletarUsuario(u)) {
			u.gravarU();
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean deletarUsuario(Usuario ui) {
		List<Usuario> listU = ui.lerU();
		if (listU != null) {
			for (Usuario u : listU) {
				if (u.getId() == ui.getId()) {
					listU.remove(u);
					u.restaurarLista(listU);
					return true;
				}
			}
		}
		return false;
	}
	
	public void restaurarListaEmp(List<Emprestimo> list) {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer;
		try {
			writer = new FileWriter("json/EmprestimosUsuario.json");
			writer.write(gson.toJson(list));
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void contabilizarMulta() {
		List<Emprestimo> listE = lerEU();
		List<Emprestimo> listE2 = new ArrayList<Emprestimo>();
		double contMulta = 0;
		
		for (Emprestimo e : listE) {
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
				Date dataNow = new Date();
				Date dataF = formato.parse(e.getDataFim());
				String dataN = formato.format(dataNow);
				Date data = formato.parse(dataN);
				if (data.after(dataF)) {
					long diffInMillies = Math.abs(data.getTime() - dataF.getTime());
				    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				    
				    contMulta = diff * 1;
				    
				    e.setMulta(contMulta);
				}
				listE2.add(e);
			} catch (Exception exc){
				exc.printStackTrace();
			}
		}
		for (Emprestimo e : listE2) {
			listE.remove(e);
		}
		restaurarListaEmp(listE);
		restaurarListaEmp(listE2);
	}
	
	public void ativarUsuario(int id) {
		List<Usuario> list = lerU();
		List<Usuario> list2 = new ArrayList<Usuario>();
		for (Usuario u : list) {
			if (u.getId() == id) {
				u.setAtivado(true);
			}
			list2.add(u);
		}
		for (Usuario u : list2) {
			list.remove(u);
		}
		restaurarLista(list);
		restaurarLista(list2);
	}

	public void desativarUsuario(int id) {
		List<Usuario> list = lerU();
		List<Usuario> list2 = new ArrayList<Usuario>();
		for (Usuario u : list) {
			if (u.getId() == id) {
				u.setAtivado(false);
			}
			list2.add(u);
		}
		for (Usuario u : list2) {
			list.remove(u);
		}
		restaurarLista(list);
		restaurarLista(list2);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id=");
		builder.append(id);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", senha=");
		builder.append(senha);
		builder.append(", ativado=");
		builder.append(ativado);
		builder.append(", LIMITE=");
		builder.append(LIMITE);
		builder.append(", quantidadeEmps=");
		builder.append(quantidadeEmps);
		builder.append(", listaE=");
		builder.append(listaE);
		builder.append(", totalMulta()=");
		builder.append(totalMulta());
		builder.append("]");
		return builder.toString();
	}
	
}
