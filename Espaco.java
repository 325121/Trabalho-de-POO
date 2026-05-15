import java.util.ArrayList;

public class Espaco {
    protected String descricao;
    protected double valorHora;
    protected double taxaLimpeza;
    protected ArrayList<Reserva> reservas;

    public Espaco(String descricao, double valorHora, double taxaLimpeza) {
        this.descricao = descricao;
        this.valorHora = valorHora;
        this.taxaLimpeza = taxaLimpeza;
        this.reservas = new ArrayList<>();
    }

    public boolean disponivel(Data d, Horario inicio, Horario fim, boolean extra) {
        // Verifica se o requisito do item extra coincide com o que o espaço oferece
        if (extra != possuiAdicionalExtra()) return false;

        for (Reserva r : reservas) {
            if (r.getData().equals(d)) {
                if (!(fim.compara(r.getInicio()) <= 0 ||
                        inicio.compara(r.getFim()) >= 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void adicionarReserva(Reserva r) {
        reservas.add(r);
    }

    public double preco(Horario inicio, Horario fim) {
        int horas = fim.getHora() - inicio.getHora();
        if (fim.getMin() > inicio.getMin()) horas++;
        if (horas <= 0) horas = 1;
        return (valorHora * horas) + taxaLimpeza;
    }

    public boolean possuiAdicionalExtra() {
        return true;
    }

    @Override
    public String toString() {
        return descricao;
    }
}