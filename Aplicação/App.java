/**
 * A classe App é a aplicação principal que demonstra a utilização do algoritmo genético para encontrar
 * um subgrafo isomórfico entre dois grafos: um grafo maior (grafoMaior) e um grafo menor (grafoMenor).
 * A aplicação cria os grafos, imprime suas representações e, em seguida, utiliza o algoritmo genético para
 * encontrar o subgrafo isomórfico. Por fim, verifica se o subgrafo é isomórfico ao grafo menor.
 */
public class App {

    public static void main(String[] args) {
        // Isomorfo
        // Criar grafo maior
        Grafo G = new Grafo(5);
        G.adicionarAresta(0, 1);
        G.adicionarAresta(1, 2);
        G.adicionarAresta(1, 3);
        G.adicionarAresta(2, 4);
        G.adicionarAresta(3, 2);
        G.adicionarAresta(3, 4);
        System.out.println("Grafo G:");
        G.imprimirGrafo();

        // Criar grafo menor
        Grafo H = new Grafo(4);
        H.adicionarAresta(2, 1);
        H.adicionarAresta(3, 2);
        H.adicionarAresta(1, 3);
        System.out.println("\nGrafo H:");
        H.imprimirGrafo();

        // Não iso
        Grafo I = new Grafo(5);
        I.adicionarAresta(0, 1);
        I.adicionarAresta(1, 2);
        I.adicionarAresta(1, 3);
        I.adicionarAresta(2, 4);
        I.adicionarAresta(4, 2);
        I.adicionarAresta(3, 4);
        System.out.println("\nGrafo I:");
        I.imprimirGrafo();

        Grafo J = new Grafo(3);
        J.adicionarAresta(2, 1);
        J.adicionarAresta(0, 2);
        J.adicionarAresta(1, 0);
        System.out.println("\nGrafo J:");
        J.imprimirGrafo();

        Grafo subgrafoIsomorfico1 = AlgoritmoGenetico.encontrarSubgrafoIsomorfico(G, H);
        Grafo subgrafoIsomorfico2 = AlgoritmoGenetico.encontrarSubgrafoIsomorfico(I, J);

        if (verificarIsomorfismo(H, subgrafoIsomorfico1))
            System.out.println("\nO subgrafo de G é isomórfico ao grafo H.");
        else
            System.out.println("\nO subgrafo de G NÃO é isomórfico ao grafo H.");

        if (verificarIsomorfismo(J, subgrafoIsomorfico2))
            System.out.println("\nO subgrafo de I é isomórfico ao grafo J.");
        else
            System.out.println("\nO subgrafo de I NÃO é isomórfico ao grafo J.");

    }

    /**
     * Verifica se o subgrafo é isomorfo ao grafo menor.
     *
     * @param A Grafo menor que representa o subgrafo a ser encontrado.
     * @param subgrafo Grafo a ser verificado quanto ao isomorfismo com o grafo menor.
     * @return true se o subgrafo é isomórfico ao grafo menor, false caso contrário.
     */
    private static boolean verificarIsomorfismo(Grafo A, Grafo subgrafo) {
        return A.isSubgrafo(subgrafo) && subgrafo.isSubgrafo(A);
    }

}
