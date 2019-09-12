import javax.swing.ImageIcon;

public class BonusRange extends Bonus{
	
	
	public BonusRange(int iX, int iY){
		img = new ImageIcon("resource/fire.png").getImage();
		this.iX = iX;
		this.iY = iY;
		findMapPosition(iX, iY);
	}
	
	@Override
	public void effect(Hero p) {
		p.range++;
	}

}
