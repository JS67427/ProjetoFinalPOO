/**
 * A classe Controlos é responsável por processar os comandos de entrada do usuário para controlar a cobra em um jogo.
 * Esta classe interpreta comandos específicos e realiza ações na instância da cobra, como mover ou crescer,
 * de acordo com as teclas pressionadas pelo usuário.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.1 - 12/05/2024
 */

public class Controlos {
    /**
     * Processa o comando de entrada dado para controlar a movimentação e ações da cobra no jogo.
     * Os comandos suportados incluem movimento em quatro direções ('W', 'A', 'S', 'D') e ações como crescer ('G')
     * e sair do jogo ('Q').
     *
     * @param c     A instância da cobra que será controlada (tipo Cobra).
     * @param input A string de entrada representando o comando do usuário (tipo String).
     */
    public void ProcessarControlos(Cobra c, String input) {
        switch (input) {
            case "W":
                if (c.setDir(90))
                    c.move();
                break;
            case "A":
                if (c.setDir(180))
                    c.move();
                break;
            case "S":
                if (c.setDir(270))
                    c.move();
                break;
            case "D":
                if (c.setDir(0))
                    c.move();
                break;
            case "G":
                c.grow();
                break;
            case "Q":
                System.out.println("Game Over!");
                System.exit(0);
        }
    }
}
