public class Node {
    // Armazena o valor (chave) do nó
    String valor;
    // Referência para o próximo nó na lista encadeada
    Node proximo;

    /**
     * Construtor do nó
     * @param chave valor a ser armazenado no nó
     */
    public Node(String chave) {
        this.valor = chave;
        this.proximo = null;
    }
}
