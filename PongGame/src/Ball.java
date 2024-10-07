import java.awt.*;
import java.util.*;

public class Ball extends Rectangle{
        //A função geral dessa classe é definir o comportamento e as características da bola.
	Random random;
	int xVelocity;
	int yVelocity;
	int initialSpeed = 2;
	
	Ball(int x, int y, int width, int height){
            //Esse método é usado para criar uma instância da classe "Ball" (bola) com posições iniciais (x, y), largura e altura definidas.
		super(x,y,width,height);
		random = new Random();
		int randomXDirection = random.nextInt(2);
		if(randomXDirection == 0)
			randomXDirection--;
		setXDirection(randomXDirection*initialSpeed);
		
		int randomYDirection = random.nextInt(2);
		if(randomYDirection == 0)
			randomYDirection--;
		setYDirection(randomYDirection*initialSpeed);
		
	}
	
	public void setXDirection(int randomXDirection) {
            //A função desse método é definir a direção da velocidade da bola no eixo X.
		xVelocity = randomXDirection;
	}
	public void setYDirection(int randomYDirection) {
            //A função desse método é definir a direção da velocidade da bola no eixo Y.
		yVelocity = randomYDirection;
	}
	public void move() {
            //A função desse método é atualizar a posição da bola de acordo com as velocidades definidas nos eixos X e Y.
		x += xVelocity;
		y += yVelocity;
	}
	public void draw(Graphics g) {
            //A função desse método é desenhar a bola na tela.
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);
	}
}
