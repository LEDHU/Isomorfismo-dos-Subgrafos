import java.util.ArrayList;
import java.util.List;

/**
 * A classe Grafo representa um grafo não direcionado por meio de sua estrutura de
 * vértices e arestas, possibilitando operações como adição de arestas, obtenção de
 * vértices, arestas e vizinhos, verificação de subgrafos e impressão do grafo.
 */
public class Grafo {

    // Atributos para armazenar o número de vértices e as adjacências entre eles
    private int vertices;
    private List<List<Integer>> adjacencias;

    /**
     * Construtor da classe Grafo, que inicializa um grafo com o número especificado
     * de vértices, criando a estrutura de adjacências correspondente.
     *
     * @param vertices Número de vértices no grafo.
     */
    public Grafo(int vertices) {
        // Atribui o número de vértices ao membro da classe
        this.vertices    = vertices;
        // Inicializa a lista de adjacências com base no número de vértices
        this.adjacencias = new ArrayList<>(vertices);
        // Para cada vértice, adiciona uma lista vazia de vizinhos à lista de adjacências
        for (int i = 0; i < vertices; i++) adjacencias.add(new ArrayList<>());
    }

    /**
     * Método para adicionar uma aresta entre os vértices de origem e destino no grafo.
     *
     * @param origem  Vértice de origem da aresta.
     * @param destino Vértice de destino da aresta.
     */
    public void adicionarAresta(int origem, int destino) {
        adjacencias.get(origem).add(destino);
        adjacencias.get(destino).add(origem);
    }

    /**
     * Método para obter o número de vértices no grafo.
     *
     * @return O número de vértices no grafo.
     */
    public int getVertices() {
        return vertices;
    }

    /**
     * Método para obter uma lista de todas as arestas no grafo.
     *
     * @return Lista de arestas no grafo.
     */
    public List<Aresta> getArestas() {
        // Inicializa uma lista para armazenar as arestas
        List<Aresta> arestas = new ArrayList<>();
        // Itera sobre os vértices do grafo
        for (int i = 0; i < this.vertices; i++)
            // Itera sobre os vizinhos de cada vértice
            for (int vizinho : this.getVizinhos(i))
                // Adiciona uma aresta representada por um par de vértices à lista
                arestas.add(new Aresta(i, vizinho));
        // Retorna a lista de arestas do grafo
        return arestas;
    }

    /**
     * Método para obter a lista de vizinhos de um vértice específico no grafo.
     *
     * @param vertice Vértice para o qual obter os vizinhos.
     * @return Lista de vizinhos do vértice especificado.
     */
    public List<Integer> getVizinhos(int vertice) {
        // Retorna a lista de vizinhos do vértice fornecido
        return adjacencias.get(vertice);
    }

    /**
     * Método para verificar se um grafo é subgrafo do grafo atual.
     *
     * @param H Grafo a ser verificado como subgrafo.
     * @return true se for subgrafo, false caso contrário.
     */
    public boolean isSubgrafo(Grafo H) {
        // O grafo menor não pode ser um subgrafo se tiver mais vértices.
        if (H.getVertices() > this.vertices) return false;
        // Chama a função verificarArestas para verificar a correspondência das arestas.
        return verificarArestas(H);
    }

    /**
     * Verifica se as arestas do grafo menor têm correspondência no grafo maior.
     *
     * @param H Grafo menor que está sendo verificado.
     * @return true se as arestas do grafo menor têm correspondência no grafo maior, false caso contrário.
     */
    private boolean verificarArestas(Grafo H) {
        // Itera sobre as arestas do grafo menor.
        for (Aresta arestaMenor : H.getArestas()) {
            // Obtém os vértices de origem e destino da aresta do grafo menor.
            int mapeamentoOrigem  = arestaMenor.getOrigem();
            int mapeamentoDestino = arestaMenor.getDestino();
            // Verifica se as arestas correspondentes existem no grafo maior.
            if (!this.adjacencias.get(mapeamentoOrigem).contains(mapeamentoDestino) ||
                !this.adjacencias.get(mapeamentoDestino).contains(mapeamentoOrigem))
                return false;
        }
        // Se todas as arestas do grafo menor têm correspondência, retorna true.
        return true;
    }


    /**
     * Método para imprimir os vértices e arestas do grafo.
     */
    public void imprimirGrafo() {
        // Imprime os vértices do grafo
        System.out.println("Vértices do Grafo:");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + ": ");
            // Imprime os vizinhos de cada vértice
            for (int vizinho : adjacencias.get(i)) System.out.print(vizinho + " ");
            System.out.println();
        }
        // Imprime as arestas do grafo.
        System.out.println("Arestas do Grafo:");
        // Itera sobre as arestas obtidas através do método getArestas().
        for (Aresta aresta : getArestas())
            System.out.println("(" + aresta.getOrigem() + ", " + aresta.getDestino() + ")");
    }
    
}
