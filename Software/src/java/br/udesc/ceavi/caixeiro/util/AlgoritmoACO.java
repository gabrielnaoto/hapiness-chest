package br.udesc.ceavi.caixeiro.util;

import br.udesc.ceavi.core.model.dao.JDBC.JDBCFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgoritmoACO {
    /**
     * Valor depositado
     */
    private double taxaFeromonio = 1.0;

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

    /**
     * Especificação da formiga
     */
    private class Formiga {
        public int cidades[] = new int[matriz.length];

        /**
         * Lista de cidades visitadas
         */
        public boolean trajeto[] = new boolean[matriz.length];

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
    }

    /**
     * Carrega as cidades a partir de arquivo
     * @param path
     * @throws IOException
     */
    public void readGraph(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader buf = new BufferedReader(fr);
        String line;
        int i = 0;

        while ((line = buf.readLine()) != null) {
            String splitA[] = line.split(" ");
            LinkedList<String> split = new LinkedList<String>();
            for (String s : splitA)
                if (!s.isEmpty())
                    split.add(s);

            if (matriz == null)
                matriz = new double[split.size()][split.size()];
            int j = 0;

            for (String s : split)
                if (!s.isEmpty())
                    matriz[i][j++] = Double.parseDouble(s) + 1; // +1 evita caminho zerado

            i++;
        }

        numeroCidades = matriz.length;
        numeroFormigas = (int) (numeroCidades * numAntFactor);

        // criando vetores
        trilhaFeromonio = new double[numeroCidades][numeroCidades];
        probabilidade = new double[numeroCidades];
        formigas = new Formiga[numeroFormigas];
        for (int j = 0; j < numeroFormigas; j++)
            formigas[j] = new Formiga();
    }

    /**
     * Carrega as cidades do banco de dados
     */
    public void buscaDados() {
        try {
            ResultSet result = JDBCFactory.getDaoRelacionamentoEndereco().getAllEnderecoEntrega();

            while (result.next()) {
//                String splitA[] = line.split(" ");
//                LinkedList<String> split = new LinkedList<String>();
//                for (String s : splitA)
//                    if (!s.isEmpty())
//                        split.add(s);
//
//                if (matriz == null)
//                    matriz = new double[split.size()][split.size()];
//                int j = 0;
//
//                for (String s : split)
//                    if (!s.isEmpty())
//                        matriz[i][j++] = Double.parseDouble(s) + 1; // +1 evita caminho zerado
//
//                i++;
            }

            numeroCidades = matriz.length;
            numeroFormigas = (int) (numeroCidades * numAntFactor);

            // criando vetores
            trilhaFeromonio = new double[numeroCidades][numeroCidades];
            probabilidade = new double[numeroCidades];
            formigas = new Formiga[numeroFormigas];
            for (int j = 0; j < numeroFormigas; j++)
                formigas[j] = new Formiga();
        } catch (SQLException ex) {
            Logger.getLogger(AlgoritmoACO.class.getName()).log(Level.SEVERE, null, ex);
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
            formigas[i].visitaCidade(aleatorio.nextInt(numeroCidades));
        }
        indice++;

    }

    private void atualizaMelhorTrajeto() {
        if (melhorTrajeto == null) {
            melhorTrajeto = formigas[0].cidades;
            distanciaMelhorTrajeto = formigas[0].distancia();
        }
        for (Formiga formiga : formigas) {
            if (formiga.distancia() < distanciaMelhorTrajeto) {
                distanciaMelhorTrajeto = formiga.distancia();
                melhorTrajeto = formiga.cidades.clone();
            }
        }
    }

    public static String escreveArray(int trajeto[]) {
        String retorno = new String();
        for (int cidade : trajeto)
            retorno = retorno + " " + cidade;
        return retorno;
    }

    public int[] resolver() {
        // llimpaas trilhas
        for (int i = 0; i < numeroCidades; i++)
            for (int j = 0; j < numeroCidades; j++)
                trilhaFeromonio[i][j] = taxaFeromonio;

        int iteracao = 0;
        // executa as iterações
        while (iteracao < limiteIteracoes) {
            preparaFormigas();
            moveFormigas();
            atualizaTrilhas();
            atualizaMelhorTrajeto();
            iteracao++;
        }
        // Escreve o resultado
        System.out.println("Menor distância: " + (distanciaMelhorTrajeto - numeroCidades));
        System.out.println("Caminho:" + escreveArray(melhorTrajeto));
        return melhorTrajeto.clone();
    }
}