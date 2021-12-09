package projeto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try {
			Scanner entrada = new Scanner(System.in);
			
			Usuario ui = new Usuario();
			Bibliotecario bi = new Bibliotecario();
			Exemplar ei = new Exemplar();
			
			System.out.println("Bem-vindo ao sistema de empr�stimos!");
			
			System.out.println("A��es:");
			System.out.println("1 - Cadastrar Bibliotec�rio");
			System.out.println("2 - Cadastrar Usu�rio");
			System.out.println("3 - Login Bibliotec�rio");
			System.out.println("4 - Login Usu�rio");
			System.out.println("5 - Cron Contabilizar Multas");
			System.out.println("6 - Listar Bibliotec�rios");
			System.out.println("7 - Listar Usu�rios");
			System.out.println("8 - Listar Exemplares");
			System.out.println("9 - Desativar Usu�rios");
			System.out.println("10 - Ativar Usu�rios");
			System.out.println("11 - Desativar Bibliotec�rio");
			System.out.println("12 - Ativar Bibliotec�rio");
			
			System.out.print("O que deseja fazer (digite o n�mero correspondente das a��es listadas acima)? ");
			int acaoI = Integer.parseInt(entrada.nextLine());
			
			switch (acaoI) {
				case 1:
					System.out.println("Cadastro de Bibliotec�rio:");
					System.out.print("Digite seu nome: ");
					String nome = entrada.nextLine();
					
					System.out.print("Crie seu Usu�rio: ");
					String usuario = entrada.nextLine();
					
					System.out.print("Crie uma senha: ");
					String senha = entrada.nextLine();
					
					System.out.println();
					Bibliotecario b = new Bibliotecario(bi.geraIdB(), nome, usuario, senha);
					b.gravarB();
					
					break;
				case 2:
					System.out.println("Cadastro Usu�rio: ");
					
					System.out.print("Digite seu nome: ");
					nome = entrada.nextLine();
					
					System.out.print("Crie seu Usu�rio: ");
					usuario = entrada.nextLine();
					
					System.out.print("Crie uma senha: ");
					senha = entrada.nextLine();
					
					Usuario u = new Usuario(ui.geraId(), nome, usuario, senha);
					u.gravarU();
					break;
				case 3:
					System.out.println("Login Bibliotec�rio: ");
					System.out.print("Digite seu Usu�rio: ");
					usuario = entrada.nextLine();
					
					System.out.print("Digite sua senha: ");
					senha = entrada.nextLine();
					b = bi.loginB(usuario, senha);
					
					while (b == null) {
						System.out.println("Login inv�lido!");
						System.out.print("Digite seu Usu�rio: ");
						usuario = entrada.nextLine();
						
						System.out.print("Digite sua senha: ");
						senha = entrada.nextLine();
						b = bi.loginB(usuario, senha);
					}
					
					System.out.println("A��es Bibliotec�rio:");
					System.out.println("1 - Cadastrar Exemplar");
					System.out.println("2 - Alterar Exemplar");
					System.out.println("3 - Deletar Exemplar");
					System.out.print("O que deseja fazer (digite o n�mero correspondente das a��es listadas acima)? ");
					int acaoB = Integer.parseInt(entrada.nextLine());
					
					switch (acaoB) {
						case 1:
							System.out.println("Cadastro de Livro/Exemplar: ");
							
							System.out.print("Digite o t�tulo do livro: ");
							String titulo = entrada.nextLine();
							
							System.out.print("Digite o(a) autor(a) do livro: ");
							String autor = entrada.nextLine();
							
							System.out.print("Digite o ISBN do livro: ");
							String isbn = entrada.nextLine();
							
							System.out.print("Digite o n�mero do Exemplar: ");
							int numero = Integer.parseInt(entrada.nextLine());
							
							b.incluirExemplar(ei.geraId(), titulo, autor, isbn, numero, true);
							break;
						case 2:
							System.out.println("Alterar Livro/Exemplar: ");
							
							System.out.print("Digite id do Exemplar que deseja alterar: ");
							int id = Integer.parseInt(entrada.nextLine());
							
							System.out.print("Digite o t�tulo do livro: ");
							titulo = entrada.nextLine();
							
							System.out.print("Digite o(a) autor(a) do livro: ");
							autor = entrada.nextLine();
							
							System.out.print("Digite o ISBN do livro: ");
							isbn = entrada.nextLine();
							
							System.out.print("Digite o n�mero do Exemplar: ");
							numero = Integer.parseInt(entrada.nextLine());
							
							if (b.alterarExemplar(id, titulo, autor, isbn, numero, true)) {
								System.out.println("Exemplar alterado com sucesso!");
							} else {
								System.out.println("Falha ao alterar exemplar!");
							}
							break;
						case 3:
							System.out.println("Deletar Livro/Exemplar: ");
							System.out.print("Digite id do Exemplar que deseja excluir: ");
							id = Integer.parseInt(entrada.nextLine());
							
							if (b.deletarExemplar(id, ei)) {
								System.out.println("Exemplar deletado com sucesso!");
							} else {
								System.out.println("Falha ao deletar Exemplar!");
							}
							break;
						default:
							System.out.println("A��o inv�lida!");
							break;
					}
					
					break;
				case 4: 
					System.out.println("Login Usu�rio: ");
					System.out.print("Digite seu Usu�rio: ");
					usuario = entrada.nextLine();
					
					System.out.print("Digite sua senha: ");
					senha = entrada.nextLine();
					u = ui.loginU(usuario, senha);
					
					while (u == null) {
						System.out.println("Login inv�lido!");
						System.out.print("Digite seu Usu�rio: ");
						usuario = entrada.nextLine();
						
						System.out.print("Digite sua senha: ");
						senha = entrada.nextLine();
						u = ui.loginU(usuario, senha);
					}
					
					System.out.println("A��es Usu�rios:");
					System.out.println("1 - Realizar empr�stimo");
					System.out.println("2 - Remover Empr�stimo");
					System.out.println("3 - Renovar Emprestimo");
					System.out.println("4 - Verificar total de multa");
					System.out.println("5 - Pagar multa");
					System.out.println("6 - Listar meus empr�stimos");
					System.out.print("O que deseja fazer (digite o n�mero correspondente das a��es listadas acima)? ");
					int acaoU = Integer.parseInt(entrada.nextLine());
					
					switch (acaoU) {
					case 1:
						System.out.println("Realizar Empr�stimo: ");
						
						System.out.print("Digite o c�digo do exemplar que deseja: ");
						int codigo = Integer.parseInt(entrada.nextLine());
						
						Exemplar ex = ei.buscaExemplar(codigo);
						
						if (ex != null && ex.isDisponivel()) {
							DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							String dataI = dt.format(LocalDateTime.now());
							LocalDate dtf = LocalDate.parse(dataI, dt);
							String dataF = dtf.plusDays(15).format(dt);
							if (u.addEmprestimo(bi, ex, dataI, dataF)) {
								
								System.out.println("Empr�stimo realizado com sucesso!");
							} else {
								System.out.println("Exemplar indispon�vel!");
							}
							
						} else {
							System.out.println("Exemplar indispon�vel!");
						}
						
						break;
					case 2:
						System.out.println("Devolu��o empr�stimo: ");
						
						System.out.print("Digite o c�digo do exemplar que deseja devolver: ");
						codigo = Integer.parseInt(entrada.nextLine());
						
						ex = ei.buscaExemplar(codigo);
						
						if (ex != null) {
							if (u.removeEmprestimo(bi, ex)) {
								System.out.println("Devolu��o realizada com sucesso!");
							} else {
								System.out.println("Falha na devolu��o!");
							}
							
						} else {
							System.out.println("N�o foi poss�vel deletar pois o exemplar n�o existe!");
						}
						break;
					case 3:
						System.out.println("Renova��o empr�stimo: ");
						
						System.out.print("Digite o c�digo do exemplar que deseja renovar: ");
						codigo = Integer.parseInt(entrada.nextLine());
						
						ex = ei.buscaExemplar(codigo);
						
						if (ex != null) {
							if (u.renovarEmprestimo(bi, ex)) {
								System.out.println("Renova��o realizada com sucesso!");
							} else {
								System.out.println("Falha ao renovar!");
							}
						} else {
							System.out.println("N�o foi poss�vel renovar pois o exemplar n�o existe!");
						}
						
						break;
					case 4:
						System.out.println("Total de multa: " + u.totalMulta());
						break;
					case 5:
						System.out.println("Total de multa: " + u.totalMulta());
						if (u.totalMulta() > 0) {
							System.out.print("Digite o valor a ser pago pela multa: ");
							double valor = Double.parseDouble(entrada.nextLine());
							
							if (u.pagarMultas(valor)) {
								System.out.println("Pagamento realizado com sucesso!");
							} else {
								System.out.println("O valor digitado n�o corresponde ao valor total de multas.");
							}
						} else {
							System.out.println("Voc� n�o possui multa para pagar atualmente.");
						}
						
						break;
					case 6:
						List<Emprestimo> listE = ui.lerEU();
						
						for (Emprestimo e : listE) {
							if (e.getUsuario() == u.getId()) {
								System.out.println(e);
							}
						}
						break;
					default:
						System.out.println("A��o Inv�lida!");
						break;
					}
					break;
				case 5:
					ui.contabilizarMulta();
					break;
				case 6:
					List<Bibliotecario> listB = bi.lerB();
					
					for (Bibliotecario bl : listB) {
						System.out.println(bl);
					}
					break;
				case 7:
					List<Usuario> listU = ui.lerU();
					
					for (Usuario ul : listU) {
						System.out.println(ul);
					}
					break;
				case 8:
					List<Exemplar> listE = ei.ler();
					
					for (Exemplar e : listE) {
						System.out.println(e);
					}
					break;
				case 9:
					
					System.out.print("Digite o id do usuario que deseja desativar: ");
					int id = Integer.parseInt(entrada.nextLine());
					ui.desativarUsuario(id);
					break;
				case 10:
					
					System.out.print("Digite o id do usuario que deseja ativar: ");
					id = Integer.parseInt(entrada.nextLine());
					ui.ativarUsuario(id);
					break;
				case 11:
					
					System.out.print("Digite o id do bibliotecario que deseja desativar: ");
					id = Integer.parseInt(entrada.nextLine());
					bi.desativarBibliotecario(id);
					break;
				case 12:
					
					System.out.print("Digite o id do bibliotecario que deseja ativar: ");
					id = Integer.parseInt(entrada.nextLine());
					bi.ativarBibliotecario(id);
					break;
				default:
					System.out.println("A��o inv�lida!");
					break;
			}
			
			entrada.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
