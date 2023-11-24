import java.util.ArrayList;
import java.util.List;

public class Grafo {

    private final int vertices;
    private final List<List<Integer>> adjacencias;

    public Grafo(int vertices) {
        this.vertices    = vertices;
        this.adjacencias = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) adjacencias.add(new ArrayList<>());
    }

    public void adicionarAresta(int origem, int destino) {
        adjacencias.get(origem).add(destino);
        adjacencias.get(destino).add(origem);
    }

    public int getVertices() {
        return vertices;
    }

    public List<Aresta> getArestas() {
        List<Aresta> arestas = new ArrayList<>();
        for (int i = 0; i < this.vertices; i++)
            for (int vizinho : this.getVizinhos(i))
                arestas.add(new Aresta(i, vizinho));
        return arestas;
    }

    public List<Integer> getVizinhos(int vertice) {
        return adjacencias.get(vertice);
    }

    public boolean isSubgrafo(Grafo H) {
        if (H.getVertices() > this.vertices) return false;
        return verificarArestas(H);
    }

    private boolean verificarArestas(Grafo H) {
        for (Aresta arestaMenor : H.getArestas()) {
            int mapeamentoOrigem  = arestaMenor.getOrigem();
            int mapeamentoDestino = arestaMenor.getDestino();

            if (!this.adjacencias.get(mapeamentoOrigem).contains(mapeamentoDestino) ||
                !this.adjacencias.get(mapeamentoDestino).contains(mapeamentoOrigem))
                return false;
        }
        return true;
    }

    public void imprimirGrafo() {
        System.out.println("Vértices do Grafo:");
        for (int i = 0; i < vertices; i++) {
            System.out.print(i + ": ");
            for (int vizinho : adjacencias.get(i)) System.out.print(vizinho + " ");
            System.out.println();
        }
        System.out.println("Arestas do Grafo:");
        for (Aresta aresta : getArestas())
            System.out.println("(" + aresta.getOrigem() + ", " + aresta.getDestino() + ")");
    }
    
}
