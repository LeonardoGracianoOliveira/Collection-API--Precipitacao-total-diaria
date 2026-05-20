import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstatisticasPrecipitacao {

    private List<Precipitacao> registros;

    public EstatisticasPrecipitacao(List<Precipitacao> registros) {
        this.registros = registros;
    }

    // 1. Total de precipitação para cada mês do ano
    public double getTotalPrecipitacaoMes(int ano, int mes) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano && p.getData().getMonthValue() == mes)
                .mapToDouble(Precipitacao::getValor)
                .sum();
    }

    // 2. Dia de maior precipitação no ano
    public Optional<Precipitacao> getDiaMaiorPrecipitacao(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .max(Comparator.comparingDouble(Precipitacao::getValor));
    }

    // 3. Dia de menor precipitação no ano
    public Optional<Precipitacao> getDiaMenorPrecipitacao(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .min(Comparator.comparingDouble(Precipitacao::getValor));
    }

    // 4. Mês de maior precipitação no ano
    public Month getMesMaiorPrecipitacao(int ano) {
        return obterAgrupamentoMensal(ano).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // 5. Mês de menor precipitação no ano
    public Month getMesMenorPrecipitacao(int ano) {
        return obterAgrupamentoMensal(ano).entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // 6. Média de precipitação do ano
    public double getMediaPrecipitacaoAno(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .mapToDouble(Precipitacao::getValor)
                .average()
                .orElse(0.0);
    }

    // 7. Média da precipitação de cada mês do ano
    public double getMediaPrecipitacaoMes(int ano, int mes) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano && p.getData().getMonthValue() == mes)
                .mapToDouble(Precipitacao::getValor)
                .average()
                .orElse(0.0);
    }

    // 8. Os 10 Dias de maior precipitação no ano
    public List<Precipitacao> getTop10DiasMaiorPrecipitacao(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .sorted(Comparator.comparingDouble(Precipitacao::getValor).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    // Método auxiliar privado para agrupar e somar dados por mês
    private Map<Month, Double> obterAgrupamentoMensal(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .collect(Collectors.groupingBy(
                        p -> p.getData().getMonth(),
                        Collectors.summingDouble(Precipitacao::getValor)
                ));
    }
}