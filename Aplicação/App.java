public class App {

    public static void main(String[] args) {

        Grafo G = new Grafo(5);
        Grafo H = new Grafo(3);
        Grafo I = new Grafo(5);
        Grafo J = new Grafo(3);

        // Isomorfo
        G.adicionarAresta(0, 1);
        G.adicionarAresta(1, 2);
        G.adicionarAresta(1, 3);
        G.adicionarAresta(2, 4);
        G.adicionarAresta(3, 2);
        G.adicionarAresta(3, 4);
        System.out.println("Grafo G:");
        G.imprimirGrafo();


        H.adicionarAresta(2, 1);
        H.adicionarAresta(3, 2);
        H.adicionarAresta(1, 3);
        System.out.println("\nGrafo H:");
        H.imprimirGrafo();

        // Não iso
        I.adicionarAresta(0, 1);
        I.adicionarAresta(1, 2);
        I.adicionarAresta(1, 3);
        I.adicionarAresta(2, 4);
        I.adicionarAresta(4, 2);
        I.adicionarAresta(3, 4);
        System.out.println("\nGrafo I:");
        I.imprimirGrafo();


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

    private static boolean verificarIsomorfismo(Grafo H, Grafo subgrafo) {
        return H.isSubgrafo(subgrafo) && subgrafo.isSubgrafo(H);
    }

}
