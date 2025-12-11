package ceub;

import java.util.List;
import java.util.StringJoiner;

public class PacoteServicos extends Servico {
    private List<String> servicosInclusos;
    private double descontoPercent;

    public PacoteServicos(String data, Pet pet, List<String> servicosInclusos, double precoTotal, double descontoPercent) {
        super(data, pet, precoTotal * (1 - descontoPercent / 100.0), "Pacote");
        this.servicosInclusos = servicosInclusos;
        this.descontoPercent = descontoPercent;
    }
    
    public List<String> getServicosInclusos() { return servicosInclusos; }
    public double getDescontoPercent() { return descontoPercent; }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ");
        if (servicosInclusos != null) {
            servicosInclusos.forEach(sj::add);
        }
        return super.toString() + " (Inclui: " + sj.toString() + ") Desconto: " + descontoPercent + "%";
    }
}