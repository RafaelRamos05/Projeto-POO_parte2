package ceub;

public class Adestramento extends Servico {
    private static final double PRECO_BASE = 120.0;

    public Adestramento(String data, Pet pet) {
        super(data, pet, PRECO_BASE, "Adestramento");
    }
}