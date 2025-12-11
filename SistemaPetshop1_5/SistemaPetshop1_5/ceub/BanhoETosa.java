package ceub;

public class BanhoETosa extends Servico {
    private static final double PRECO_BASE = 80.0;
    
    public BanhoETosa(String data, Pet pet) {
        super(data, pet, PRECO_BASE, "Banho e Tosa");
    }
}