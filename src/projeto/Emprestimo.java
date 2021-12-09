package projeto;

public class Emprestimo {
	private String dataInicio;
	private String dataFim;
	private double multa;
	private Exemplar exemplar;
	private int usuario;
	
	public Emprestimo(String dataInicio, String dataFim, Exemplar exemplar, int usuario) {
		setDataInicio(dataInicio);
		setDataFim(dataFim);
		setExemplar(exemplar);
		setUsuario(usuario);
	}
	public Emprestimo() {
		
	}
	public String getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	public String getDataFim() {
		return dataFim;
	}
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}
	public double getMulta() {
		return multa;
	}
	public void setMulta(double multa) {
		this.multa = multa;
	}
	public Exemplar getExemplar() {
		return exemplar;
	}
	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}
	public int getUsuario() {
		return usuario;
	}
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Emprestimo [dataInicio=");
		builder.append(dataInicio);
		builder.append(", dataFim=");
		builder.append(dataFim);
		builder.append(", multa=");
		builder.append(multa);
		builder.append(", exemplar=");
		builder.append(exemplar);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append("]");
		return builder.toString();
	}
	
}
