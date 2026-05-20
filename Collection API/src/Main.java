import java.util.List;

public class Main {
    public static void main(String[] args) {
        String caminhoCSV = "PluviometriaFuncemeNormalizada_2026-05-19T21_02_25.csv";

        List<Precipitacao> dados = LeitorCSV.lerDados(caminhoCSV);

        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado. Verifique se o arquivo está na raiz do projeto e possui o nome correto.");
            return;
        }

        EstatisticasPrecipitacao stats = new EstatisticasPrecipitacao(dados);

        int anoAlvo = 2025;
        int mesAlvo = 2;

        System.out.println("=== ESTATÍSTICAS DE PRECIPITAÇÃO - ACARAÚ (" + anoAlvo + ") ===");

        System.out.printf("Total de precipitação no mês %d: %.2f mm\n",
                mesAlvo, stats.getTotalPrecipitacaoMes(anoAlvo, mesAlvo));

        stats.getDiaMaiorPrecipitacao(anoAlvo).ifPresent(d ->
                System.out.println("Dia de maior precipitação: " + d));
        stats.getDiaMenorPrecipitacao(anoAlvo).ifPresent(d ->
                System.out.println("Dia de menor precipitação: " + d));

        System.out.println("Mês de MAIOR precipitação: " + stats.getMesMaiorPrecipitacao(anoAlvo));
        System.out.println("Mês de MENOR precipitação: " + stats.getMesMenorPrecipitacao(anoAlvo));

        System.out.printf("Média de precipitação do ano: %.2f mm/dia\n", stats.getMediaPrecipitacaoAno(anoAlvo));

        System.out.printf("Média de precipitação no mês %d: %.2f mm/dia\n",
                mesAlvo, stats.getMediaPrecipitacaoMes(anoAlvo, mesAlvo));

        System.out.println("\nTop 10 dias de maior precipitação:");
        List<Precipitacao> top10 = stats.getTop10DiasMaiorPrecipitacao(anoAlvo);
        for (int i = 0; i < top10.size(); i++) {
            System.out.println((i + 1) + "º -> " + top10.get(i));
        }
    }
}