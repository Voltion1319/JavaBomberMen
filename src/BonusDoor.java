import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class BonusDoor extends Bonus{

	Map map;
	public BonusDoor(int iX, int iY,Map map){
		img = new ImageIcon("resource/door.png").getImage();
		this.iX = iX;
		this.iY = iY;
		findMapPosition(iX, iY);
		this.map = map;
	}
	
	
	@Override
	public void effect(Hero p) {
		JOptionPane.showMessageDialog(null,"You win!!");
		map.mapEnd=true;
		//System.exit(1);
		
	}

}
