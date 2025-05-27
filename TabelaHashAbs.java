/**
 * Classe abstrata que implementa o funcionamento básico de uma tabela hash.
 * Esta classe fornece a estrutura base para diferentes implementações de funções hash.
 * Utiliza o método de tratamento de colisões por encadeamento separado (separate chaining).
 */
public abstract class TabelaHashAbs {
    // Define o número total de posições disponíveis na tabela hash
    // Este valor é usado como divisor na operação de módulo das funções hash
    protected int tamanho;

    // Array de listas encadeadas onde cada posição pode armazenar múltiplos elementos
    // Quando ocorre uma colisão, o novo elemento é adicionado à lista da posição correspondente
    // Isso implementa o tratamento de colisão por encadeamento separado (separate chaining)
    protected LinkedList[] tabela;

    // Mantém o registro do número total de colisões que ocorreram
    // Uma colisão acontece quando tentamos inserir um elemento em uma posição que já contém algum valor
    protected int colisoes;

    /**
     * Construtor - Inicializa a tabela hash com o tamanho especificado.
     * Cria um array de listas encadeadas vazio e inicializa o contador de colisões.
     * 
     * @param tamanho número de posições na tabela (deve ser maior que 0)
     */
    public TabelaHashAbs(int tamanho) {
        // Guarda o tamanho da tabela para uso nos cálculos de índice
        this.tamanho = tamanho;
        
        // Cria o array de listas encadeadas com o tamanho especificado
        tabela = new LinkedList[tamanho];
        
        // Inicializa cada posição com uma lista encadeada vazia
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList();
        }
        
