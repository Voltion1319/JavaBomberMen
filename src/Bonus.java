import java.awt.Image;

public abstract class Bonus {
	
	Image img;
	int x;
	int y;
	int iX;
	int iY;
	
	abstract public void effect(Hero p);
	
	protected void findMapPosition(int iX, int iY){
		x = iX*34;
		y = iY*34;
	}

}
