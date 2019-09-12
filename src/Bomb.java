import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Bomb implements ActionListener {
	
	ArrayList<Image> imgs = new ArrayList<Image>();
	ArrayList<Image> explosions = new ArrayList<Image>();
	Image img = new ImageIcon("resource/bomb1.png").getImage();
	Timer timer = new Timer(500,this );
	
	
	int iX= 0;
	int iY = 0;
	int s = 0;
	int range = 0;

	int boom = 0;
	
	public Bomb(int x, int y,int range){
		this.iX = x/34;
		this.iY = y/34;		
		this.range = range;
		
		imgs.add(new ImageIcon("resource/bomb1.png").getImage());
		imgs.add(new ImageIcon("resource/bomb2.png").getImage());
		imgs.add(new ImageIcon("resource/bomb3.png").getImage());
		imgs.add(new ImageIcon("resource/bomb4.png").getImage());
		
		explosions.add(new ImageIcon("resource/explosion.png").getImage());
		explosions.add(new ImageIcon("resource/explosionV.png").getImage());
		
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		s++;
		
		if(s==1)
			img = imgs.get(1);
		if(s==3)
			img = imgs.get(2);
		
		if(s==5 || s==6){
			boom ++;
			img = imgs.get(3);
		}

		if(s==7)timer.stop();
	}
	
	public void checkBoom(int[][] map){
		if(map[iX][iY]==2 && boom==0){
			boom++;
			img = imgs.get(3);
		}
			
	}

}
