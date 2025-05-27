import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe principal que testa e compara as implementações das tabelas hash
 */
public class Main {
    public static void main(String[] args) {
        // Constantes de configuração
        final int TAMANHO_TABELA = 32;
        final String ARQUIVO_NOMES = "female_names.txt";
        String[] nomes = new String[5000];

        // Leitura do arquivo de nomes
        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_NOMES))) {
            String linha;
            int i = 0;
            while ((linha = leitor.readLine()) != null && i < 5000) {
                nomes[i++] = linha.trim();
            }
        } catch (IOException erro) {
            System.err.println("Erro ao ler arquivo: " + erro.getMessage());
            return;
        }

        // Criação das tabelas hash
        TabelaHashAbs tabelaHash1 = new TabelaHash1(TAMANHO_TABELA);
        TabelaHashAbs tabelaHash2 = new TabelaHash2(TAMANHO_TABELA);


        // Teste de inserção na primeira tabela
        long tempoInicio1 = System.nanoTime();
        for (String nome : nomes) {
            tabelaHash1.inserir(nome);
        }
        long tempoFim1 = System.nanoTime();

        // Teste de inserção na segunda tabela
        long tempoInicio2 = System.nanoTime();
        for (String nome : nomes) {
            tabelaHash2.inserir(nome);
        }
        long tempoFim2 = System.nanoTime();

        // Teste de busca na primeira tabela
        long tempoBuscaInicio1 = System.nanoTime();
        for (String nome : nomes) {
            tabelaHash1.contem(nome);
        }
        long tempoBuscaFim1 = System.nanoTime();

        // Teste de busca na segunda tabela
        long tempoBuscaInicio2 = System.nanoTime();
        for (String nome : nomes) {
            tabelaHash2.contem(nome);
        }
        long tempoBuscaFim2 = System.nanoTime();

           // Exibição dos resultados
        System.out.println("=== RELATÓRIO COMPARATIVO ===");

        System.out.println("\nTabelaHash1 (função hash polinomial base 31):");
        System.out.println("Colisões: " + tabelaHash1.getColisoes());
        System.out.println("Fator de Carga: " + String.format("%.2f", tabelaHash1.getFatorCarga()));
        System.out.println("Tempo de inserção: " + (tempoFim1 - tempoInicio1) / 1_000_000.0 + " ms");
        System.out.println("Tempo de busca: " + (tempoBuscaFim1 - tempoBuscaInicio1) / 1_000_000.0 + " ms");
        exibirDistribuicao(tabelaHash1);

        System.out.println("\nTabelaHash2 (função hash com mistura de bits):");
        System.out.println("Colisões: " + tabelaHash2.getColisoes());
        System.out.println("Fator de Carga: " + String.format("%.2f", tabelaHash2.getFatorCarga()));
        System.out.println("Tempo de inserção: " + (tempoFim2 - tempoInicio2) / 1_000_000.0 + " ms");
        System.out.println("Tempo de busca: " + (tempoBuscaFim2 - tempoBuscaInicio2) / 1_000_000.0 + " ms");
        exibirDistribuicao(tabelaHash2);

    }

    /**
     * Exibe a distribuição de elementos em cada posição da tabela hash
     * @param tabela tabela hash a ser analisada
     */
    public static void exibirDistribuicao(TabelaHashAbs tabela) {
        int[] distribuicao = tabela.contarElementosPorPosicao();
        System.out.println("Distribuição de chaves por posição:");
        for (int i = 0; i < distribuicao.length; i++) {
            System.out.printf("Posição %2d: %d elementos%n", i, distribuicao[i]);
        }
    }
}
