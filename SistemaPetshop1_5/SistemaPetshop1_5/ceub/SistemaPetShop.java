package ceub;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaPetShop {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Cliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInt();
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> buscarCliente();
                case 4 -> excluirCliente();
                case 6 -> listarPets();
                case 7 -> excluirPet();
                case 8 -> contratarServico();
                case 9 -> contratarPacote();
                case 10 -> listarServicos();
                case 11 -> cancelarServico();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA PETSHOP ===");
        System.out.println("1. Cadastrar Cliente + Pet");
        System.out.println("2. Listar Clientes");
        System.out.println("3. Buscar Cliente");
        System.out.println("4. Excluir Cliente");
        System.out.println("6. Listar Pets");
        System.out.println("7. Excluir Pet");
        System.out.println("8. Contratar Serviço Avulso");
        System.out.println("9. Contratar Pacote");
        System.out.println("10. Listar Serviços");
        System.out.println("11. Cancelar Serviço");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    private static int lerInt() {
        try { return Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { return -1; }
    }
    private static double lerDouble() {
        try { return Double.parseDouble(scanner.nextLine().trim()); } catch (Exception e) { return -1; }
    }

    private static void cadastrarCliente() {
        System.out.println("\n=== Cadastro de Cliente ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("CPF: "); // Removido "apenas números"
        String cpf = scanner.nextLine();

        Cliente cliente;
        try {
            cliente = new Cliente(nome, telefone, email, cpf);
        } catch (Exception e) {
            System.out.println("X Erro ao criar cliente: " + e.getMessage());
            return;
        }

        System.out.println("\n=== Cadastro do Pet ===");
        System.out.print("Nome do pet: ");
        String pNome = scanner.nextLine();
        System.out.print("Raça: ");
        String raca = scanner.nextLine();
        
        System.out.print("Sexo (F/M): ");
        String sInput = scanner.nextLine();
        char sexo = sInput.isEmpty() ? ' ' : sInput.charAt(0);

        System.out.print("Idade: ");
        int idade = lerInt();
        System.out.print("Peso (kg): ");
        double peso = lerDouble();

        try {
            Pet pet = new Pet(pNome, raca, idade, peso, sexo);
            cliente.adicionarPet(pet);
            clientes.add(cliente);
            System.out.println("Cliente e Pet cadastrados com sucesso!");
        } catch (Exception e) {
            System.out.println("X Erro ao cadastrar pet: " + e.getMessage());
        }
    }
    
    // Demais métodos permanecem similares aos anteriores
    private static void listarClientes() {
        if (clientes.isEmpty()) { System.out.println("Nenhum cliente."); return; }
        for (int i = 0; i < clientes.size(); i++) System.out.println((i+1) + ". " + clientes.get(i));
    }
    
    private static void buscarCliente() {
        System.out.print("Busca: "); String b = scanner.nextLine().toLowerCase();
        for(Cliente c : clientes) if(c.getNome().toLowerCase().contains(b)) System.out.println(c);
    }

    private static void excluirCliente() {
        listarClientes();
        if(clientes.isEmpty()) return;
        System.out.print("Nº Cliente: "); int idx = lerInt() - 1;
        if(idx < 0 || idx >= clientes.size()) return;
        
        Cliente c = clientes.get(idx);
        if(!c.getServicos().isEmpty()) { System.out.println("Possui serviços."); return; }
        
        boolean petServ = false;
        for(Pet p : c.getPets()) if(!c.buscarServicosPorPet(p).isEmpty()) petServ = true;
        
        if(petServ) System.out.println("Pets com serviços.");
        else { clientes.remove(idx); System.out.println("Removido."); }
    }

    private static void listarPets() {
        listarClientes();
        System.out.print("Nº Cliente: "); int idx = lerInt() - 1;
        if(idx >= 0 && idx < clientes.size()) clientes.get(idx).listarPets();
    }
    
    private static void excluirPet() {
        listarClientes();
        System.out.print("Nº Cliente: "); int idx = lerInt() - 1;
        if(idx < 0 || idx >= clientes.size()) return;
        Cliente c = clientes.get(idx);
        c.listarPets();
        System.out.print("Nº Pet: "); int pIdx = lerInt() - 1;
        if(c.removerPet(pIdx)) System.out.println("Pet removido.");
        else System.out.println("Erro ao remover pet.");
    }
    
    private static void contratarServico() {
        listarClientes();
        if(clientes.isEmpty()) return;
        System.out.print("Nº Cliente: "); int idx = lerInt() - 1;
        if(idx < 0 || idx >= clientes.size()) return;
        Cliente c = clientes.get(idx);
        if(c.getPets().isEmpty()) return;
        c.listarPets();
        System.out.print("Nº Pet: "); int pIdx = lerInt() - 1;	
        if(pIdx < 0 || pIdx >= c.getPets().size()) return;
        Pet pet = c.getPets().get(pIdx);
        System.out.println("1. Banho (80) 2. Consulta (150)");
        int t = lerInt();
        System.out.print("Data: "); String d = scanner.nextLine();
        if(t==1) c.adicionarServico(new BanhoETosa(d, pet));
        else if(t==2) c.adicionarServico(new ConsultaVeterinaria(d, pet));
    }
    
    private static void contratarPacote() { System.out.println("Funcionalidade similar ao contratarServico."); }
    private static void listarServicos() { 
        listarClientes(); System.out.print("Nº Cliente: "); int idx = lerInt()-1; 
        if(idx>=0 && idx<clientes.size()) clientes.get(idx).listarServicos(); 
    }
    private static void cancelarServico() {
         listarClientes(); System.out.print("Nº Cliente: "); int idx = lerInt()-1;
         if(idx>=0 && idx<clientes.size()) {
             Cliente c = clientes.get(idx); c.listarServicos();
             System.out.print("Nº Serviço: "); int sIdx = lerInt()-1;
             if(c.cancelarServico(sIdx)) System.out.println("Cancelado.");
         }
    }
}