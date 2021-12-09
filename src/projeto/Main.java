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
			
			System.out.println("Bem-vindo ao sistema de empréstimos!");
			
			System.out.println("Ações:");
			System.out.println("1 - Cadastrar Bibliotecário");
			System.out.println("2 - Cadastrar Usuário");
			System.out.println("3 - Login Bibliotecário");
			System.out.println("4 - Login Usuário");
			System.out.println("5 - Cron Contabilizar Multas");
			System.out.println("6 - Listar Bibliotecários");
			System.out.println("7 - Listar Usuários");
			System.out.println("8 - Listar Exemplares");
			System.out.println("9 - Desativar Usuários");
			System.out.println("10 - Ativar Usuários");
			System.out.println("11 - Desativar Bibliotecário");
			System.out.println("12 - Ativar Bibliotecário");
			
			System.out.print("O que deseja fazer (digite o número correspondente das ações listadas acima)? ");
			int acaoI = Integer.parseInt(entrada.nextLine());
			
			switch (acaoI) {
				case 1:
					System.out.println("Cadastro de Bibliotecário:");
					System.out.print("Digite seu nome: ");
					String nome = entrada.nextLine();
					
					System.out.print("Crie seu Usuário: ");
					String usuario = entrada.nextLine();
					
					System.out.print("Crie uma senha: ");
					String senha = entrada.nextLine();
					
					System.out.println();
					Bibliotecario b = new Bibliotecario(bi.geraIdB(), nome, usuario, senha);
					b.gravarB();
					
					break;
				case 2:
					System.out.println("Cadastro Usuário: ");
					
					System.out.print("Digite seu nome: ");
					nome = entrada.nextLine();
					
					System.out.print("Crie seu Usuário: ");
					usuario = entrada.nextLine();
					
					System.out.print("Crie uma senha: ");
					senha = entrada.nextLine();
					
					Usuario u = new Usuario(ui.geraId(), nome, usuario, senha);
					u.gravarU();
					break;
				case 3:
					System.out.println("Login Bibliotecário: ");
					System.out.print("Digite seu Usuário: ");
					usuario = entrada.nextLine();
					
					System.out.print("Digite sua senha: ");
					senha = entrada.nextLine();
					b = bi.loginB(usuario, senha);
					
					while (b == null) {
						System.out.println("Login inválido!");
						System.out.print("Digite seu Usuário: ");
						usuario = entrada.nextLine();
						
						System.out.print("Digite sua senha: ");
						senha = entrada.nextLine();
						b = bi.loginB(usuario, senha);
					}
					
					System.out.println("Ações Bibliotecário:");
					System.out.println("1 - Cadastrar Exemplar");
					System.out.println("2 - Alterar Exemplar");
					System.out.println("3 - Deletar Exemplar");
					System.out.print("O que deseja fazer (digite o número correspondente das ações listadas acima)? ");
					int acaoB = Integer.parseInt(entrada.nextLine());
					
					switch (acaoB) {
						case 1:
							System.out.println("Cadastro de Livro/Exemplar: ");
							
							System.out.print("Digite o título do livro: ");
							String titulo = entrada.nextLine();
							
							System.out.print("Digite o(a) autor(a) do livro: ");
							String autor = entrada.nextLine();
							
							System.out.print("Digite o ISBN do livro: ");
							String isbn = entrada.nextLine();
							
							System.out.print("Digite o número do Exemplar: ");
							int numero = Integer.parseInt(entrada.nextLine());
							
							b.incluirExemplar(ei.geraId(), titulo, autor, isbn, numero, true);
							break;
						case 2:
							System.out.println("Alterar Livro/Exemplar: ");
							
							System.out.print("Digite id do Exemplar que deseja alterar: ");
							int id = Integer.parseInt(entrada.nextLine());
							
							System.out.print("Digite o título do livro: ");
							titulo = entrada.nextLine();
							
							System.out.print("Digite o(a) autor(a) do livro: ");
							autor = entrada.nextLine();
							
							System.out.print("Digite o ISBN do livro: ");
							isbn = entrada.nextLine();
							
							System.out.print("Digite o número do Exemplar: ");
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
							System.out.println("Ação inválida!");
							break;
					}
					
					break;
				case 4: 
					System.out.println("Login Usuário: ");
					System.out.print("Digite seu Usuário: ");
					usuario = entrada.nextLine();
					
					System.out.print("Digite sua senha: ");
					senha = entrada.nextLine();
					u = ui.loginU(usuario, senha);
					
					while (u == null) {
						System.out.println("Login inválido!");
						System.out.print("Digite seu Usuário: ");
						usuario = entrada.nextLine();
						
						System.out.print("Digite sua senha: ");
						senha = entrada.nextLine();
						u = ui.loginU(usuario, senha);
					}
					
					System.out.println("Ações Usuários:");
					System.out.println("1 - Realizar empréstimo");
					System.out.println("2 - Remover Empréstimo");
					System.out.println("3 - Renovar Emprestimo");
					System.out.println("4 - Verificar total de multa");
					System.out.println("5 - Pagar multa");
					System.out.println("6 - Listar meus empréstimos");
					System.out.print("O que deseja fazer (digite o número correspondente das ações listadas acima)? ");
					int acaoU = Integer.parseInt(entrada.nextLine());
					
					switch (acaoU) {
					case 1:
						System.out.println("Realizar Empréstimo: ");
						
						System.out.print("Digite o código do exemplar que deseja: ");
						int codigo = Integer.parseInt(entrada.nextLine());
						
						Exemplar ex = ei.buscaExemplar(codigo);
						
						if (ex != null && ex.isDisponivel()) {
							DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							String dataI = dt.format(LocalDateTime.now());
							LocalDate dtf = LocalDate.parse(dataI, dt);
							String dataF = dtf.plusDays(15).format(dt);
							if (u.addEmprestimo(bi, ex, dataI, dataF)) {
								
								System.out.println("Empréstimo realizado com sucesso!");
							} else {
								System.out.println("Exemplar indisponível!");
							}
							
						} else {
							System.out.println("Exemplar indisponível!");
						}
						
						break;
					case 2:
						System.out.println("Devolução empréstimo: ");
						
						System.out.print("Digite o código do exemplar que deseja devolver: ");
						codigo = Integer.parseInt(entrada.nextLine());
						
						ex = ei.buscaExemplar(codigo);
						
						if (ex != null) {
							if (u.removeEmprestimo(bi, ex)) {
								System.out.println("Devolução realizada com sucesso!");
							} else {
								System.out.println("Falha na devolução!");
							}
							
						} else {
							System.out.println("Não foi possível deletar pois o exemplar não existe!");
						}
						break;
					case 3:
						System.out.println("Renovação empréstimo: ");
						
						System.out.print("Digite o código do exemplar que deseja renovar: ");
						codigo = Integer.parseInt(entrada.nextLine());
						
						ex = ei.buscaExemplar(codigo);
						
						if (ex != null) {
							if (u.renovarEmprestimo(bi, ex)) {
								System.out.println("Renovação realizada com sucesso!");
							} else {
								System.out.println("Falha ao renovar!");
							}
						} else {
							System.out.println("Não foi possível renovar pois o exemplar não existe!");
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
								System.out.println("O valor digitado não corresponde ao valor total de multas.");
							}
						} else {
							System.out.println("Você não possui multa para pagar atualmente.");
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
						System.out.println("Ação Inválida!");
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
					System.out.println("Ação inválida!");
					break;
			}
			
			entrada.close();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
