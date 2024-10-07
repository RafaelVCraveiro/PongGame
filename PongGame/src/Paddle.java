import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle{
        //A função geral dessa classe é definir o comportamento e as características do paddle.
	int id;
	int yVelocity;
	int speed = 10;
	
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
		super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
		this.id=id;
	}
	
	public void keyPressed(KeyEvent e) {
            //A função desse método é responder a eventos de pressionamento de tecla (KeyEvent) para controlar o movimento da bola.
		switch(id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(-speed);
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				setYDirection(speed);
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(-speed);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(speed);
			}
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
            //A função desse método é responder a eventos de liberação de tecla (KeyEvent) para interromper o movimento da bola.
		switch(id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_W) {
				setYDirection(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_S) {
				setYDirection(0);
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_UP) {
				setYDirection(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN) {
				setYDirection(0);
			}
			break;
		}
	}
	public void setYDirection(int yDirection) {
            //A função desse método é definir a direção da velocidade do paddle no eixo Y. 
		yVelocity = yDirection;
	}
	public void move() {
            //O método move() atualiza a posição vertical do paddle ao longo do eixo Y, de acordo com a velocidade definida em yVelocity.
		y= y + yVelocity;
	}
	public void draw(Graphics g) {
            //Esse método é responsável por desenhar o paddle em um componente gráfico.
		if(id==1)
			g.setColor(Color.blue);
		else
			g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}
}

