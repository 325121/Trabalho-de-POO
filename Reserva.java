public class Reserva {

    private Data d;
    private Horario inicio;
    private Horario fim;
    private Espaco esp;
    private Cliente cli;

    public Reserva(Data d, Horario inicio, Horario fim, Espaco esp, Cliente cli) {
        this.d = d;
        this.inicio = inicio;
        this.fim = fim;
        this.esp = esp;
        this.cli = cli;
    }

    public Data getData() {
        return d;
    }

    public Horario getInicio() {
        return inicio;
    }

    public Horario getFim() {
        return fim;
    }

    public Espaco getEspaco() {
        return esp;
    }

    public Cliente getCliente() {
        return cli;
    }

    public double preco() {
        return esp.preco(inicio, fim);
    }

    @Override
    public String toString() {
        return "Reserva:\n" +
                "* Local: " + esp + "\n" +
                "* Data: " + d + "\n" +
                "* Horario: " + inicio + " - " + fim + "\n" +
                "* Cliente: " + cli + "\n" +
                "* Valor: R$ " + String.format("%.2f", preco()).replace('.', ',');
    }
}