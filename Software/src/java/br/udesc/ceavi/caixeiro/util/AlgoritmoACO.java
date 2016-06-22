package br.udesc.ceavi.caixeiro.util;

import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.EntregaIteracao;
import br.udesc.ceavi.caixeiro.model.RelacionamentoEndereco;
import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import java.util.ArrayList;
import java.util.Random;

public class AlgoritmoACO {

    private Entrega entrega;

    private final EntregaIteracao iteracao = new EntregaIteracao();

    private ArrayList<Endereco> enderecos = new ArrayList();

    /**
     * Probabilidade de usar caminhos com feromônios
     */
    private double probFeromonio = 1;

    /**
     * Probabilidade de usar caminhos próximos
     */
    private double probCaminho = 5;

    /**
     * Taxa de dissipação de feromônio
     */
    private double taxaDissipacao = 0.5;

    /**
     *  Cociente de deposeito de feromônio
     */
    private double coeficienteDeposito = 500;

    /**
     * Percentual de formigas
     */
    private double numAntFactor = 0.8;

    /**
     * Quantidade de iterações
     */
    private int limiteIteracoes = 2000;

    public int numeroCidades = 0;
    public int numeroFormigas = 0;
    private double matriz[][] = null;
    private double trilhaFeromonio[][] = null;
    private Formiga formigas[] = null;
    private Random aleatorio = new Random();
    private double probabilidade[] = null;

    private int indice = 0;

    public int[] melhorTrajeto;
    public double distanciaMelhorTrajeto;

    private void gravaIteracao(double media) {
        iteracao.setDistancia(distanciaMelhorTrajeto - (numeroCidades + 1));
        iteracao.setMedia(media);
        iteracao.setTrajeto(escreveArray(melhorTrajeto));

        // entrega, distanciaMelhorTrajeto, media, melhorTrajeto
        JDBCFactory.getDaoEntregaIteracao().insert(iteracao);
    }

    /**
     * Carrega as cidades do banco de dados
     */
    public void buscaDados() {
        numeroCidades  = JDBCFactory.getDaoRelacionamentoEndereco().getQuantidadeEntrega(entrega);
        numeroFormigas = (int) (numeroCidades * numAntFactor);

        // criando vetores
        matriz   = new double[numeroCidades][numeroCidades];
        formigas = new Formiga[numeroFormigas];

        probabilidade   = new double[numeroCidades];
        trilhaFeromonio = new double[numeroCidades][numeroCidades];

        // prepara uma formiga com os atributos padrão
        Formiga prototipo = new Formiga();
        prototipo.cidades = new int[numeroCidades + 1];
        prototipo.trajeto = new boolean[numeroCidades];
        for (int j = 0; j < numeroFormigas; j++) {
            formigas[j] = prototipo.clone();
        }

        // limpa as trilhas
        for (int i = 0; i < numeroCidades; i++)
            for (int j = 0; j < numeroCidades; j++)
                trilhaFeromonio[i][j] = 1.0; // valor inicial


        for (RelacionamentoEndereco r : JDBCFactory.getDaoRelacionamentoEndereco().getEnderecosEntrega(entrega)) {
            if (!enderecos.contains(r.getEnderecoSaida())){
                enderecos.add(r.getEnderecoSaida());
            }
            if (!enderecos.contains(r.getEnderecoChegada())){
                enderecos.add(r.getEnderecoChegada());
            }

            int idSaida = enderecos.indexOf(r.getEnderecoSaida()),
                idChegada = enderecos.indexOf(r.getEnderecoChegada());
            matriz[idSaida][idChegada] = r.getDistancia() + 1;
        }
    }

    // exponencial aproximado, menor precisão, porém mais rápido que o nativo
    // See:
    // http://martin.ankerl.com/2007/10/04/optimized-exponencial-approximation-for-java-and-c-c/
    public static double exponencial(final double a, final double b) {
        final long tmp = (long) (9076650 * (a - 1) / (a + 1 + 4 * (Math.sqrt(a))) * b + 1072632447);
        return Double.longBitsToDouble(tmp << 32);
    }

    /**
     * Grava a chance de utilizar cada cidade
     * +feromõnio +curto?
     * @param formiga
     */
    private void probabilidadeViagem(Formiga formiga) {
        int i = formiga.cidades[indice];

        double denominador = 0.0;
        for (int l = 0; l < numeroCidades; l++)
            if (!formiga.getCidade(l))
                denominador += exponencial(trilhaFeromonio[i][l], probFeromonio) * exponencial(1.0 / matriz[i][l], probCaminho);


        for (int j = 0; j < numeroCidades; j++) {
            if (formiga.getCidade(j)) {
                probabilidade[j] = 0.0;
            } else {
                double numerador = exponencial(trilhaFeromonio[i][j], probFeromonio) * exponencial(1.0 / matriz[i][j], probCaminho);
                probabilidade[j] = numerador / denominador;
            }
        }

    }

