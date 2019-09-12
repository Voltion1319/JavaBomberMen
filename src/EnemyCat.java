public class EnemyCat extends Enemy{
	
	public EnemyCat(int x, int y, int[][] map){
		this.x = x;
		this.y = y;
		this.map = map;
		
		speed=2;
		
		initializeAnim("Cat", 3, 3, 3, 3);
		img = imgsTop.get(0);
		
	}
	
	
}
