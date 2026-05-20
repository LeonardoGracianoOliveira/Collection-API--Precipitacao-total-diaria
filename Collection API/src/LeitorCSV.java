import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {

    public static List<Precipitacao> lerDados(String caminhoArquivo) {
        List<Precipitacao> dados = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] colunas = linha.split(";");

                if(colunas.length >= 3) {
                    try {
                        double valor = Double.parseDouble(colunas[1].trim());
                        LocalDate data = LocalDate.parse(colunas[2].trim(), formatter);

                        dados.add(new Precipitacao(data, valor));
                    } catch (Exception e) {
                        System.err.println("Erro ao converter linha: " + linha);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }

        return dados;
    }
}