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

    public Map<Month, Double> getTotalPrecipitacaoPorMesNoAno(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .collect(Collectors.groupingBy(
                        p -> p.getData().getMonth(),
                        Collectors.summingDouble(Precipitacao::getValor)
                ));
    }

    public Optional<Precipitacao> getDiaMaiorPrecipitacao(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .max(Comparator.comparingDouble(Precipitacao::getValor));
    }

    public Optional<Precipitacao> getDiaMenorPrecipitacao(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .min(Comparator.comparingDouble(Precipitacao::getValor));
    }

    public Month getMesMaiorPrecipitacao(int ano) {
        return getTotalPrecipitacaoPorMesNoAno(ano).entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Month getMesMenorPrecipitacao(int ano) {
        return getTotalPrecipitacaoPorMesNoAno(ano).entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public double getMediaPrecipitacaoAno(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .mapToDouble(Precipitacao::getValor)
                .average()
                .orElse(0.0);
    }

    public Map<Month, Double> getMediaPrecipitacaoPorMesNoAno(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .collect(Collectors.groupingBy(
                        p -> p.getData().getMonth(),
                        Collectors.averagingDouble(Precipitacao::getValor)
                ));
    }

    public List<Precipitacao> getTop10DiasMaiorPrecipitacao(int ano) {
        return registros.stream()
                .filter(p -> p.getData().getYear() == ano)
                .sorted(Comparator.comparingDouble(Precipitacao::getValor).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }
}
