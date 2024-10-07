import java.awt.*;

public class Score extends Rectangle{
        //A classe Score é responsável por gerenciar e exibir a pontuação do jogo. 
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player1;
	int player2;
        
	
	Score(int GAME_WIDTH, int GAME_HEIGHT){
            //Construtor da classe
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
	}
	public void draw(Graphics g) {
            //Esse método é responsável por desenhar a pontuação na tela ou em um componente gráfico.
		g.setColor(Color.white);
		g.setFont(new Font("Consolas",Font.PLAIN,60));
		
		g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
		
		g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)-105, 50);
		g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDTH/2)+5, 50);
	}
}
