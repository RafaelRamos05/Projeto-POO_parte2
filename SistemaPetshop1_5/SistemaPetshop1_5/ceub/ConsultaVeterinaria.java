package ceub;

public class ConsultaVeterinaria extends Servico {
    private static final double PRECO_BASE = 150.0;
    
    public ConsultaVeterinaria(String data, Pet pet) {
        super(data, pet, PRECO_BASE, "Consulta Veterinária");
    }
}