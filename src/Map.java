import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	public int[][] map;
	
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
	
	private final int brickDensity = 1;//0<=brickDensity<=10
	
	private int enemyCount=10;
	public Boolean mapEnd = false;
	
	private int frame=34;
	
	private Random rand = new Random();
	private int randX = 0, randY=0;
	
	public BonusDoor door;
	
	public Map() {
		map = new int[31][19];
		
		initializeMap();
		initializeBonuses();
		initializeEnemyLocation();
	}
	
	private void initializeMap() {
		for(int x=0;x<31;x++)
			for(int y=0;y<19;y++){	
				if(x%2!=0 && y%2!=0)
					map[x][y] = -1;
				else if(rand.nextInt(10)<=brickDensity)
					map[x][y] = 1;
					else
						map[x][y] = 0;
			}
		
		map[0][0]=0;
		map[1][0]=0;
		map[2][0]=1;
		map[0][1]=0;
		map[0][2]=1;
		
	}
	
	private void initializeBonuses() {
		while(map[randX][randY]!=1){
			randX=rand.nextInt(30);
			randY=rand.nextInt(18);
		}
		bonuses.add(new BonusRange(randX, randY));
		randX=rand.nextInt(30);
		randY=rand.nextInt(18);
		while(map[randX][randY]!=1){
			randX=rand.nextInt(30);
			randY=rand.nextInt(18);
		}
		bonuses.add(new BonusBomb(randX, randY));
		while(map[randX][randY]!=1){
			randX=rand.nextInt(30);
			randY=rand.nextInt(18);
		}
		door = new BonusDoor(randX, randY,this);
	}
	
	private void initializeEnemyLocation() {
		for(int i=enemyCount;i>0;i--){
			randX=rand.nextInt(12)+3;
			randY=rand.nextInt(6)+3;
			//enemies.add(new EnemyRabbit(randX*(frame*2),randY*(frame*2),map));
			enemies.add(new EnemyCat(randX*(frame*2),randY*(frame*2),map));
			map[randX*2][randY*2]=0;
		}
	}	

}
