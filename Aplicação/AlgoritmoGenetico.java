import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlgoritmoGenetico {
    private static final int TAMANHO_POPULACAO = 50;
    private static final double TAXA_MUTACAO   = 0.1;
    private static final int NUMERO_GERACOES   = 2;

    public static Grafo encontrarSubgrafoIsomorfico(Grafo G, Grafo H) {
        List<Grafo> populacao = gerarPopulacaoInicial(G, H);

        for (int geracao = 0; geracao < NUMERO_GERACOES; geracao++)
            populacao = selecionarNovaPopulacao(populacao, G, H);

        return encontrarMelhorIndividuo(populacao, G, H);
    }

    private static List<Grafo> gerarPopulacaoInicial(Grafo G, Grafo H) {
        List<Grafo> populacao = new ArrayList<>();

        for (int i = 0; i < TAMANHO_POPULACAO; i++)
            populacao.add(gerarIndividuoAleatorio(H));

        return populacao;
    }

    private static Grafo gerarIndividuoAleatorio(Grafo H) {
        Grafo individuo = new Grafo(H.getVertices());
        Random random = new Random();

        for (Aresta aresta : H.getArestas()) {
            if (random.nextDouble() < 0.5)
                individuo.adicionarAresta(aresta.getOrigem(), aresta.getDestino());
        }
        return individuo;
    }

    private static List<Grafo> selecionarNovaPopulacao(List<Grafo> populacao, Grafo G, Grafo H) {
        List<Grafo> novaPopulacao = new ArrayList<>();

        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            Grafo pai1  = selecionarPai(populacao, G, H);
            Grafo pai2  = selecionarPai(populacao, G, H);
            Grafo filho = crossover(pai1, pai2);
            mutacao(filho);
            novaPopulacao.add(filho);
        }
        return novaPopulacao;
    }

    private static Grafo selecionarPai(List<Grafo> populacao, Grafo G, Grafo H) {
        Grafo pai = populacao.get(new Random().nextInt(TAMANHO_POPULACAO));
        Grafo melhorIndividuo = encontrarMelhorIndividuo(populacao, G, H);
        if (new Random().nextDouble() < 0.1) pai = melhorIndividuo;
        return pai;
    }

    private static Grafo crossover(Grafo pai1, Grafo pai2) {
        Grafo filho = new Grafo(pai1.getVertices());
        Random random = new Random();

        for (Aresta aresta : pai1.getArestas()) {
            if (random.nextBoolean())
                filho.adicionarAresta(aresta.getOrigem(), aresta.getDestino());
        }

        for (Aresta aresta : pai2.getArestas()) {

            if (!filho.getArestas().contains(aresta) && random.nextBoolean())
                filho.adicionarAresta(aresta.getOrigem(), aresta.getDestino());
        }

        return filho;
    }

    private static void mutacao(Grafo grafo) {
        Random random = new Random();
        for (Aresta aresta : grafo.getArestas()) {
            if (random.nextDouble() < TAXA_MUTACAO)
                grafo.adicionarAresta(aresta.getOrigem(), aresta.getDestino());
        }
    }

    private static Grafo encontrarMelhorIndividuo(List<Grafo> populacao, Grafo G, Grafo H) {
        Grafo melhorIndividuo = populacao.get(0);
        int melhorScore = calcularScore(melhorIndividuo, G, H);

        for (Grafo individuo : populacao) {
            int scoreAtual = calcularScore(individuo, G, H);
            if (scoreAtual > melhorScore) {
                melhorIndividuo = individuo;
                melhorScore = scoreAtual;
            }
        }
        return melhorIndividuo;
    }

    private static int calcularScore(Grafo individuo, Grafo G, Grafo H) {
        int score = 0;
        if (G.isSubgrafo(individuo))
            score += H.getArestas().size();
        return score;
    }
}