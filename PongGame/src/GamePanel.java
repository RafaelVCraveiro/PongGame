import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JInternalFrame implements Runnable {
    /*A classe GamePanel representa o painel do jogo, que estende a classe 
    JInternalFrame e implementa a interface Runnable para permitir a execução 
    do jogo em uma thread separada.*/
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel() {
        //O trecho de código apresentado é o construtor da classe GamePanel.
        super("Game Panel");
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();	
        
    }

    public void newBall() {
        /*Esse método é responsável por criar uma nova instância do objeto
        Ball com base nas dimensões do jogo e em valores aleatórios para a posição inicial da bola.*/
        random = new Random();
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2), random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        /*Esse método é responsável por criar duas novas instâncias 
        do objeto Paddle e atribuí-las às variáveis paddle1 e paddle2.*/
        paddle1 = new Paddle(0, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT / 2) - (PADDLE_HEIGHT / 2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        /*Esse método é um método sobrescrito da classe JInternalFrame 
        que é responsável por desenhar os componentes gráficos do painel do jogo na tela.*/
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        /*Esse método é responsável por desenhar os elementos do jogo,
        como os paddles, a bola e a pontuação, em um componente gráfico.*/
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();
    }

    public void move() {
        /*Esse método é responsável por atualizar a posição dos elementos do jogo,
        como os paddles e a bola, com base em suas velocidades.*/
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        //Esse método é responsável por verificar as colisões e tomar as ações correspondentes no jogo.
        
        //Verifica se ocorreu colisão da bola na parte inferior e superior da tela
        if (ball.y <= 0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        //Verifica se ocorreu colisão entre a bola e o paddle1.
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++;
            if (ball.yVelocity > 0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //Verifica se ocorreu colisão entre a bola e o paddle2.
        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; 
            if (ball.yVelocity > 0)
                ball.yVelocity++; 
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //Verifica se os paddles atingem as bordas superior e inferior da janela.
        if (paddle1.y <= 0)
            paddle1.y = 0;
        if (paddle1.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle1.y = GAME_HEIGHT - PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y = 0;
        if (paddle2.y >= (GAME_HEIGHT - PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT - PADDLE_HEIGHT;
       
        
            
            
       
       //Verificar se a bola atinge a borda esquerda da janela do jogo.
        if (ball.x <= 0) {
            if(score.player2 == 4){
                dispose();
                score.player2 = 0;
                score.player1 = 0;
                salvarPontuacaoPLayer2(score.player2);
                salvarPontuacaoPLayer1(score.player1);
                JOptionPane.showMessageDialog(null, "Player 2 Ganhou!!!");
                System.exit(0);
                
            }else{
            score.player2++;
            newPaddles();
            newBall();
            salvarPontuacaoPLayer2(score.player2);
            salvarPontuacaoPLayer1(score.player1);
        }
        }
        //Verificar se a bola atinge a borda direita da janela do jogo.
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            if(score.player1 == 4){
                dispose();
                score.player1 = 0;
                score.player2 = 0;
                salvarPontuacaoPLayer1(score.player1);
                salvarPontuacaoPLayer2(score.player2);
                JOptionPane.showMessageDialog(null, "Player 1 Ganhou!!!");
                System.exit(0);
                
            }else{
            score.player1++;
            newPaddles();
            newBall();
            salvarPontuacaoPLayer1(score.player1);
            salvarPontuacaoPLayer2(score.player2);
        }
    }
    }
    

    public void run() {
        //Esse método implementa o loop principal do jogo.
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            
            }
        }
    }

    public class AL extends KeyAdapter {
        /*Essa classe é uma classe interna que estende KeyAdapter 
        e é usada para lidar com eventos de teclado no jogo.*/
        
        public void keyPressed(KeyEvent e) {
            //Esse método é responsável por atualizar as posições dos paddles de acordo com a tecla pressionada pelo usuário.
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            /*Esse método é responsável por atualizar o estado dos paddles 
            quando uma tecla é solta ou seja, quando o usuário deixa de pressionar uma tecla.*/
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
   public void salvarPontuacaoPLayer1(int player1) {
       /*Esse método é responsável por salvar a pontuação do "Player 1"
       em um arquivo de texto chamado "PontucaoPlayer1.txt".*/
        try  {
            FileWriter myWrite = new FileWriter("PontucaoPlayer1.txt");
            myWrite.write(Integer.toString(score.player1));
            myWrite.close();
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao salvar a pontuação: " + e.getMessage());
        }
}
   public void salvarPontuacaoPLayer2(int player2) {
       /*Esse método é responsável por salvar a pontuação do "Player 2"
       em um arquivo de texto chamado "PontucaoPlayer2.txt".*/
        try  {
            FileWriter myWrite = new FileWriter("PontucaoPlayer2.txt");
            myWrite.write(Integer.toString(score.player2));
            myWrite.close();
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao salvar a pontuação: " + e.getMessage());
        }
}
   public int carregarPontuacaoPlayer1(int placarPlayer1 ){
       /*Esse método é responsável por carregar a pontuação do "Player 1" 
       a partir de um arquivo de texto chamado "PontucaoPlayer1.txt".*/
       try{
                    File myObj = new File("PontucaoPlayer1.txt");
                    Scanner myReader = new Scanner(myObj);
                    placarPlayer1 = myReader.nextInt();                                      
                    return placarPlayer1;
                    
                }catch(Exception e){System.out.println("erro");}
                
        return placarPlayer1;        
   }
   
    public void lerPlacarPlayer1(){
        /*Esse método tem como objetivo ler a pontuação do "Player 1"
        armazenada no arquivo e atualizar o valor da pontuação na variável score.player1.*/
        int placarPlayer1 = 0;
        int resultado = carregarPontuacaoPlayer1(placarPlayer1);
        score.player1 = resultado;   
   }
   public int carregarPontuacaoPlayer2(int placarPlayer2){
       /*Esse método é responsável por carregar a pontuação do "Player 2" 
       a partir de um arquivo de texto chamado "PontucaoPlayer2.txt".*/
       try{
                    File myObj = new File("PontucaoPlayer2.txt");
                    Scanner myReader = new Scanner(myObj);
                    placarPlayer2 = myReader.nextInt();           
                    return placarPlayer2;    
                }catch(Exception e){System.out.println("erro");}
        return placarPlayer2;
   }
   
   public void lerPlacarPlayer2(){
       /*Esse método tem como objetivo ler a pontuação do "Player 2"
        armazenada no arquivo e atualizar o valor da pontuação na variável score.player2.*/
        int placarPlayer2 = 0;
        int resultado = carregarPontuacaoPlayer2(placarPlayer2);
        score.player2 = resultado;
   }
   
   
   
}
