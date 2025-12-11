package ceub;

public class Hospedagem extends Servico {
    private static final double PRECO_BASE = 100.0; 

    public Hospedagem(String data, Pet pet) {
        super(data, pet, PRECO_BASE, "Hospedagem");
    }
}