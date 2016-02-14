package gui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;


public class MapFrame extends JFrame{
	// constants
	private static final int frameWidth = 1200;
	private static final int frameHeight = 1000;
	private static final String frameTitle = "Kansas";
	//fields
	private Console cs = new Console();
	
	public MapFrame(){
		
	}
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		frame.setSize(frameWidth, frameHeight);
		frame.setTitle(frameTitle);
//		JPanel panel= new JPanel();
		
		EditPanel r = new EditPanel(frame);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		MapComponent component = new MapComponent();
		
		frame.add(r);
		
		frame.pack();
		frame.setVisible(true);
		
		// Test for closing only the specified window.
		JFrame f1 = new JFrame();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.pack();
//		f1.add(r);
		f1.setVisible(true);

	}
	
	
	class Console extends JTextArea {
		public Console() {
			this.setEditable(false);
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Console");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setPreferredSize(new Dimension(145, 5));
			this.append("Treevisualizer Helper:\n "
					+ " The add button add the \n  integer u put in in the \n  text field, \n"
					+ "  the Random button add \n  a random  node having \n  the random value\n  from 0 to 100, \n"
					+ "  the slider controls the \n  space between nodes.\n   NOW ADD A NODE!\n\n                   @author niz;)");

		}
	}
}
