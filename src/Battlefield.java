import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Battlefield extends JPanel implements ActionListener{

	//WTF?!
	private static final long serialVersionUID = 1L;
	
	private final int fps = 40;
	Timer mainTimer = new Timer(1000/fps,this);
	
	private final Image brick = new ImageIcon("resource/bricks.png").getImage();	
	
	public static int width = 1054;
	public static int height = 646;
	public static int frame = 34;
	
	Color floorColor = new Color(52, 218, 69);
	Color lColor = new Color(255, 239, 219);
	Color DColor = new Color(139, 131, 120);
	
	Map m ;
	
	private Hero p;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	private ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	private ArrayList<Map> maps = new ArrayList<Map>();
	
	private Thread audio =  new Thread(new Audio());
	
//	map legend
//	0-null
//	1-bricks
//	2-explosion
//	3-explosion bricks
//	4-bomb
	
	public Battlefield(){
		setPreferredSize(new Dimension(width,height));
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		maps.add(new Map());
		maps.add(new Map());
		m=maps.get(0);
		enemies = m.enemies;
		bonuses = m.bonuses;
		p = new Hero(m.map,2,2,2);
		mainTimer.start();
		audio.start();
	}

	private class MyKeyAdapter extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			p.keyPressed(e);
		}
		public void keyReleased(KeyEvent e){
			p.keyReleased(e);
		}	
	}
	
	public void paint(Graphics g){
		g = (Graphics2D) g;
		fillBattlefieldWalls(g);	
		fillBattlefieldBricks(g);
		drawEnemies(g);
		explosion(g);
		g.drawImage(p.img, p.x, p.y, null);
		drawBonuses(g);
	}
	
	private void drawBonuses(Graphics g) {
		if(bonuses.size()!=0)
			for (Bonus b : bonuses) {
				if(m.map[b.iX][b.iY]==0)
					g.drawImage(b.img, b.x, b.y, null);
			}
		if(m.map[m.door.iX][m.door.iY]==0)
			g.drawImage(m.door.img, m.door.x, m.door.y,null);
	}

	private void drawEnemies(Graphics g){
		for (Enemy en : enemies) {
			g.drawImage(en.img, en.x, en.y, null);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		p.moveNew();
		checkCollision1();
		checkWin();
		checkEnemyDied();
		repaint();
		initializeBomb();
		checkBombDied();
		checkBonusDied();
	}
	
	private void checkCollision1() {
		for (Enemy e : enemies) {
			if(p.getRect().intersects(e.getRect()) || m.map[(int)(p.x+p.centerX)/frame][(int)(p.y+p.centerY)/frame]==2){
				JOptionPane.showMessageDialog(null,"GAME OVER");
				System.exit(1);
			}
		}
	}
	
	private void checkBonusDied() {
		if(bonuses.size()!=0)
			for (int i=0;i<bonuses.size();i++) {
				if((int)(p.x+p.centerX)/frame==bonuses.get(i).iX && (int)(p.y+p.centerY)/frame==bonuses.get(i).iY){
					bonuses.get(i).effect(p);
					bonuses.remove(i);
				}
			}
	}

	private void checkBombDied(){
		for(Bomb bm : bombs)
			bm.checkBoom(m.map);
	}
	
	private void initializeBomb() {
		if(p.bombs>0){
			if(bombs.size()<p.bombCount && m.map[(int)(p.x+p.centerX)/frame][(int)(p.y+p.centerY)/frame]==0){
				bombs.add(new Bomb(p.x+p.centerX,p.y+p.centerY,p.range));
				m.map[(int)(p.x+p.centerX)/frame][(int)(p.y+p.centerY)/frame]=4;
			}
			p.bombs--;
		}
	}

	private void checkEnemyDied(){
		for(int i=0;i<enemies.size();i++){
			enemies.get(i).newMove();
			if(m.map[enemies.get(i).iX][enemies.get(i).iY]==2)
				enemies.remove(i);
		}
	}
	
	private void checkWin() {
		if(enemies.size()==0 && (int)(p.x+p.centerX)/frame == m.door.iX &&(int)(p.y+p.centerY)/frame == m.door.iY){
			m.door.effect(p);
			m=maps.get(1);
			p = new Hero(m.map,p.speed,p.range,p.bombCount);
			enemies = m.enemies;
			bonuses = m.bonuses;
		}
		
	}
	
	private void fillBattlefieldBricks(Graphics g){
		for(int x=0;x<31;x++)
			for(int y=0;y<19;y++){
				if(m.map[x][y]==1)
					g.drawImage(brick, x*frame, y*frame, null);
			}
	}

	private void fillBattlefieldWalls(Graphics g){

		
		g.setColor(floorColor);
		g.fillRect(0, 0, width, height);
		
		for(int x=frame;x<width-frame;x+=frame*2)
			for(int y=frame; y<height-frame;y+=frame*2){
				Color myColor = new Color(205, 183, 158);
				g.setColor(myColor);
				g.fillRect(x, y, frame, frame);
				
				g.setColor(lColor);
				g.drawLine(x+frame, y, x, y);
				g.drawLine(x, y+frame, x, y);
				
				g.setColor(DColor);
				g.drawLine(x+frame, y, x+frame, y+frame);
				g.drawLine(x, y+frame, x+frame, y+frame);
			}					
	}
	
	private void explosion(Graphics g){
						
		for(Bomb bm : bombs){
			g.drawImage(bm.img, bm.iX*frame, bm.iY*frame, null);
			if(bm.boom == 1){
				m.map[bm.iX][bm.iY]=2;
				for(int i = 1;i<=bm.range;i++)
					if(bm.iY%2==0){
						if(bm.iX+i<31){
							if(m.map[bm.iX+i][bm.iY]==0 || m.map[bm.iX+i][bm.iY]==2 || m.map[bm.iX+i][bm.iY]==4){
								g.drawImage(bm.explosions.get(0), (bm.iX+i)*frame, bm.iY*frame, null);
								m.map[bm.iX+i][bm.iY]=2;
							}
							else {
								m.map[bm.iX+i][bm.iY]=3;
								i=bm.range+1;
							}
						}
					}
				
				for(int i = 1;i<=bm.range;i++)
					if(bm.iY%2==0){
						if(bm.iX-i>=0){
							if(m.map[bm.iX-i][bm.iY]==0 || m.map[bm.iX-i][bm.iY]==2 || m.map[bm.iX-i][bm.iY]==4){
								g.drawImage(bm.explosions.get(0), (bm.iX-i)*frame, bm.iY*frame, null);
								m.map[bm.iX-i][bm.iY]=2;
							}
							else {
								m.map[bm.iX-i][bm.iY]=3;
								i=bm.range+1;
							}
						}
					}
				
				for(int i = 1;i<=bm.range;i++)
					if(bm.iX%2==0){
						if(bm.iY+i<19){
							if(m.map[bm.iX][bm.iY+i]==0 || m.map[bm.iX][bm.iY+i]==2 || m.map[bm.iX][bm.iY+i]==4){
								g.drawImage(bm.explosions.get(1), bm.iX*frame, (bm.iY+i)*frame, null);
								m.map[bm.iX][bm.iY+i]=2;
							}
							else {
								m.map[bm.iX][bm.iY+i]=3;
								i=bm.range+1;
							}
						}
					}
			
				for(int i = 1;i<=bm.range;i++)
					if(bm.iX%2==0){
						if(bm.iY-i>=0){
							if(m.map[bm.iX][bm.iY-i]==0 || m.map[bm.iX][bm.iY-i]==2 || m.map[bm.iX][bm.iY-i]==4){
								g.drawImage(bm.explosions.get(1), bm.iX*frame, (bm.iY-i)*frame, null);
								m.map[bm.iX][bm.iY-i]=2;
							}
							else {
								m.map[bm.iX][bm.iY-i]=3;
								i=bm.range+1;
							}
						}
					}
			}					
		}
		
		if(bombs.size()!=0)
			for(int i=0;i<bombs.size();i++)
				if(bombs.get(i).boom == 2 ){
					m.map[bombs.get(i).iX][bombs.get(i).iY]=0;
					for(int g1 = bombs.get(i).range;g1>0;g1--){
						if(bombs.get(i).iX+g1<31)
							if(m.map[bombs.get(i).iX+g1][bombs.get(i).iY]==3 || m.map[bombs.get(i).iX+g1][bombs.get(i).iY]==2)
								m.map[bombs.get(i).iX+g1][bombs.get(i).iY]=0;
						
						if(bombs.get(i).iX-g1>=0)
							if(m.map[bombs.get(i).iX-g1][bombs.get(i).iY]==3 || m.map[bombs.get(i).iX-g1][bombs.get(i).iY]==2)
								m.map[bombs.get(i).iX-g1][bombs.get(i).iY]=0;
						
						if(bombs.get(i).iY+g1<19)
							if(m.map[bombs.get(i).iX][bombs.get(i).iY+g1]==3 || m.map[bombs.get(i).iX][bombs.get(i).iY+g1]==2)
								m.map[bombs.get(i).iX][bombs.get(i).iY+g1]=0;
						
						if(bombs.get(i).iY-g1>=0)
							if(m.map[bombs.get(i).iX][bombs.get(i).iY-g1]==3 || m.map[bombs.get(i).iX][bombs.get(i).iY-g1]==2)
								m.map[bombs.get(i).iX][bombs.get(i).iY-g1]=0;
					}
					bombs.remove(i);
				}
	}
			
}
