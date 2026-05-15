import java.util.ArrayList;

public class Sistema {

    private ArrayList<Cliente> clientes;
    private ArrayList<Sala> salas;
    private ArrayList<Estacao> estacoes;
    private ArrayList<Reserva> reservas;

    private double valorHora;
    private double taxaLimpeza;
    private double precoProjetor;
    private double precoMonitor;

    public Sistema(double valorHora, double taxaLimpeza,
                   double precoProjetor, double precoMonitor) {
        this.valorHora = valorHora;
        this.taxaLimpeza = taxaLimpeza;
        this.precoProjetor = precoProjetor;
        this.precoMonitor = precoMonitor;

        clientes = new ArrayList<>();
        salas = new ArrayList<>();
        estacoes = new ArrayList<>();
        reservas = new ArrayList<>();
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public Cliente getCliente(String cpf) {
        for (Cliente c : clientes)
            if (c.getCpf().equals(cpf)) return c;
        return null;
    }

    public void cadastrar(Cliente c) {
        clientes.add(c);
    }

    public ArrayList<Sala> getSalas() {
        return salas;
    }

    public void cadastrar(Sala s) {
        salas.add(s);
    }

    public ArrayList<Estacao> getEstacoes() {
        return estacoes;
    }

    public void cadastrar(Estacao e) {
        estacoes.add(e);
    }

    // Reserva com horário específico
    public boolean reservar(String tipo, Data d, Horario inicio, Horario fim,
                            Cliente c, boolean extra) {
        ArrayList<? extends Espaco> lista = tipo.equalsIgnoreCase("s") ? salas : estacoes;
        for (Espaco esp : lista) {
            if (esp.disponivel(d, inicio, fim, extra)) {
                Reserva r = new Reserva(d, inicio, fim, esp, c);
                esp.adicionarReserva(r);
                reservas.add(r);
                return true;
            }
        }
        return false;
    }

    // Reserva para dia inteiro (08:00 às 22:00)
    public boolean reservar(String tipo, Data d, Cliente c, boolean extra) {
        return reservar(tipo, d, new Horario(8, 0), new Horario(22, 0), c, extra);
    }

    // Reserva por turno
    public boolean reservar(String tipo, Data d, String turno, Cliente c, boolean extra) {
        Horario inicio, fim;
        switch (turno.toLowerCase()) {
            case "m" -> { inicio = new Horario(8, 0);  fim = new Horario(12, 0); }
            case "v" -> { inicio = new Horario(13, 0); fim = new Horario(17, 0); }
            case "n" -> { inicio = new Horario(18, 0); fim = new Horario(22, 0); }
            default  -> { return false; }
        }
        return reservar(tipo, d, inicio, fim, c, extra);
    }

    // Retorna todas as reservas
    public ArrayList<Reserva> getReservas() { return reservas; }

    // Filtra por data
    public ArrayList<Reserva> getReservas(Data d) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas)
            if (r.getData().equals(d)) resultado.add(r);
        return resultado;
    }

    // Filtra por cliente
    public ArrayList<Reserva> getReservas(Cliente c) {
        ArrayList<Reserva> resultado = new ArrayList<>();
        for (Reserva r : reservas)
            if (r.getCliente().getCpf().equals(c.getCpf())) resultado.add(r);
        return resultado;
    }

    public double getValorHora()    { return valorHora; }
    public double getTaxaLimpeza()  { return taxaLimpeza; }
    public double getPrecoProjetor(){ return precoProjetor; }
    public double getPrecoMonitor() { return precoMonitor; }
}