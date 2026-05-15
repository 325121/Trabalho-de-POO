public class Sala extends Espaco {
    private boolean projetor;
    private double precoProjetor;

    public Sala(String nome, boolean projetor,
                double valorHora, double taxaLimpeza, double precoProjetor) {
        super(nome, valorHora, taxaLimpeza);
        this.projetor = projetor;
        this.precoProjetor = precoProjetor;
    }

    @Override
    public double preco(Horario inicio, Horario fim) {
        // Multiplica o preço base do Espaço (horas + limpeza) por 4
        double total = super.preco(inicio, fim) * 4;

        // Adiciona o projetor se existir
        if (projetor) total += precoProjetor;

        return total;
    }

    @Override
    public boolean possuiAdicionalExtra() {
        return projetor;
    }

    @Override
    public String toString() {
        return descricao + (projetor ? " (Sala com Projetor)" : " (Sala sem Projetor)");
    }
}