import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String caminhoCSV = "PluviometriaFuncemeNormalizada_2026-05-19T21_02_25.csv";

        List<Precipitacao> dados = LeitorCSV.lerDados(caminhoCSV);

        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado no arquivo.");
            return;
        }

        EstatisticasPrecipitacao stats = new EstatisticasPrecipitacao(dados);
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=======================================================");
            System.out.println("       SISTEMA DE ESTATÍSTICAS DE PRECIPITAÇÃO         ");
            System.out.println("=======================================================");
            System.out.println("1 - Total de precipitação para cada mês do ano");
            System.out.println("2 - Dia de maior e menor precipitação no ano");
            System.out.println("3 - Mês de maior e menor precipitação no ano");
            System.out.println("4 - Média de precipitação do ano");
            System.out.println("5 - Média da precipitação de cada mês do ano");
            System.out.println("6 - Os 10 Dias de maior precipitação no ano");
            System.out.println("0 - Sair do programa");
            System.out.print("-> Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Digite um número.");
                continue;
            }

            if (opcao == 0) {
                System.out.println("Encerrando o programa...");
                break;
            }

            if (opcao >= 1 && opcao <= 6) {
                System.out.print("-> Digite o ano desejado (ex: 2025): ");
                int anoSelecionado;
                try {
                    anoSelecionado = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ano inválido. Retornando ao menu.");
                    continue;
                }

                System.out.println("\n--- RESULTADOS PARA O ANO DE " + anoSelecionado + " ---");

                switch (opcao) {
                    case 1:
                        Map<Month, Double> totaisMensais = stats.getTotalPrecipitacaoPorMesNoAno(anoSelecionado);
                        if (totaisMensais.isEmpty()) System.out.println("Sem dados para este ano.");
                        else totaisMensais.forEach((mes, total) -> System.out.printf("Mês %s: %.2f mm\n", mes, total));
                        break;

                    case 2:
                        stats.getDiaMaiorPrecipitacao(anoSelecionado).ifPresentOrElse(
                                d -> System.out.println("Dia de MAIOR precipitação: " + d),
                                () -> System.out.println("Sem dados para este ano.")
                        );
                        stats.getDiaMenorPrecipitacao(anoSelecionado).ifPresent(d ->
                                System.out.println("Dia de MENOR precipitação: " + d));
                        break;

                    case 3:
                        Month mesMaior = stats.getMesMaiorPrecipitacao(anoSelecionado);
                        Month mesMenor = stats.getMesMenorPrecipitacao(anoSelecionado);
                        if (mesMaior != null) {
                            System.out.println("Mês com MAIOR precipitação: " + mesMaior);
                            System.out.println("Mês com MENOR precipitação: " + mesMenor);
                        } else {
                            System.out.println("Sem dados para este ano.");
                        }
                        break;

                    case 4:
                        double mediaAnual = stats.getMediaPrecipitacaoAno(anoSelecionado);
                        if(mediaAnual > 0) System.out.printf("Média anual de precipitação: %.2f mm/dia\n", mediaAnual);
                        else System.out.println("Sem dados para este ano.");
                        break;

                    case 5:
                        Map<Month, Double> mediasMensais = stats.getMediaPrecipitacaoPorMesNoAno(anoSelecionado);
                        if (mediasMensais.isEmpty()) System.out.println("Sem dados para este ano.");
                        else mediasMensais.forEach((mes, media) -> System.out.printf("Média do mês %s: %.2f mm/dia\n", mes, media));
                        break;

                    case 6:
                        List<Precipitacao> top10 = stats.getTop10DiasMaiorPrecipitacao(anoSelecionado);
                        if (top10.isEmpty()) {
                            System.out.println("Sem dados para este ano.");
                        } else {
                            System.out.println("Top 10 dias com mais chuva:");
                            for (int i = 0; i < top10.size(); i++) {
                                System.out.println((i + 1) + "º -> " + top10.get(i));
                            }
                        }
                        break;
                }
            } else {
                System.out.println("Opção inexistente. Tente novamente.");
            }
        }

        scanner.close();
    }
}
