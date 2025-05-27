public class LinkedList {
    // Referência para o primeiro nó da lista encadeada
    private Node primeiro;

    /**
     * Construtor - Cria uma lista encadeada vazia
     */
    public LinkedList() {
        primeiro = null;
    }

    /**
     * Adiciona um novo valor no início da lista
     * @param valor valor a ser adicionado
     */
    public void adicionar(String valor) {
        Node novoNo = new Node(valor);
        novoNo.proximo = primeiro;
        primeiro = novoNo;
    }

    /**
     * Verifica se um valor existe na lista
     * @param valor valor a ser procurado
     * @return verdadeiro se o valor for encontrado, falso caso contrário
     */
    public boolean contem(String valor) {
        Node noAtual = primeiro;
        while (noAtual != null) {
            if (noAtual.valor.equals(valor)) return true;
            noAtual = noAtual.proximo;
        }
        return false;
    }

    /**
     * Calcula o tamanho da lista
     * @return número de elementos na lista
     */
    public int tamanho() {
        int contador = 0;
        Node noAtual = primeiro;
        while (noAtual != null) {
            contador++;
            noAtual = noAtual.proximo;
        }
        return contador;
    }

    /**
     * Verifica se a lista está vazia
     * @return verdadeiro se a lista estiver vazia, falso caso contrário
     */
    public boolean estaVazia() {
        return primeiro == null;
    }

    /**
     * Remove um valor da lista
     * @param valor valor a ser removido
     * @return verdadeiro se o valor foi encontrado e removido, falso caso contrário
     */
    public boolean remover(String valor) {
        if (primeiro == null) return false;

        // Caso especial: remover o primeiro elemento
        if (primeiro.valor.equals(valor)) {
            primeiro = primeiro.proximo;
            return true;
        }

        // Procura o elemento na lista
        Node anterior = primeiro;
        Node atual = primeiro.proximo;
        while (atual != null) {
            if (atual.valor.equals(valor)) {
                anterior.proximo = atual.proximo;
                return true;
            }
            anterior = atual;
            atual = atual.proximo;
        }
        return false;
    }

    /**
     * Limpa a lista, removendo todos os elementos
     */
    public void limpar() {
        primeiro = null;
    }

    /**
     * Retorna o primeiro nó da lista (método público para acesso controlado)
     * @return primeiro nó da lista
     */
    public Node getPrimeiroNo() {
        return primeiro;
    }
}
