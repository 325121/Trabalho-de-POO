import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Classe com as rotinas de entrada e saída do projeto
 * @author Hilario Seibel Junior, Apoema Moreira Alves Fernandes e Matheus de Jesus Abdon
 */
public class Entrada {

    public Scanner input;

    public Entrada() {
        try {
            this.input = new Scanner(new FileInputStream("input.txt")).useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            this.input = new Scanner(System.in).useLocale(Locale.US);
        }
    }

    public void fechar() {input.close();}

    public String lerLinha(String msg) {
        System.out.print(msg);
        String linha = this.input.nextLine();
        while (linha.charAt(0) == '#') linha = this.input.nextLine();
        return linha;
    }

    public int lerInteiro(String msg) {
        String linha = this.lerLinha(msg);
        return Integer.parseInt(linha);
    }

    public double lerDouble(String msg) {
        return Double.parseDouble(this.lerLinha(msg));
    }

    public int menu() {
        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Cadastros\n" +
                "2) Reservas\n" +
                "0) Sair\n";
        int op = this.lerInteiro(msg);
        while (op < 0 || op > 2) {
            System.out.println("\"Opção inválida. Tente novamente: \"");
            op = this.lerInteiro(msg);
        }
        return op;
    }

    public Sistema criarSistema() {
        System.out.println("Iniciando o sistema...");
        double valorHora    = this.lerDouble("Digite o valor por hora para usar um espaço: R$ ");
        double taxaLimpeza  = this.lerDouble("Digite a taxa de limpeza: R$ ");
        double precoProjetor= this.lerDouble("Digite o valor extra para usar o projetor: R$ ");
        double precoMonitor = this.lerDouble("Digite o valor para usar o monitor extra: R$ ");
        return new Sistema(valorHora, taxaLimpeza, precoProjetor, precoMonitor);
    }


    public void menuCadastro(Sistema s) {
        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Ver clientes\n" +
                "2) Ver salas\n" +
                "3) Ver estações de trabalho\n" +
                "4) Cadastrar cliente\n" +
                "5) Cadastrar sala\n" +
                "6) Cadastrar estação de trabalho\n" +
                "0) Voltar\n";
        int op = this.lerInteiro(msg);
        while (op < 0 || op > 6) {
            System.out.println("Opção inválida. Tente novamente: ");
            op = this.lerInteiro(msg);
        }
        switch (op) {
            case 1 -> listarClientes(s);
            case 2 -> listarSalas(s);
            case 3 -> listarEstacoes(s);
            case 4 -> cadastrarCliente(s);
            case 5 -> cadastrarSala(s);
            case 6 -> cadastrarEstacao(s);
        }
    }

