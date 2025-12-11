package ceub;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private List<Pet> pets = new ArrayList<>();
    private List<Servico> servicos = new ArrayList<>();

    public Cliente(String nome, String telefone, String email, String cpf) {
        // Validação Nome
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        // Validação Telefone (Aceita números, parenteses, traço e espaço)
        if (telefone == null || telefone.trim().isEmpty() || !telefone.matches("[0-9() -]+")) {
            throw new IllegalArgumentException("Telefone inválido (use apenas números, (), espaço ou hífen).");
        }
        
        // Validação Email
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        // Validação CPF
        if (cpf == null || cpf.trim().isEmpty() || !cpf.matches("[0-9-]+")) {
            throw new IllegalArgumentException("CPF inválido (use apenas números e hífen).");
        }

        this.nome = nome.trim();
        this.telefone = telefone.trim();
        this.email = email.trim();
        this.cpf = cpf.trim();
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }
    public List<Pet> getPets() { return pets; }
    public List<Servico> getServicos() { return servicos; }

    public void adicionarPet(Pet pet) {
        if (pet == null) throw new IllegalArgumentException("Pet inválido.");
        pets.add(pet);
    }

    public boolean removerPet(int indice) {
        if (indice < 0 || indice >= pets.size()) return false;
        Pet p = pets.get(indice);
        
        // Verifica se o pet tem serviços ativos antes de remover
        boolean hasService = false;
        for (Servico s : servicos) {
            if (s.getPet().equals(p)) {
                hasService = true;
                break;
            }
        }
        if (hasService) return false;
        
        pets.remove(indice);
        return true;
    }

    public void adicionarServico(Servico servico) {
        if (servico == null) throw new IllegalArgumentException("Serviço inválido.");
        servicos.add(servico);
    }

    public boolean cancelarServico(int indice) {
        if (indice < 0 || indice >= servicos.size()) return false;
        servicos.remove(indice);
        return true;
    }
    
    public List<Servico> buscarServicosPorPet(Pet pet) {
        List<Servico> result = new ArrayList<>();
        for (Servico s : servicos) {
            if (s.getPet().equals(pet)) result.add(s);
        }
        return result;
    }
    
    // --- MÉTODOS QUE FALTAVAM (Reinseridos aqui) ---
    
    public void listarPets() {
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado para " + nome);
            return;
        }
        System.out.println("\n=== PETS DE " + nome.toUpperCase() + " ===");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + ". " + pets.get(i));
        }
    }

    public void listarServicos() {
        if (servicos.isEmpty()) {
            System.out.println("\n" + nome + " não contratou nenhum serviço.");
            return;
        }
        System.out.println("\n=== SERVIÇOS CONTRATADOS POR " + nome.toUpperCase() + " ===");
        for (int i = 0; i < servicos.size(); i++) {
            System.out.println((i + 1) + ". " + servicos.get(i));
        }
    }

    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ")";
    }
}