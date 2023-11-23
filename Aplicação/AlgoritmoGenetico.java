import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A classe AlgoritmoGenetico implementa um algoritmo genético para encontrar um subgrafo isomórfico
 * de tamanho máximo entre dois grafos: um grafo maior (G) e um grafo menor (H).
 * A busca é realizada através da evolução de uma população de grafos, utilizando operadores como
 * crossover e mutação.
 */
public class AlgoritmoGenetico {

    // Parâmetros do algoritmo genético
    private static final int TAMANHO_POPULACAO = 50;
    private static final double TAXA_MUTACAO   = 0.1;
    private static final int NUMERO_GERACOES   = 2;

    /**
     * Método principal que inicia o algoritmo genético para encontrar um subgrafo isomórfico.
     *
     * @param G Grafo maior no qual buscar o subgrafo isomórfico.
     * @param H Grafo menor que representa o subgrafo a ser encontrado.
     * @return O subgrafo isomórfico de tamanho máximo encontrado.
     */
    public static Grafo encontrarSubgrafoIsomorfico(Grafo G, Grafo H) {
        // Gera uma população inicial de grafos aleatórios
        List<Grafo> populacao = gerarPopulacaoInicial(G, H);
        // Realiza a evolução da população por um número definido de gerações
        for (int geracao = 0; geracao < NUMERO_GERACOES; geracao++)
            populacao = selecionarNovaPopulacao(populacao, G, H);
        // Retorna o melhor indivíduo (grafo) encontrado na última geração
        return encontrarMelhorIndividuo(populacao, G, H);
    }

    /**
     * Gera a população inicial de grafos aleatórios para o algoritmo genético.
     *
     * @param G Grafo maior no qual buscar o subgrafo isomórfico.
     * @param H Grafo menor que representa o subgrafo a ser encontrado.
     * @return Lista de grafos que compõem a população inicial.
     */
    private static List<Grafo> gerarPopulacaoInicial(Grafo G, Grafo H) {
        // Inicializa uma nova lista para armazenar a população
        List<Grafo> populacao = new ArrayList<>();
        // Itera sobre o tamanho da população desejada
        for (int i = 0; i < TAMANHO_POPULACAO; i++)
            // Gera um indivíduo aleatório e o adiciona à população
            populacao.add(gerarIndividuoAleatorio(H));
        // Retorna a população inicial gerada
        return populacao;
    }

    /**
     * Gera um indivíduo aleatório (grafo) para a população inicial.
     *
     * @param H Grafo menor que representa o subgrafo a ser encontrado.
     * @return Grafo aleatório gerado.
     */
    private static Grafo gerarIndividuoAleatorio(Grafo H) {
        // Inicializa um novo grafo (indivíduo) com base no número de vértices do grafo menor
        Grafo individuo = new Grafo(H.getVertices());
        // Cria uma instância da classe Random para geração de números aleatórios
        Random random = new Random();
        // Itera sobre as arestas do grafo menor
        for (Aresta aresta : H.getArestas()) {
            // Verifica se a aresta do grafo menor deve ser incluída no indivíduo com base em um valor aleatório
            if (random.nextDouble() < 0.5)
                // Adiciona a aresta ao indivíduo
                individuo.adicionarAresta(aresta.getOrigem(), aresta.getDestino());
        }
        // Retorna o indivíduo gerado aleatoriamente
        return individuo;
    }

    /**
     * Seleciona uma nova população com base na população atual e operadores genéticos.
     *
     * @param populacao Atual população de grafos.
     * @param G Grafo maior no qual buscar o subgrafo isomórfico.
     * @param H Grafo menor que representa o subgrafo a ser encontrado.
     * @return Nova população selecionada após crossover e mutação.
     */
    private static List<Grafo> selecionarNovaPopulacao(List<Grafo> populacao, Grafo G, Grafo H) {
        // Inicializa uma nova lista para armazenar a nova população
        List<Grafo> novaPopulacao = new ArrayList<>();
        // Itera sobre o tamanho da população desejada
        for (int i = 0; i < TAMANHO_POPULACAO; i++) {
            // Seleciona dois pais da população
            Grafo pai1  = selecionarPai(populacao, G, H);
            Grafo pai2  = selecionarPai(populacao, G, H);
            // Realiza o crossover entre os dois pais para gerar um filho
            Grafo filho = crossover(pai1, pai2); // Aplica mutação no filho
            mutacao(filho);
            novaPopulacao.add(filho); // Adiciona o filho à nova população
        }
        // Retorna a nova população gerada
        return novaPopulacao;
    }

