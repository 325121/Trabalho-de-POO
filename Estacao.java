public class Estacao extends Espaco {
    private boolean monitorExtra;
    private double precoMonitor;

    public Estacao(String descricao, boolean monitorExtra,
                   double valorHora, double taxaLimpeza, double precoMonitor) {
        super(descricao, valorHora, taxaLimpeza);
        this.monitorExtra = monitorExtra;
        this.precoMonitor = precoMonitor;
    }

    @Override
    public double preco(Horario inicio, Horario fim) {
        double total = super.preco(inicio, fim);
        if (monitorExtra) total += precoMonitor;
        return total;
    }

    @Override
    public boolean possuiAdicionalExtra() {
        return monitorExtra;
    }

    @Override
    public String toString() {
        return descricao + " (Estacao de Trabalho " +
                (monitorExtra ? "com Monitor Extra" : "sem Monitor Extra") + ")";
    }
}
