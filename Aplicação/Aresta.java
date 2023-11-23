/**
 * A classe Aresta representa uma aresta em um grafo direcionado,
 * com atributos para armazenar a origem e o destino da aresta.
 */
public class Aresta {
    
    // Atributos para armazenar a origem e o destino da aresta
    private int origem;
    private int destino;

    /**
     * Construtor da classe Aresta, que inicializa uma aresta com a origem
     * e o destino especificados.
     *
     * @param origem  Valor inteiro representando o vértice de origem da aresta.
     * @param destino Valor inteiro representando o vértice de destino da aresta.
     */
    public Aresta(int origem, int destino) {
        this.origem  = origem;
        this.destino = destino;
    }

    /**
     * Método para obter o valor do atributo origem.
     *
     * @return O valor inteiro representando o vértice de origem da aresta.
     */
    public int getOrigem() {
        return origem;
    }

    /**
     * Método para obter o valor do atributo destino.
     *
     * @return O valor inteiro representando o vértice de destino da aresta.
     */
    public int getDestino() {
        return destino;
    }

}
