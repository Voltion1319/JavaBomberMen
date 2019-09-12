import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Hero{
	private ArrayList<Image> imgs = new ArrayList<Image>();
	Image img;
	
	private int[][] map;
	
	private int speedX = 0;
	private int speedY = 0;
	private final int frame = 34;
	private final int limitValueCorrection = 8;
	
	public int x = 2;
	public int y = 2;
	
	public int iX1=0,iX2=0;
	public int iY1=0,iY2=0;
	
	public int centerX ;
	public int centerY ;
	
	
	public int speed = 2;
	public int bombs = 0;
	public int range = 2;
	public int bombCount = 2;
	
	private int movement = 0, numb=0, direct=0;
	
	public Hero(int[][] map,int speed,int range,int bombcount){
		
		imgs.add(new ImageIcon("resource/Heroes/PlayerT1.png").getImage());//0
		imgs.add(new ImageIcon("resource/Heroes/PlayerT2.png").getImage());
		imgs.add(new ImageIcon("resource/Heroes/PlayerT3.png").getImage());
		
		imgs.add(new ImageIcon("resource/Heroes/Player1.png").getImage());//3
		imgs.add(new ImageIcon("resource/Heroes/Player2.png").getImage());
		imgs.add(new ImageIcon("resource/Heroes/Player3.png").getImage());
		
		imgs.add(new ImageIcon("resource/Heroes/PlayerL1.png").getImage());//6
		imgs.add(new ImageIcon("resource/Heroes/PlayerL2.png").getImage());
		imgs.add(new ImageIcon("resource/Heroes/PlayerL3.png").getImage());
		
		imgs.add(new ImageIcon("resource/Heroes/PlayerR1.png").getImage());//9
		imgs.add(new ImageIcon("resource/Heroes/PlayerR2.png").getImage());
		imgs.add(new ImageIcon("resource/Heroes/PlayerR3.png").getImage());
		
		this.map=map;
		this.bombCount=bombcount;
		this.range=range;
		
		img = imgs.get(0);
		centerX = (int)img.getWidth(null)/2;
		centerY = (int)img.getHeight(null)/2;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,img.getWidth(null),img.getHeight(null));
	}

	public void moveNew(){
		animation();
		if(x+speedX>0 && x+speedX<1032 && y+speedY>0 && y+speedY<614 ){//проверка выхода за карту 
			findMapPosition();
			if(Math.abs(map[iX1][iY1])!=1 && Math.abs(map[iX2][iY2])!=1)//проверка на кирпичи или стену
				step(0);
			else correction();
		}
	}
	
	private void correction(){
		if(speedX<0 && map[leftiX(speedX)][topiY(limitValueCorrection)]==0 && map[leftiX(speedX)][topiY(0)]==-1){
			x=(leftiX(speedX)+1)*frame;
			y=topiY(limitValueCorrection)*frame;
		}
			
		else if(speedX<0 && map[leftiX(speedX)][botiY(-limitValueCorrection)]==0 && map[leftiX(speedX)][botiY(0)]==-1){
			x=(leftiX(speedX)+1)*frame;
			y=botiY(-limitValueCorrection)*frame+2;
		}
			
		else if(speedX>0 && map[rightiX(speedX)][botiY(-limitValueCorrection)]==0 && map[rightiX(speedX)][botiY(0)]==-1){
			x=(rightiX(speedX)-1)*frame+frame-img.getWidth(null);
			y=botiY(-limitValueCorrection)*frame+2;
		}
			
		else if(speedX>0 && map[rightiX(speedX)][topiY(limitValueCorrection)]==0 && map[rightiX(speedX)][topiY(0)]==-1){
			x=(rightiX(speedX)-1)*frame+frame-img.getWidth(null);
			y=topiY(limitValueCorrection)*frame;
		}
			
	}
	
	private void step(int correction){
		x+=speedX;
		y+=speedY+correction;
	}
	
	private void findMapPosition(){
		if(speedX<0){
			iX1 = leftiX(speedX);
			iY1 = topiY(speedY);
			
			iX2 = leftiX(speedX);
			iY2 = botiY(speedY);
		}
		else if(speedX>0){
			iX1 = rightiX(speedX);
			iY1 = topiY(speedY);
			
			iX2 = rightiX(speedX);
			iY2 = botiY(speedY);
		}
		else if(speedY<0){
			iX1 = leftiX(speedX);
			iY1 = topiY(speedY);
			
			iX2 = rightiX(speedX);
			iY2 = topiY(speedY);
		}
		else if(speedY>0){
			iX1 = leftiX(speedX);
			iY1 = botiY(speedY);
			
			iX2 = rightiX(speedX);
			iY2 = botiY(speedY);
		}
	}
	
	private int leftiX(int correction){
		return (int)(x+correction)/frame;
	}
	
	private int rightiX(int correction){
		return (int)(x+img.getWidth(null)+correction)/frame;
	}
	
	private int topiY(int correction){
		return (int)(y+correction)/frame;
	}
	
	private int botiY(int correction){
		return (int)(y+img.getHeight(null)+correction)/frame;
	}
	
	private void animation(){
		if(movement == 10) movement=0;
		
		movement = movement + Math.abs(speedX) + Math.abs(speedY);
		if(movement<3)
			numb=0;
		else if(movement<=6)
			numb=1;
		else if(movement<10)
			numb=2;
		
		img = imgs.get(direct+numb);
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT && speedY==0){
			speedX = speed;
			direct = 9;
		}
		if (key == KeyEvent.VK_LEFT && speedY==0){
			speedX =-speed;
			direct = 6;
		}
		if (key == KeyEvent.VK_UP && speedX==0){
			speedY = -speed;
			direct = 0;
		}
		if (key == KeyEvent.VK_DOWN && speedX==0){
			speedY = speed;
			direct = 3;
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT ||key == KeyEvent.VK_LEFT ||key == KeyEvent.VK_UP ||key == KeyEvent.VK_DOWN){
			speedX=0;
			speedY=0;
		}
		if (key == KeyEvent.VK_X){			
			bombs++;
		}

	}
	
}
