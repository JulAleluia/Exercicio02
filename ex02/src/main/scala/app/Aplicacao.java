package app;

import java.util.List;
import java.util.Scanner;

import dao.DAO;
import dao.UsuarioDAO;
import model.Usuario;

public class Aplicacao {

    private static final Scanner scanner = new Scanner(System.in);
    private static UsuarioDAO usuarioDAO;

    public static void main(String[] args) throws Exception {
        usuarioDAO = new UsuarioDAO();
        exibirMenu();
    }

    private static void exibirMenu() throws Exception {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n==== Menu ====");
            System.out.println("1. Listar usuários");
            System.out.println("2. Inserir usuário");
            System.out.println("3. Excluir usuário");
            System.out.println("4. Atualizar usuário");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    listarUsuarios();
                    break;
                case 2:
                    inserirUsuario();
                    break;
                case 3:
                    excluirUsuario();
                    break;
                case 4:
                    atualizarUsuario();
                    break;
                case 5:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private static void listarUsuarios() throws Exception {
        System.out.println("\n==== Listagem de usuários ====");
        List<Usuario> usuarios = usuarioDAO.getAll();
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    private static void inserirUsuario() throws Exception {
        System.out.println("\n==== Inserir usuário ====");
        System.out.print("Digite o código: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Digite o login: ");
        String login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();
        System.out.print("Digite o sexo (M/F): ");
        char sexo = scanner.nextLine().charAt(0);

        Usuario usuario = new Usuario(codigo, login, senha, sexo);
        if (usuarioDAO.insert(usuario)) {
            System.out.println("Usuário inserido com sucesso.");
        } else {
            System.out.println("Falha ao inserir o usuário.");
        }
    }

    private static void excluirUsuario() throws Exception {
        System.out.println("\n==== Excluir usuário ====");
        System.out.print("Digite o código do usuário a ser excluído: ");
        int codigo = scanner.nextInt();

        if (usuarioDAO.delete(codigo)) {
            System.out.println("Usuário excluído com sucesso.");
        } else {
            System.out.println("Falha ao excluir o usuário.");
        }
    }

    private static void atualizarUsuario() throws Exception {
        System.out.println("\n==== Atualizar usuário ====");
        System.out.print("Digite o código do usuário a ser atualizado: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Digite a nova senha: ");
        String novaSenha = scanner.nextLine();

        Usuario usuario = usuarioDAO.getByCodigo(codigo);
        if (usuario != null) {
            usuario.setSenha(novaSenha);
            if (usuarioDAO.update(usuario)) {
                System.out.println("Senha atualizada com sucesso.");
            } else {
                System.out.println("Falha ao atualizar a senha.");
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
}