    public void listarClientes(Sistema s) {
        System.out.println("*********************************");
        ArrayList<Cliente> clientes = s.getClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("Clientes cadastrados:");
            for (Cliente c : clientes) System.out.println(c);
        }
    }

    public void listarSalas(Sistema s) {
        System.out.println("*********************************");
        ArrayList<Sala> salas = s.getSalas();
        if (salas.isEmpty()) {
            System.out.println("Nenhuma sala cadastrada.");
        } else {
            System.out.println("Salas cadastradas:");
            for (Sala sala : salas) System.out.println(sala);
        }
    }

    public void listarEstacoes(Sistema s) {
        System.out.println("*********************************");
        ArrayList<Estacao> estacoes = s.getEstacoes();
        if (estacoes.isEmpty()) {
            System.out.println("Nenhuma estação cadastrada.");
        } else {
            System.out.println("Estações de Trabalho cadastradas:");
            for (Estacao e : estacoes) System.out.println(e);
        }
    }

    public void cadastrarCliente(Sistema s) {
        System.out.println("Cadastrando cliente.");
        String nome  = lerLinha("Digite o nome do cliente: ");
        String cpf   = lerLinha("Digite o CPF do cliente: ");
        String email = lerLinha("Digite o email do cliente: ");
        String senha = lerLinha("Digite a senha do cliente: ");
        if (s.getCliente(cpf) == null) {
            s.cadastrar(new Cliente(nome, cpf, email, senha));
        } else {
            System.out.println("CPF já cadastrado.");
        }
    }

    public void cadastrarSala(Sistema s) {
        System.out.println("Cadastrando sala.");
        String nome = lerLinha("Digite o nome da sala: ");
        String resp = lerLinha("Possui projetor? (s/n): ");
        boolean projetor = resp.equalsIgnoreCase("s");
        s.cadastrar(new Sala(nome, projetor,
                s.getValorHora(), s.getTaxaLimpeza(), s.getPrecoProjetor()));
    }

    public void cadastrarEstacao(Sistema s) {
        System.out.println("Cadastrando estação.");
        String nome = lerLinha("Digite o nome da estação de trabalho: ");
        String resp = lerLinha("Possui monitor extra? (s/n): ");
        boolean monitor = resp.equalsIgnoreCase("s");
        s.cadastrar(new Estacao(nome, monitor,
                s.getValorHora(), s.getTaxaLimpeza(), s.getPrecoMonitor()));
    }

    public void menuReservas(Sistema s) {
        String msg = "*********************\n" +
                "Escolha uma opção:\n" +
                "1) Ver reservas\n" +
                "2) Ver reservas por data\n" +
                "3) Ver reservas por cliente\n" +
                "4) Fazer reserva (dia inteiro)\n" +
                "5) Fazer reserva (turno inteiro)\n" +
                "6) Fazer reserva (horário específico)\n" +
                "0) Voltar\n";
        int op = this.lerInteiro(msg);
        while (op < 0 || op > 6) {
            System.out.println("Opção inválida.");
            op = this.lerInteiro(msg);
        }
        switch (op) {
            case 1 -> listarReservas(s);
            case 2 -> listarReservasData(s);
            case 3 -> listarReservasCliente(s);
            case 4 -> reservarDia(s);
            case 5 -> reservarTurno(s);
            case 6 -> reservarHorario(s);
        }
    }

    public void listarReservas(Sistema s) {
        ArrayList<Reserva> reservas = s.getReservas();
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada.");
        } else {
            System.out.println("Reservas cadastradas:");
            for (Reserva r : reservas) System.out.println(r);
        }
    }

    public void listarReservasData(Sistema s) {
        Data d = lerData();
        ArrayList<Reserva> reservas = s.getReservas(d);
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada nesta data.");
        } else {
            System.out.println("Reservas cadastradas nesta data:");
            for (Reserva r : reservas) System.out.println(r);
        }
    }

    public void listarReservasCliente(Sistema s) {
        listarClientes(s);
        String cpf = lerLinha("Digite o CPF do cliente: ");
        Cliente c = s.getCliente(cpf);
        if (c == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        ArrayList<Reserva> reservas = s.getReservas(c);
        if (reservas.isEmpty()) {
            System.out.println("Nenhuma reserva cadastrada para este cliente.");
        } else {
            System.out.println("Reservas cadastradas para este cliente:");
            for (Reserva r : reservas) System.out.println(r);
        }
    }

    private String lerTipo() {
        return lerLinha("Deseja reservar uma sala ou estação de trabalho? (s/e): ");
    }

    private boolean lerExtra(String tipo) {
        if (tipo.equalsIgnoreCase("s")) {
            return lerLinha("Deseja reservar sala com projetor? (s/n): ").equalsIgnoreCase("s");
        } else {
            return lerLinha("Deseja estação com monitor extra? (s/n): ").equalsIgnoreCase("s");
        }
    }

    private Data lerData() {
        System.out.println("Escolha uma data (dd/mm/aaaa):");
        int dia = lerInteiro("Dia: ");
        int mes = lerInteiro("Mês: ");
        int ano = lerInteiro("Ano: ");
        return new Data(dia, mes, ano);
    }

    private Cliente lerCliente(Sistema s) {
        System.out.println("*********************************");
        listarClientes(s);
        String cpf = lerLinha("Digite o CPF do cliente: ");
        return s.getCliente(cpf);
    }

    public void reservarDia(Sistema s) {
        String tipo  = lerTipo();
        boolean extra = lerExtra(tipo);
        Data d = lerData();
        Cliente c = lerCliente(s);
        if (c == null) { System.out.println("Cliente não encontrado."); return; }
        boolean ok = s.reservar(tipo, d, c, extra);
        System.out.println(ok ? "Reserva realizada com sucesso!" : "Reserva não realizada.");
    }

    public void reservarTurno(Sistema s) {
        String tipo  = lerTipo();
        boolean extra = lerExtra(tipo);
        Data d = lerData();
        String turno = lerLinha("Escolha um turno: matutino, vespertino ou noturno (m/v/n): ");
        Cliente c = lerCliente(s);
        if (c == null) { System.out.println("Cliente não encontrado."); return; }
        boolean ok = s.reservar(tipo, d, turno, c, extra);
        System.out.println(ok ? "Reserva realizada com sucesso!" : "Reserva não realizada.");
    }

    public void reservarHorario(Sistema s) {
        String tipo  = lerTipo();
        boolean extra = lerExtra(tipo);
        Data d = lerData();
        System.out.println("Escolha um horário (hh:mm):");
        int hIni = lerInteiro("Hora: ");
        int mIni = lerInteiro("Minuto: ");
        System.out.println("Escolha um horário (hh:mm):");
        int hFim = lerInteiro("Hora: ");
        int mFim = lerInteiro("Minuto: ");
        Horario inicio = new Horario(hIni, mIni);
        Horario fim    = new Horario(hFim, mFim);
        Cliente c = lerCliente(s);
        if (c == null) { System.out.println("Cliente não encontrado."); return; }
        boolean ok = s.reservar(tipo, d, inicio, fim, c, extra);
        System.out.println(ok ? "Reserva realizada com sucesso!" : "Reserva não realizada.");
    }
}