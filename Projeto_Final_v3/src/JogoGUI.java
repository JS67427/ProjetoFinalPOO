/**
 * A classe JogoGUI gere a interface gráfica do jogo de cobra.
 * Esta inicializa e desenha a interface, bem como controla o loop do jogo,
 * entrada do utilizador e interações com o jogador.
 *
 * @author Jorge Silva, Paulo Martins, Vasile Karpa
 * @version 1.0 - 22/05/2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.util.List;

public class JogoGUI extends JFrame {
    private Cobra snake;
    private Arena arena;
    private Comida food;
    private List<Obstaculo> allObstacles;
    private Obstaculo collisionObstacle = null;
    private boolean complete;
    private boolean wantsObstacles;
    private int currentScore;
    private Timer gameTimer;
    private boolean isPaused = false;
    private boolean isAI = false;

    private JPanel gamePanel;
    private JPanel menuPanel;
    private JPanel optionsPanel;
    private JPanel infoPanel;

    private JLabel arenaSizeLabel;
    private JLabel snakeSizeLabel;
    private JLabel currentScoreLabel;
    private JLabel snakeDirectionLabel;
    private JSlider speedSlider;

    /**
     * Construtor da classe JogoGUI. Configura a interface inicial do jogo e adiciona os componentes principais.
     */
    public JogoGUI() {
        setTitle("OOP SNAKE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setupMenuPanel();
        setupGamePanel();
        setupOptionsPanel(this.isAI);
        setupInfoPanel();

        setContentPane(menuPanel);
        setVisible(true);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setMinimumSize(new Dimension(1280, 720));

        // Adicione o KeyListener uma vez
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });

        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * Configura o painel do menu inicial do jogo.
     */
    private void setupMenuPanel() {
        infoPanel = new JPanel();
        menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("OOP SNAKE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        menuPanel.add(titleLabel, gbc);

        JButton playButton = new JButton("Jogar");
        playButton.addActionListener(e -> showOptions(false));
        gbc.gridy = 1;
        menuPanel.add(playButton, gbc);

        JButton playWithAIButton = new JButton("Jogar com IA");
        playWithAIButton.addActionListener(e -> showOptions(true));
        gbc.gridy = 2;
        menuPanel.add(playWithAIButton, gbc);

        JButton scoresButton = new JButton("Ver todas as pontuações");
        scoresButton.addActionListener(e -> showScores());
        gbc.gridy = 3;
        menuPanel.add(scoresButton, gbc);

        JButton exitButton = new JButton("Sair");
        exitButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 4;
        menuPanel.add(exitButton, gbc);
    }

    /**
     * Configura o painel de jogo onde a arena, a cobra e outros elementos do jogo são desenhados.
     */
    private void setupGamePanel() {
        gamePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGame(g);
            }
        };
        gamePanel.setBackground(Color.BLACK);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(infoPanel);

        add(gamePanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    /**
     * Configura o painel de informações que exibe dados como tamanho da arena, tamanho da cobra,
     * direção atual da cobra e pontuação atual.
     */
    private void setupInfoPanel() {
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        arenaSizeLabel = new JLabel();
        snakeSizeLabel = new JLabel();
        currentScoreLabel = new JLabel();
        snakeDirectionLabel = new JLabel();

        JLabel controlsLabel = new JLabel("<html>&nbsp;&nbsp;Controlos<br>" +
                "&nbsp;&nbsp;&nbsp;WASD para mover<br>&nbsp;&nbsp;&nbsp;P para pausa/continuar" +
                "<br>&nbsp;&nbsp;&nbsp;Q para voltar ao menu<br>&nbsp;&nbsp;&nbsp;ESC para sair<html>");
        infoPanel.add(controlsLabel);

        infoPanel.add(new JLabel("<html><br><br>&nbsp;&nbsp;Informações do Jogo<html>"));
        infoPanel.add(arenaSizeLabel);
        infoPanel.add(snakeSizeLabel);
        infoPanel.add(snakeDirectionLabel);
        infoPanel.add(currentScoreLabel);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(infoPanel, BorderLayout.EAST);
    }

    /**
     * Configura o painel de opções onde o usuário pode definir as configurações do jogo antes de iniciar.
     *
     * @param isAI Indica se o jogo será jogado com a ajuda da IA.
     */
    private void setupOptionsPanel(boolean isAI) {
        optionsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label1 = new JLabel("Modo gráfico: ");
        JComboBox<String> rasterizationComboBox = new JComboBox<>(new String[]{"Contorno", "Completo"});
        gbc.gridx = 0;
        gbc.gridy = 0;
        optionsPanel.add(label1, gbc);
        gbc.gridx = 1;
        optionsPanel.add(rasterizationComboBox, gbc);

        JLabel label2 = new JLabel("Gerar obstáculos aleatórios? ");
        JComboBox<String> obstaclesComboBox = new JComboBox<>(new String[]{"Não", "Sim"});
        obstaclesComboBox.setEnabled(!isAI); // Disable if playing with AI
        gbc.gridx = 0;
        gbc.gridy = 1;
        optionsPanel.add(label2, gbc);
        gbc.gridx = 1;
        optionsPanel.add(obstaclesComboBox, gbc);

        JLabel label3 = new JLabel("Comprimento da arena: ");
        JTextField widthField = new JTextField("750");
        gbc.gridx = 0;
        gbc.gridy = 2;
        optionsPanel.add(label3, gbc);
        gbc.gridx = 1;
        optionsPanel.add(widthField, gbc);

        JLabel label4 = new JLabel("Altura da arena: ");
        JTextField heightField = new JTextField("750");
        gbc.gridx = 0;
        gbc.gridy = 3;
        optionsPanel.add(label4, gbc);
        gbc.gridx = 1;
        optionsPanel.add(heightField, gbc);

        JLabel label5 = new JLabel("Comprimento da aresta da cabeça da cobra: ");
        JTextField headField = new JTextField("50");
        gbc.gridx = 0;
        gbc.gridy = 4;
        optionsPanel.add(label5, gbc);
        gbc.gridx = 1;
        optionsPanel.add(headField, gbc);

        JLabel speedLabel = new JLabel("Velocidade do jogo: ");
        speedSlider = new JSlider(50, 200, 100);
        speedSlider.setMajorTickSpacing(50);
        speedSlider.setMinorTickSpacing(50);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setLabelTable(speedSlider.createStandardLabels(50));
        speedSlider.setSnapToTicks(true);
        Hashtable<Integer, JLabel> speedLabels = new Hashtable<>();
        speedLabels.put(50, new JLabel("Rápido"));
        speedLabels.put(100, new JLabel("Normal"));
        speedLabels.put(150, new JLabel("Lento"));
        speedLabels.put(200, new JLabel("Super Lento"));
        speedSlider.setLabelTable(speedLabels);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        optionsPanel.add(speedLabel, gbc);
        gbc.gridy = 6;
        optionsPanel.add(speedSlider, gbc);
        gbc.gridwidth = 1;

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> {
            setContentPane(menuPanel);
            revalidate();
            repaint();
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        optionsPanel.add(cancelButton, gbc);

        JButton saveButton = new JButton("Jogar");
        saveButton.addActionListener(e -> {
            complete = rasterizationComboBox.getSelectedIndex() == 1;
            wantsObstacles = !isAI && obstaclesComboBox.getSelectedIndex() == 1;
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int head = Integer.parseInt(headField.getText());
            int delay = speedSlider.getValue();

            initializeGame(width, height, head, delay);
            setContentPane(gamePanel);
            revalidate();
            repaint();
        });
        gbc.gridx = 1;
        optionsPanel.add(saveButton, gbc);
    }

    /**
     * Atualiza os componentes do painel de informações.
     */
    private void updateInfoPanel() {
        if (arena != null)
            arenaSizeLabel.setText("   Tamanho da Arena: " + arena.getWidth() + " x " + arena.getHeight() + "   ");
        if (snake != null) {
            snakeSizeLabel.setText("   Size H: " + snake.getEdge() + "   ");
            snakeDirectionLabel.setText("   Dir H: " + snake.getDir() + "   ");
        }
        currentScoreLabel.setText("   Pontos Atuais: " + currentScore + "   ");
    }

    /**
     * Exibe o painel de opções onde o usuário pode configurar o jogo.
     *
     * @param isAI Indica se o jogo será jogado com a ajuda da IA.
     */
    private void showOptions(boolean isAI) {
        this.isAI = isAI;
        setupOptionsPanel(isAI);
        setContentPane(optionsPanel);
        revalidate();
        repaint();
    }

    /**
     * Exibe as pontuações registradas em um diálogo de mensagem.
     */
    private void showScores() {
        Pontuacao pontuacao = new Pontuacao();
        List<String> pontuacoes = pontuacao.getPontuacoes();

        JTextArea textArea = new JTextArea();
        for (String p : pontuacoes)
            textArea.append(p + "\n");
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Pontuações", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Inicializa os componentes do jogo, como a cobra, a arena, a comida e os obstáculos.
     *
     * @param width A largura da arena (tipo int).
     * @param height A altura da arena (tipo int).
     * @param head O tamanho da cabeça da cobra (tipo int).
     * @param delay O atraso entre os ciclos do jogo em milissegundos (tipo int).
     */
    private void initializeGame(int width, int height, int head, int delay) {
        snake = new Cobra(height, width, head);
        arena = new Arena(Jogo.OptimalSizeArena(head, height), Jogo.OptimalSizeArena(head, width));

        currentScore = 0;
        allObstacles = new ArrayList<>();
        if (wantsObstacles)
            for (int i = 0;
                 i < Math.min(width, height) / (Math.pow(10, String.valueOf(Math.min(width, height)).length() - 1));
                 i++)
                allObstacles.add(new Obstaculo(arena.getWidth(), arena.getHeight(), snake));

        food = new Comida(arena.getHeight(), arena.getWidth(), allObstacles, snake);

        gameTimer = new Timer(delay, e -> gameLoop());
        gameTimer.start();

        updateInfoPanel();
    }

    /**
     * Lógica de movimentação da cobra controlada pela IA para se aproximar da comida.
     */
    private void aiMove() {
        if (food != null && snake != null) {
            Ponto foodPosition = new Ponto(0, 0);
            if (food.getFood() instanceof Quadrado quadrado)
                foodPosition = quadrado.getTopLeft();
            else if (food.getFood() instanceof Circunferencia circunferencia)
                foodPosition = new Ponto(circunferencia.getCenter().getX() - circunferencia.getRadius(),
                        circunferencia.getCenter().getY() - circunferencia.getRadius());

            Ponto snakeHead = snake.getSnake().get(0).getTopLeft();

            //0: esq-dir; 90: baixo-cima; 180: dir-esq; 270: cima-baixo
            // Lógica básica de IA para mover a cobra em direção à comida
            if (snakeHead.getY() > foodPosition.getY())
                snake.setSafeDir(90, arena.getWidth(), arena.getHeight());
            else if (snakeHead.getY() < foodPosition.getY())
                snake.setSafeDir(270, arena.getWidth(), arena.getHeight());
            else if (snakeHead.getX() > foodPosition.getX())
                snake.setSafeDir(180, arena.getWidth(), arena.getHeight());
            else if (snakeHead.getX() < foodPosition.getX())
                snake.setSafeDir(0, arena.getWidth(), arena.getHeight());
        }
    }

    /**
     * Lógica principal do jogo, responsável por atualizar a posição da cobra, verificar colisões,
     * desenhar a arena e atualizar a pontuação.
     */
    private void gameLoop() {
        if (!isPaused) {
            try {
                if (snake.getDir() != -1) {
                    if (isAI)
                        aiMove();
                    snake.move();
                }
            } catch (IllegalArgumentException e) {
                gameOver();
            }

            if (!arena.drawSnake(snake, complete))
                gameOver();
            else {
                if (wantsObstacles) {
                    for (Obstaculo o : allObstacles) {
                        if (o.isRotating()) {
                            arena.deleteObstacle(o, complete);
                            o.rotate();
                            arena.drawObstacle(o, complete);
                        } else
                            arena.drawObstacle(o, complete);
                    }

                    if (!snake.getIntersectedObstacles(allObstacles).isEmpty()) {
                        collisionObstacle = snake.getIntersectedObstacles(allObstacles).get(0);
                        if (collisionObstacle != null)
                            gameOver();
                    }
                }

                arena.drawFood(food, complete);
                if (snake.containsFood(food)) {
                    arena.drawEatenFood(food, complete);
                    snake.grow();
                    currentScore += food.getAllIntPoints().length;
                    food = new Comida(arena.getHeight(), arena.getWidth(), allObstacles, snake);
                    if (!food.wasGenerated()) {
                        currentScore = Integer.MAX_VALUE;
                        gameOver();
                    }
                }

                gamePanel.repaint();
                updateScore();
                updateInfoPanel();
            }
        }
    }

    /**
     * Trata os eventos de teclado para controlar a cobra e outras ações do jogo.
     *
     * @param keyCode O código da tecla pressionada (tipo int).
     */
    private void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE)
            System.exit(0);
        if (keyCode == KeyEvent.VK_P)
            togglePause();
        if (gameTimer != null && gameTimer.isRunning()) {
            switch (keyCode) {
                case KeyEvent.VK_W -> snake.setDir(90);
                case KeyEvent.VK_A -> snake.setDir(180);
                case KeyEvent.VK_S -> snake.setDir(270);
                case KeyEvent.VK_D -> snake.setDir(0);
                case KeyEvent.VK_Q -> {
                    gameTimer.stop();
                    setContentPane(menuPanel);
                    revalidate();
                    repaint();
                }
            }
        }
    }

    /**
     * Alterna o estado de pausa do jogo.
     */
    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused)
            gameTimer.stop();
        else
            gameTimer.start();
    }

    /**
     * Atualiza a pontuação atual exibida na interface do usuário.
     */
    private void updateScore() {
        currentScoreLabel.setText("Pontos: " + currentScore);
    }

    /**
     * Trata o final do jogo, exibindo a pontuação final e permitindo que o jogador insira seu nome para salvar a pontuação.
     */
    private void gameOver() {
        gameTimer.stop();
        gamePanel.repaint();

        SwingUtilities.invokeLater(() -> {
            if (currentScore == 0)
                JOptionPane.showMessageDialog(this, "Pontuação igual a 0, não foi adicionada " +
                        "aos recordes.", "Game Over!", JOptionPane.INFORMATION_MESSAGE);
            else {
                String nomeJogador = JOptionPane.showInputDialog(this, "Game Over! Pontuação: "
                        + currentScore + "\nDigite seu nome:", "Game Over!", JOptionPane.ERROR_MESSAGE);
                if (nomeJogador != null && !nomeJogador.trim().isEmpty()) {
                    Pontuacao pontuacao = new Pontuacao();
                    pontuacao.adicionarPontuacao(currentScore, nomeJogador.trim());
                }
            }

            showScores();

            setContentPane(menuPanel);
            revalidate();
            repaint();
        });
    }

    /**
     * Desenha o estado atual do jogo na interface gráfica.
     *
     * @param g O objeto Graphics usado para desenhar os componentes.
     */
    private void drawGame(Graphics g) {
        if (snake == null || arena == null)
            return;

        g.setColor(Color.WHITE);
        g.drawRect(0, 0, arena.getWidth(), arena.getHeight());
        if (complete) {
            g.setColor(Color.GREEN);
            for (Quadrado q : snake.getSnake())
                g.fillRect((int) q.getTopLeft().getX(), (int) q.getTopLeft().getY(),
                        snake.getEdge(), snake.getEdge());

            g.setColor(Color.PINK);
            if (food.getFood() instanceof Quadrado quadrado)
                g.fillRect((int) quadrado.getTopLeft().getX(), (int) quadrado.getTopLeft().getY(),
                        food.getFoodSize(), food.getFoodSize());
            else if (food.getFood() instanceof Circunferencia circunferencia)
                g.fillOval((int) (circunferencia.getCenter().getX() - circunferencia.getRadius()),
                        (int) (circunferencia.getCenter().getY() - circunferencia.getRadius()),
                        food.getFoodSize(), food.getFoodSize());

            if (wantsObstacles) {
                for (Obstaculo obstaculo : allObstacles) {
                    if (obstaculo == collisionObstacle)
                        g.setColor(Color.YELLOW);
                    else
                        g.setColor(Color.RED);

                    if (obstaculo.getObstaculo() instanceof Quadrado quadrado)
                        g.fillRect((int) quadrado.getTopLeft().getX(), (int) quadrado.getTopLeft().getY(),
                                obstaculo.getObsSize(), obstaculo.getObsSize());
                    else if (obstaculo.getObstaculo() instanceof Retangulo retangulo)
                        g.fillRect((int) retangulo.getTopLeft().getX(), (int) retangulo.getTopLeft().getY(),
                                obstaculo.getRectBigSide(), obstaculo.getObsSize());
                    else if (obstaculo.getObstaculo() instanceof Triangulo triangulo) {
                        Ponto[] vertices = triangulo.getVertices();
                        int[] xPoints = {(int) vertices[0].getX(), (int) vertices[1].getX(), (int) vertices[2].getX()};
                        int[] yPoints = {(int) vertices[0].getY(), (int) vertices[1].getY(), (int) vertices[2].getY()};
                        g.fillPolygon(xPoints, yPoints, 3);
                    }
                }
            }
            System.out.println();
        } else {
            g.setColor(Color.GREEN);
            for (Quadrado quadrado : snake.getSnake())
                g.drawRect((int) quadrado.getTopLeft().getX(), (int) quadrado.getTopLeft().getY(), snake.getEdge(), snake.getEdge());

            g.setColor(Color.PINK);
            if (food.getFood() instanceof Quadrado quadrado)
                g.drawRect((int) quadrado.getTopLeft().getX(), (int) quadrado.getTopLeft().getY(),
                        food.getFoodSize(), food.getFoodSize());
            else if (food.getFood() instanceof Circunferencia circunferencia)
                g.drawOval((int) (circunferencia.getCenter().getX() - circunferencia.getRadius()),
                        (int) (circunferencia.getCenter().getY() - circunferencia.getRadius()),
                        food.getFoodSize(), food.getFoodSize());

            if (wantsObstacles) {
                for (Obstaculo obstaculo : allObstacles) {
                    if (obstaculo == collisionObstacle)
                        g.setColor(Color.YELLOW);
                    else
                        g.setColor(Color.RED);

                    if (obstaculo.getObstaculo() instanceof Quadrado quadrado)
                        g.drawRect((int) quadrado.getTopLeft().getX(), (int) quadrado.getTopLeft().getY(),
                                obstaculo.getObsSize(), obstaculo.getObsSize());
                    else if (obstaculo.getObstaculo() instanceof Retangulo retangulo)
                        g.drawRect((int) retangulo.getTopLeft().getX(), (int) retangulo.getTopLeft().getY(),
                                obstaculo.getRectBigSide(), obstaculo.getObsSize());
                    else if (obstaculo.getObstaculo() instanceof Triangulo triangulo) {
                        Ponto[] vertices = triangulo.getVertices();
                        int[] xPoints = {(int) vertices[0].getX(), (int) vertices[1].getX(), (int) vertices[2].getX()};
                        int[] yPoints = {(int) vertices[0].getY(), (int) vertices[1].getY(), (int) vertices[2].getY()};
                        g.drawPolygon(xPoints, yPoints, 3);
                    }
                }
            }
        }
    }
}
