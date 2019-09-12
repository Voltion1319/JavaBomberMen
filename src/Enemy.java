import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Enemy {
	
	protected ArrayList<Image> imgs = new ArrayList<Image>();
	protected ArrayList<Image> imgsTop = new ArrayList<Image>();
	protected ArrayList<Image> imgsBot = new ArrayList<Image>();
	protected ArrayList<Image> imgsLeft = new ArrayList<Image>();
	protected ArrayList<Image> imgsRight = new ArrayList<Image>();
	
	protected ArrayList<Integer> directions = new ArrayList<Integer>();
	protected int[][] map;
	
	public Image img ;
	
	protected int animationSpeed = 6;
	
	public int x;
	public int y;
	
	protected Random rand = new Random(); 

	protected int speed=1;
	
	protected final Integer top = 0;
	protected final Integer bot = 1;
	protected final Integer left = 2;
	protected final Integer right = 3;
	
	protected final int frame = 34;
	
	protected Integer direct = 0;
	protected int way = frame;
	
	public int iX,iY;
	
	protected int movement = 0, numb=0;
	
	
	public  Rectangle getRect(){
		return new Rectangle(x,y,img.getWidth(null),img.getHeight(null));
	}
	
	public void newMove(){
		animation();
		
		iX=(int)(x+img.getWidth(null)/2)/frame;
		iY=(int)(y+img.getHeight(null)/2)/frame;
		
		if(way==frame){
			
			directions.clear();
			
			if(iY!=1)
				if(map[iX][iY-1]==0)
					directions.add(top);
			if(iY<=17)
				if(map[iX][iY+1]==0)
					directions.add(bot);
			if(iX!=1)
				if(map[iX-1][iY]==0)
					directions.add(left);
			if(iX<=29)
				if(map[iX+1][iY]==0) 
					directions.add(right);
			
			if(directions.size()==0){
				return;
			}
			way=0;
			direct = directions.get(rand.nextInt(directions.size()));			
		}
		
		if(direct==top)
			y-=speed;
		else if(direct==bot)
			y+=speed;
		else if(direct==left)
			x-=speed;
		else if(direct==right)
			x+=speed;
		
		way+=speed;
		
	}

	protected void animation() {
		
		if(direct == top)
			imgs=imgsTop;
		else if(direct==bot)
			imgs=imgsBot;
		else if(direct==left)
			imgs=imgsLeft;
		else if(direct==right)
			imgs=imgsRight;
		
		movement+=speed;
		if(movement%(speed*animationSpeed)==0){
			numb++;
			if(numb==imgs.size())
				numb=0;	
		}
		img = imgs.get(numb);
	}
	
	protected void initializeAnim(String name, int t, int b, int l, int r){
		for(int i=1;i<=t;i++)
			imgsTop.add(new ImageIcon("resource/Enemy/"+name+"/enemy"+name+"T"+i+".png").getImage());
		for(int i=1;i<=t;i++)
			imgsBot.add(new ImageIcon("resource/Enemy/"+name+"/enemy"+name+"B"+i+".png").getImage());
		for(int i=1;i<=t;i++)
			imgsLeft.add(new ImageIcon("resource/Enemy/"+name+"/enemy"+name+"L"+i+".png").getImage());
		for(int i=1;i<=t;i++)
			imgsRight.add(new ImageIcon("resource/Enemy/"+name+"/enemy"+name+"R"+i+".png").getImage());
			
		
	}
}