    /**
     * Determina qual cidade será visitada
     * @param formiga
     * @return
     */
    private int buscaProximaCidade(Formiga formiga) {
        // calcula a probabilidade de cada cidade ser visitada
        probabilidadeViagem(formiga);
        // busca número aleatório
        double r = aleatorio.nextDouble();
        double tot = 0;
        for (int i = 0; i < numeroCidades; i++) {
            tot += probabilidade[i];
            if (tot >= r)
                return i;
        }

        return  numeroCidades -1;
    }

    /**
     * Atualiza feromônios
     */
    private void atualizaTrilhas() {
        /**
         * Evapora feromônios
         */
        for (int i = 0; i < numeroCidades; i++)
            for (int j = 0; j < numeroCidades; j++)
                trilhaFeromonio[i][j] *= taxaDissipacao;

        /**
         * Deposita novos feromônios
         */
        for (Formiga formiga : formigas) {
            double contribuicao = coeficienteDeposito / formiga.distancia();
            for (int i = 0; i < numeroCidades - 1; i++) {
                trilhaFeromonio[formiga.cidades[i]][formiga.cidades[i + 1]] += contribuicao;
            }
            trilhaFeromonio[formiga.cidades[numeroCidades - 1]][formiga.cidades[0]] += contribuicao;
        }
    }

    /**
     * Move as formigas para cada cidade disponível
     */
    private void moveFormigas() {
        while (indice < numeroCidades - 1) {
            for (Formiga formiga : formigas)
                formiga.visitaCidade(buscaProximaCidade(formiga));
            indice++;
        }
    }

    /**
     * Coloca as formigas em uma cidade aleatória para iniciar
     */
    private void preparaFormigas() {
        indice = -1;
        for (int i = 0; i < numeroFormigas; i++) {
            formigas[i].limpa();
            formigas[i].visitaCidade(0);
        }
        indice++;

    }

    /**
     * verifica e atualiza o trajeto mais curto (com menor tempo)
     */
    private void atualizaMelhorTrajeto() {
        if (melhorTrajeto == null) {
            melhorTrajeto = formigas[0].cidades;
            distanciaMelhorTrajeto = formigas[0].distancia();
        }

        double totalFormigas = 0;

        for (Formiga formiga : formigas) {
            // e voltamos para o início
            formiga.visitaCidade(0);
            double distancia = formiga.distancia();
            totalFormigas += distancia;

            if (distancia < distanciaMelhorTrajeto || distanciaMelhorTrajeto == 0) {
                distanciaMelhorTrajeto = distancia;
                melhorTrajeto = formiga.cidades.clone();
            }
        }

        gravaIteracao(totalFormigas / formigas.length);
    }

    private String escreveArray(int trajeto[]) {
        String retorno = new String();
        for (int cidade : trajeto) {
            String id = String.valueOf(enderecos.get(cidade).getId());
            retorno = retorno + " " + id;
        }
        return retorno;
    }

    public int[] calcula(Entrega entrega) {
        this.entrega = entrega;
        this.iteracao.setEntrega(entrega);

        buscaDados();

        int i = 0;
        // executa as iterações
        while (i < limiteIteracoes) {
            preparaFormigas();
            moveFormigas();
            atualizaTrilhas();
            atualizaMelhorTrajeto();
            i++;
        }

        // Escreve o resultado
        System.out.println("Menor distância: " + (distanciaMelhorTrajeto - (numeroCidades + 1)));
        System.out.println("Caminho:" + escreveArray(melhorTrajeto));
        return melhorTrajeto.clone();
    }

    /**
     * Especificação da formiga
     */
    private class Formiga {
        public int cidades[];

        /**
         * Lista de cidades visitadas
         */
        public boolean trajeto[];

        public void visitaCidade(int cidade) {
            cidades[indice + 1] = cidade;
            trajeto[cidade] = true;
        }

        public boolean getCidade(int i) {
            return trajeto[i];
        }

        public double distancia() {
            double length = matriz[cidades[numeroCidades - 1]][cidades[0]];
            for (int i = 0; i < numeroCidades - 1; i++) {
                length += matriz[cidades[i]][cidades[i + 1]];
            }
            return length;
        }

        public void limpa() {
            for (int i = 0; i < numeroCidades; i++)
                trajeto[i] = false;
        }

        protected Formiga clone() {
            Formiga f = new Formiga();
            f.cidades = cidades.clone();
            f.trajeto = trajeto.clone();
            return f;
        }
    }
}