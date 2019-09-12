
public class EnemyRabbit extends Enemy{
	
	public EnemyRabbit(int x, int y, int[][] map){
		
		this.x = x;
		this.y = y;
		this.map = map;
		
		speed=2;
		
		initializeAnim("Rabbit", 3, 4, 3, 3);
		img = imgsTop.get(0);
		
	}
	
	public void newMove(){
		animation();
		
		iX=(int)(x+img.getWidth(null)/2)/frame;
		iY=(int)(y+img.getHeight(null)/2)/frame;
		
		if(way==frame*2){
			
			directions.clear();
			
			if(iY!=2)
				if(map[iX][iY-2]==0)
					directions.add(top);
			if(iY<=16)
				if(map[iX][iY+2]==0)
					directions.add(bot);
			if(iX!=2)
				if(map[iX-2][iY]==0)
					directions.add(left);
			if(iX<=28)
				if(map[iX+2][iY]==0) 
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

}
