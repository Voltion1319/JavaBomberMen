import javax.swing.ImageIcon;

public class BonusBomb extends Bonus{
	
	public BonusBomb(int iX, int iY) {
		img = new ImageIcon("resource/bomb.png").getImage();
		this.iX = iX;
		this.iY = iY;
		findMapPosition(iX, iY);
	}

	@Override
	public void effect(Hero h) {
		h.bombCount++;
		
	}

}
