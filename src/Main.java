import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("Bomber^^");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.add(new Battlefield());
		f.setVisible(true);
		f.pack();
	}

}
