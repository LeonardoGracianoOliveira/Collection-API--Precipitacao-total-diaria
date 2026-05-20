import java.time.LocalDate;

public class Precipitacao {
    private LocalDate data;
    private double valor;

    public Precipitacao(LocalDate data, double valor) {
        this.data = data;
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Data: " + data + " | Precipitação: " + valor + " mm";
    }
}