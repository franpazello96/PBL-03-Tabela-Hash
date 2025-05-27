/**
 * Implementação da tabela hash usando função de mistura de bits
 */
public class TabelaHash2 extends TabelaHashAbs {
    /**
     * Construtor - Inicializa a tabela hash com o tamanho especificado
     * @param tamanho número de posições na tabela
     */
    public TabelaHash2(int tamanho) {
        super(tamanho);
    }

    /**
     * Implementa a função de hash usando método de mistura de bits
     * Esta função usa operação XOR e deslocamento de bits para gerar o código hash
     * @param chave String a ser transformada em índice
     * @return índice calculado pela função hash
     */
    @Override
    protected int funcaoHash(String chave) {
        int codigoHash = 0;
        for (int i = 0; i < chave.length(); i++) {
            codigoHash ^= (chave.charAt(i) << (i % 5));
        }
        return Math.abs(codigoHash) % tamanho;
    }
}
