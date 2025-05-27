/**
 * Implementa uma função de hash simples baseada no tamanho da string e primeira letra.
 * Esta implementação é ideal para fins didáticos por sua simplicidade.
 */
public class TabelaHash3 extends TabelaHashAbs {  // Herda funcionalidades básicas da classe abstrata
    
    /**
     * Construtor da tabela hash.
     * @param tamanho Define quantas posições a tabela terá
     */
    public TabelaHash3(int tamanho) {
        super(tamanho);  // Chama o construtor da classe pai para inicializar a tabela
    }

    /**
     * Implementa a função de hash que transforma uma string em um índice da tabela.
     * A função usa uma abordagem simples combinando dois fatores:
     * 1. O tamanho da string
     * 2. A posição da primeira letra no alfabeto (A=1, B=2, etc)
     * 
     * Por exemplo, para a string "CASA":
     * - Primeira letra 'C' = posição 3 no alfabeto
     * - Comprimento = 4 caracteres
     * - Soma = 7
     * - Índice final = 7 % tamanho da tabela
     * 
     * @param chave A string que será transformada em um índice
     * @return Um número entre 0 e (tamanho-1)
     */
    @Override
    protected int funcaoHash(String chave) {
        // Converte a primeira letra para maiúscula e obtém sua posição no alfabeto (A=1, B=2, etc)
        int posicaoLetra = chave.toUpperCase().charAt(0) - 'A' + 1;
        
        // Combina o tamanho da string com a posição da letra e aplica o módulo
        // para garantir que o resultado fique dentro do tamanho da tabela
        return (chave.length() + posicaoLetra) % tamanho;
    }
}
