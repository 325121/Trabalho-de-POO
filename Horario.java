public class Horario {
    private int hora;
    private int min;

    public Horario(int hora, int min) {
        this.hora = hora;
        this.min = min;
    }

    public int compara(Horario h2) {
        if (this.hora < h2.hora || (this.hora == h2.hora && this.min < h2.min))
            return -1;

        if (this.hora > h2.hora || (this.hora == h2.hora && this.min > h2.min))
            return 1;

        return 0;
    }

    public int getHora() {
        return hora;
    }

    public int getMin() {
        return min;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", this.hora, this.min);
    }
}