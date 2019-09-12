
public class EnemyScorp extends Enemy{

	public EnemyScorp(int x, int y, int[][] map){
		this.x = x;
		this.y = y;
		this.map = map;
		
		speed=1;
		
		initializeAnim("Scorp", 4, 4, 5, 5);
		img = imgsTop.get(0);
	}
}
