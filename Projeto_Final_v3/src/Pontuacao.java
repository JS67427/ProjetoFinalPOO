/**
 * A classe Pontuacao gere as pontuações de um jogo, permitindo adicionar, organizar e recuperar
 * as melhores pontuações armazenadas em um arquivo. As pontuações são mantidas em um arquivo de texto
 * chamado "resultados.txt".
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.2 - 22/05/2024
 */

import java.io.*;
import java.util.*;

public class Pontuacao {
    private static final String FILE_NAME = "resultados.txt";

    /**
     * Adiciona uma pontuação ao arquivo de resultados. Se a pontuação for zero, ela não é adicionada.
     * Após adicionar uma nova pontuação, as pontuações são reorganizadas.
     *
     * @param score A pontuação a ser adicionada (tipo int).
     * @param nome  O nome do jogador (tipo String).
     */
    public void adicionarPontuacao(int score, String nome) {
        if (score == 0)
            System.out.println("Pontuação igual a 0, não foi adicionada aos recordes.");
        else {
            try (FileWriter fw = new FileWriter(FILE_NAME, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(score + " " + nome);
            } catch (IOException e) {
                System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        }
        organizarPontuacoes();
    }

    /**
     * Organiza o arquivo de pontuações por pontuação máxima. As pontuações são lidas, ordenadas e reescritas
     * de volta ao arquivo.
     */
    private void organizarPontuacoes() {
        List<String> pontuacoes = lerPontuacoes();
        List<PontuacaoJogo> listaPontuacoes = new ArrayList<>();
        for (String pontuacao : pontuacoes) {
            String[] parts = pontuacao.split(" ");
            int score = Integer.parseInt(parts[0]);
            String nome = parts[1];
            listaPontuacoes.add(new PontuacaoJogo(score, nome));
        }

        listaPontuacoes.sort(Collections.reverseOrder());

        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (PontuacaoJogo pj : listaPontuacoes) {
                out.println(pj.score + " " + pj.nome);
            }
        } catch (IOException e) {
            System.err.println("Erro ao reescrever o arquivo: " + e.getMessage());
        }
    }

    /**
     * Lê as pontuações do arquivo e retorna uma lista de strings, cada uma contendo uma pontuação e o nome
     * do jogador associado.
     *
     * @return Uma lista de pontuações (tipo List<String>).
     */
    private List<String> lerPontuacoes() {
        List<String> pontuacoes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pontuacoes.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return pontuacoes;
    }

    /**
     * Retorna as pontuações lidas do arquivo.
     *
     * @return Lista de pontuações (List<String>).
     */
    public List<String> getPontuacoes() {
        return lerPontuacoes();
    }

    /**
     * Imprime as melhores n pontuações registradas. Se o número solicitado for maior que as pontuações
     * disponíveis, serão impressas todas as pontuações existentes.
     *
     * @param n O número de top pontuações a imprimir (tipo int).
     */
    public void imprimirTopNPontuacoes(int n) {
        List<String> pontuacoes = lerPontuacoes();
        System.out.println("Top " + n + " Pontuações:");
        for (int i = 0; i < Math.min(n, pontuacoes.size()); i++)
            System.out.println(pontuacoes.get(i));
    }

    /**
     * Classe interna PontuacaoJogo define a estrutura de uma pontuação no jogo, incluindo o score e o nome
     * do jogador. Esta implementa Comparable para permitir a ordenação das pontuações.
     */
    private static class PontuacaoJogo implements Comparable<PontuacaoJogo> {
        int score;
        String nome;

        PontuacaoJogo(int score, String nome) {
            this.score = score;
            this.nome = nome;
        }

        @Override
        public int compareTo(PontuacaoJogo o) {
            return Integer.compare(this.score, o.score);
        }
    }
}
