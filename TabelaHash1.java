/**
 * Implementação da tabela hash usando função polinomial base 31
 */
public class TabelaHash1 extends TabelaHashAbs {
    /**
     * Construtor - Inicializa a tabela hash com o tamanho especificado
     * @param tamanho número de posições na tabela
     */
    public TabelaHash1(int tamanho) {
        super(tamanho);
    }

    @Override
    protected int funcaoHash(String chave) {
        int codigoHash = 0;
        for (int i = 0; i < chave.length(); i++) {
            codigoHash = (31 * codigoHash + chave.charAt(i)) % tamanho;
        }
        return Math.abs(codigoHash);
    }
}