    /**
     * Seleciona um pai para o crossover, com chances de escolher o melhor indivíduo.
     *
     * @param populacao Atual população de grafos.
     * @param G Grafo maior no qual buscar o subgrafo isomórfico.
     * @param H Grafo menor que representa o subgrafo a ser encontrado.
     * @return Grafo pai selecionado para o crossover.
     */
    private static Grafo selecionarPai(List<Grafo> populacao, Grafo G, Grafo H) {
        // Seleciona aleatoriamente um grafo da população como pai
        Grafo pai = populacao.get(new Random().nextInt(TAMANHO_POPULACAO));
        // Encontra o melhor indivíduo na população
        Grafo melhorIndividuo = encontrarMelhorIndividuo(populacao, G, H);
        // Garante que o pai seja o melhor indivíduo pelo menos uma vez, com base em um valor aleatório
        // O valor 0.7 refere-se a uma probabilidade, determinando a chance de selecionar
        // o melhor indivíduo (melhor grafo) da população como pai em vez de escolher aleatoriamente um grafo da população.
        // Nesse caso, 0.7, significa que há 70% de probabilidade de escolher o melhor indivíduo.
        if (new Random().nextDouble() < 0.7) pai = melhorIndividuo;
        return pai; // Retorna o grafo selecionado como pai
    }

    /**
     * Realiza o crossover entre dois pais para gerar um filho.
     *
     * @param pai1 Primeiro pai para o crossover.
     * @param pai2 Segundo pai para o crossover.
     * @return Grafo filho gerado após o crossover.
     */
    private static Grafo crossover(Grafo pai1, Grafo pai2) {
        // Cria um novo grafo (filho) com base no número de vértices do pai1
        Grafo filho = new Grafo(pai1.getVertices());
        // Cria uma instância da classe Random para geração de números aleatórios
        Random random = new Random();
        // Itera sobre as arestas do pai1
        for (Aresta aresta : pai1.getArestas()) {
            // Verifica se a aresta do pai1 deve ser incluída no filho com base em um valor aleatório
            if (random.nextBoolean())
                filho.adicionarAresta(aresta.getOrigem(), aresta.getDestino()); // Adiciona a aresta ao filho
        }
        // Itera sobre as arestas do pai2
        for (Aresta aresta : pai2.getArestas()) {
            // Verifica se a aresta do pai2 não está no filho e deve ser incluída com base em um valor aleatório
            if (!filho.getArestas().contains(aresta) && random.nextBoolean())
                filho.adicionarAresta(aresta.getOrigem(), aresta.getDestino()); // Adiciona a aresta ao filho
        }
        // Retorna o filho gerado pelo crossover
        return filho;
    }

    /**
     * Realiza a mutação em um grafo, adicionando ou removendo arestas aleatoriamente.
     *
     * @param grafo Grafo a ser mutado.
     */
    private static void mutacao(Grafo grafo) {
        // Cria uma instância da classe Random para geração de números aleatórios
        Random random = new Random();
        // Itera sobre as arestas do grafo
        for (Aresta aresta : grafo.getArestas()) {
            // Verifica se uma mutação deve ser aplicada com base na taxa de mutação
            if (random.nextDouble() < TAXA_MUTACAO)
                // Adiciona ou remove a aresta, dependendo do valor aleatório gerado
                grafo.adicionarAresta(aresta.getOrigem(), aresta.getDestino());
        }
    }

    /**
     * Encontra o melhor indivíduo (grafo) em uma população com base no score.
     *
     * @param populacao Atual população de grafos.
     * @param G Grafo maior no qual buscar o subgrafo isomórfico.
     * @param H Grafo menor que representa o subgrafo a ser encontrado.
     * @return Melhor grafo encontrado na população.
     */
    private static Grafo encontrarMelhorIndividuo(List<Grafo> populacao, Grafo G, Grafo H) {
        // Inicializa o melhor indivíduo com o primeiro da população
        Grafo melhorIndividuo = populacao.get(0);
        // Calcula o score do melhor indivíduo
        int melhorScore = calcularScore(melhorIndividuo, G, H);
        // Itera sobre os indivíduos da população
        for (Grafo individuo : populacao) {
            // Calcula o score do indivíduo atual
            int scoreAtual = calcularScore(individuo, G, H);
            // Se o score do indivíduo atual é maior que o melhor score até agora
            if (scoreAtual > melhorScore) {
                // Atualiza o melhor indivíduo e o melhor score
                melhorIndividuo = individuo;
                melhorScore = scoreAtual;
            }
        }
        // Retorna o melhor indivíduo encontrado na população
        return melhorIndividuo;
    }

    /**
     * Calcula o score de um grafo com base na quantidade de arestas que formam um subgrafo isomórfico.
     *
     * @param individuo Grafo para o qual calcular o score.
     * @param G Grafo maior no qual buscar o subgrafo isomórfico.
     * @param H Grafo menor que representa o subgrafo a ser encontrado.
     * @return Score do grafo com base na quantidade de arestas formando um subgrafo isomórfico.
     */
    private static int calcularScore(Grafo individuo, Grafo G, Grafo H) {
        // Inicializa o score como zero
        int score = 0;
        // Se o indivíduo é um subgrafo do grafo maior
        if (G.isSubgrafo(individuo))
            // Incrementa o score pela quantidade de arestas do grafo menor
            score += H.getArestas().size();
        // Retorna o score calculado
        return score;
    }
    
}
