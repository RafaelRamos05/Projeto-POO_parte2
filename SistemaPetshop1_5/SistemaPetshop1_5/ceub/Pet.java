package ceub;

public class Pet {
    private String nome;
    private String raca;
    private int idade;
    private double peso;
    private char sexo;

    public Pet(String nome, String raca, int idade, double peso, char sexo) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome inválido.");
        if (raca == null || raca.trim().isEmpty()) throw new IllegalArgumentException("Raça inválida.");
        if (idade < 0) throw new IllegalArgumentException("Idade inválida.");
        if (peso <= 0) throw new IllegalArgumentException("Peso inválido.");
        
        char s = Character.toUpperCase(sexo);
        if (s != 'F' && s != 'M') {
            throw new IllegalArgumentException("Sexo inválido. Use 'F' ou 'M'.");
        }

        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.peso = peso;
        this.sexo = s;
    }

    public String getNome() { return nome; }
    public String getRaca() { return raca; }
    public int getIdade() { return idade; }
    public double getPeso() { return peso; }
    public char getSexo() { return sexo; }

    @Override
    public String toString() {
        return nome + " (" + raca + ", " + sexo + ", " + idade + " anos, " + peso + "kg)";
    }
}