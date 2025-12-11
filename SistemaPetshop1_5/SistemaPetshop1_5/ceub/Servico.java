package ceub;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Servico {
    private static int ID_COUNTER = 1;
    private final int id;
    private LocalDate dataRealizacao;
    private Pet pet;
    private double preco;
    private String descricao;
    protected static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected Servico(String data, Pet pet, double preco, String descricao) {
        this.id = ID_COUNTER++;
        this.dataRealizacao = parseData(data);
        this.pet = pet;
        this.preco = preco;
        this.descricao = descricao;
    }

    private LocalDate parseData(String data) {
        try {
            return LocalDate.parse(data, FORMAT);
        } catch (Exception e) {
            return LocalDate.now(); // se formato inválido, usa data atual
        }
    }

    public int getId() { return id; }
    public LocalDate getDataRealizacao() { return dataRealizacao; }
    public Pet getPet() { return pet; }
    public double getPreco() { return preco; }
    public String getDescricao() { return descricao; }
    
    // Método auxiliar útil se quiser verificar datas futuras
    public boolean isAgendadoParaFuturo() {
        return dataRealizacao.isAfter(LocalDate.now());
    }

    @Override
    public String toString() {
        return "[" + id + "] " + descricao + " para " + pet.getNome() + " em " +
                dataRealizacao.format(FORMAT) + " - R$ " + String.format("%.2f", preco);
    }
}