        // Inicializa o contador de colisões
        colisoes = 0;
    }

    /**
     * Método abstrato que define a função de hash a ser implementada nas subclasses
     * @param chave valor a ser transformado em índice da tabela
     * @return índice calculado pela função hash (deve estar entre 0 e tamanho-1)
     */
    /**
     * Método abstrato que define a função de hash a ser implementada nas subclasses.
     * Cada subclasse deve implementar sua própria estratégia de transformação da chave em índice.
     * 
     * @param chave String a ser transformada em índice da tabela
     * @return índice calculado pela função hash (deve estar entre 0 e tamanho-1)
     */
    protected abstract int funcaoHash(String chave);

    /**
     * Insere uma chave na tabela hash.
     * O processo de inserção segue os seguintes passos:
     * 1. Verifica se é necessário redimensionar a tabela
     * 2. Calcula o índice usando a função hash
     * 3. Verifica se há colisão
     * 4. Insere a chave na lista encadeada correspondente
     * 
     * @param chave valor a ser inserido na tabela
     */
    public void inserir(String chave) {
        // Verifica se é necessário aumentar o tamanho da tabela
        // O fator 0.75 é um valor comum para manter um bom equilíbrio entre espaço e tempo
        if (getFatorCarga() > 0.75) {
            redimensionar();
        }

        // Calcula o índice onde a chave será inserida
        int indice = funcaoHash(chave);

        // Se já existe algum elemento nesta posição, incrementa o contador de colisões
        if (!tabela[indice].estaVazia()) colisoes++;

        // Adiciona a chave na lista encadeada da posição calculada
        tabela[indice].adicionar(chave);
    }

    /**
     * Verifica se uma chave existe na tabela hash
     * @param chave valor a ser procurado
     * @return verdadeiro se a chave for encontrada, falso caso contrário
     */
    /**
     * Verifica se uma chave existe na tabela hash.
     * O processo de busca é eficiente pois:
     * 1. Calcula o índice usando a mesma função hash
     * 2. Procura apenas na lista encadeada daquele índice
     * 
     * @param chave valor a ser procurado na tabela
     * @return verdadeiro se a chave for encontrada, falso caso contrário
     */
    public boolean contem(String chave) {
        // Calcula o índice onde a chave deveria estar
        int indice = funcaoHash(chave);
        // Verifica se a chave existe na lista encadeada daquela posição
        return tabela[indice].contem(chave);
    }

    /**
     * Conta quantos elementos existem em cada posição da tabela.
     * Este método é útil para análise da distribuição dos elementos
     * e avaliação da qualidade da função hash.
     * 
     * @return array onde cada posição contém o número de elementos naquela posição da tabela
     */
    public int[] contarElementosPorPosicao() {
        // Cria um array para armazenar a contagem de elementos
        int[] contagem = new int[tamanho];
        
        // Para cada posição da tabela, conta quantos elementos existem na lista
        for (int i = 0; i < tamanho; i++) {
            contagem[i] = tabela[i].tamanho();
        }
        
        return contagem;
    }

    /**
     * Retorna o número total de colisões ocorridas na tabela hash
     * @return total de colisões
     */
    /**
     * Retorna o número total de colisões ocorridas na tabela hash.
     * Uma colisão é registrada sempre que tentamos inserir um elemento
     * em uma posição que já contém outro elemento.
     * 
     * @return número total de colisões desde a criação da tabela
     */
    public int getColisoes() {
        return colisoes;
    }

    /**
     * Calcula o fator de carga atual da tabela hash.
     * O fator de carga é a proporção entre o número total de elementos
     * e o tamanho da tabela. Este valor é importante para decidir
     * quando a tabela precisa ser redimensionada.
     * 
     * Por exemplo:
     * - Se temos 15 elementos em uma tabela de tamanho 20
     * - Fator de carga = 15/20 = 0.75
     * 
     * @return fator de carga (número entre 0 e maior que 1)
     */
    public double getFatorCarga() {
        // Conta o número total de elementos em todas as listas
        int totalElementos = 0;
        for (LinkedList lista : tabela) {
            totalElementos += lista.tamanho();
        }
        
        // Calcula e retorna a proporção entre elementos e tamanho
        return (double) totalElementos / tamanho;
    }

    /**
     * Redimensiona a tabela hash quando o fator de carga ultrapassa 0.75.
     * O tamanho da tabela é aumentado em 25% (novo tamanho = tamanho + tamanho/4).
     * Depois, todos os elementos são remapeados para a nova tabela (rehashing).
     */
    /**
     * Redimensiona a tabela hash quando o fator de carga ultrapassa 0.75.
     * O processo de redimensionamento segue os seguintes passos:
     * 1. Cria uma nova tabela 25% maior que a atual
     * 2. Recalcula os índices de todos os elementos (rehashing)
     * 3. Insere todos os elementos na nova tabela
     * 
     * Este redimensionamento é importante para manter a eficiência da tabela
     * conforme ela cresce, evitando muitas colisões.
     */
    private void redimensionar() {
        // Calcula o novo tamanho (aumenta em 25%)
        int novoTamanho = tamanho + tamanho / 4;
        
        // Guarda a tabela atual para posterior transferência dos elementos
        LinkedList[] antigaTabela = tabela;

        // Cria a nova tabela com o novo tamanho
        tabela = new LinkedList[novoTamanho];
        // Inicializa cada posição com uma lista vazia
        for (int i = 0; i < novoTamanho; i++) {
            tabela[i] = new LinkedList();
        }

        // Atualiza o tamanho e reinicia a contagem de colisões
        tamanho = novoTamanho;
        colisoes = 0;

        // Transfere todos os elementos da tabela antiga para a nova
        for (int i = 0; i < antigaTabela.length; i++) {
            Node atual = antigaTabela[i].getPrimeiroNo();
            while (atual != null) {
                // Recalcula o índice de cada elemento para o novo tamanho
                int novoIndice = funcaoHash(atual.valor);
                
                // Verifica se há colisão na nova posição
                if (!tabela[novoIndice].estaVazia()) colisoes++;
                
                // Insere o elemento na nova tabela
                tabela[novoIndice].adicionar(atual.valor);
                
                // Avança para o próximo elemento da lista
                atual = atual.proximo;
            }
        }
    }

    /**
     * Exclui uma chave da tabela hash
     * @param chave valor a ser excluído
     * @return verdadeiro se a chave foi encontrada e excluída, falso caso contrário
     */
    /**
     * Remove uma chave específica da tabela hash.
     * O processo de exclusão é eficiente pois:
     * 1. Usa a função hash para encontrar a posição exata
     * 2. Remove apenas da lista encadeada correta
     * 3. Não precisa reorganizar a tabela
     * 
     * @param chave o valor a ser excluído da tabela
     * @return true se a chave foi encontrada e removida, false caso contrário
     */
    public boolean excluir(String chave) {
        // Calcula o índice onde a chave deveria estar
        int indice = funcaoHash(chave);
        // Remove a chave da lista encadeada naquela posição
        return tabela[indice].remover(chave);
    }

    /**
     * Limpa completamente a tabela hash, removendo todos os elementos.
     * Este método:
     * 1. Remove todos os elementos de todas as listas
     * 2. Reinicia o contador de colisões
     * 3. Mantém o mesmo tamanho da tabela
     * 
     * Útil quando queremos reutilizar a mesma tabela hash
     */
    public void limpar() {
        // Limpa cada lista encadeada da tabela
        for (int i = 0; i < tamanho; i++) {
            tabela[i].limpar();
        }
        // Reinicia o contador de colisões
        colisoes = 0;
    }
}
