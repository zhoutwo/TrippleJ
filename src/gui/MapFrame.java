package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import backend.City;
import backend.Map;
import utils.FlexRedBlackTree;


public class MapFrame extends JFrame{
	// constants
	private static final int FRAME_WIDTH = 1050;
	private static final int FRAME_HEIGHT = 900;
	private static final String FRAME_TITLE = "Kansas";
	// fields
	private Console cs;
	private MapPanel mp;
	private int state;
	private City selectedCity;
	private Map currentMap;
	
	
	public MapFrame(Map map){
		super();
		currentMap = map;
		cs = new Console();
		Dimension d = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(FRAME_TITLE);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		mp = new MapPanel();
		this.add(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Centers it
		this.setVisible(true);
		mp.mdp.add(new JComponent(){
			@Override
		    public void paintComponent(Graphics g) {
				System.out.println("i wam");
		        super.paintComponent(g);
		        g.setColor(Color.BLACK);
		        g.fillOval(200, 200, 70, 70);
		    }
		});
//		Graphics2D gg = (Graphics2D) mp.mdp.getGraphics();
//		gg.setColor(Color.BLACK);
//		mp.mdp.addCityToMap(gg);
//		this.repaint();
	}
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//
//		frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
////		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
//		frame.setTitle(FRAME_TITLE);
////		JPanel panel= new JPanel();
//		
//		EditPanel r = new EditPanel(frame);
//		
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
////		MapComponent component = new MapComponent();
//		
//		frame.add(r);
//		
//		frame.pack();
//		frame.setVisible(true);
//		
//		// This will close all windows.
//		JFrame f1 = new JFrame();
//		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f1.pack();
////		f1.add(r);
//		f1.setVisible(true);
//
//	}

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
	
	public class MapPanel extends JPanel {
//		private Map stateMap;
		private MapDisplayPanel mdp;
		private ListDisplayPanel ldp;
		private SearchFormPanel sfp;
		private EditButtonPanel ebp;
		
		public MapPanel() {
			super(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			// Inserting MapDisplayPanel
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 0.50;
			c.weighty = 0.50;
			c.ipadx = 50;
			c.ipady = 50;
			mdp = new MapDisplayPanel();
			this.add(mdp, c);
			// Inserting ListDisplayPanel
			c.gridx = 1;
			c.gridy = 0;
			c.weightx = 0.50;
			c.weighty = 0.50;
			c.ipadx = 50;
			c.ipady = 50;
			ldp = new ListDisplayPanel();
			this.add(ldp, c);
			// Inserting SearchFormPanel
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 0.50;
			c.weighty = 0.50;
			c.ipadx = 50;
			c.ipady = 50;
			sfp = new SearchFormPanel();
			this.add(sfp, c);
			// Inserting EditButtonPanel
			c.gridx = 1;
			c.gridy = 1;
			c.weightx = 0.50;
			c.weighty = 0.50;
			c.ipadx = 50;
			c.ipady = 50;
			ebp = new EditButtonPanel();
			this.add(ebp, c);
		}
		
		public class MapDisplayPanel extends JPanel {
			
			public MapDisplayPanel() {
				super();
				this.setBackground(Color.GREEN);
				Dimension d = new Dimension(650, 650);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
				
			}
			
//			private void addCityToMap(Graphics2D g2){
//				Iterator<City> i = currentMap.getPopTree().iterator();
//				City temp;
//				double x;
//				double y;
//				while(i.hasNext()){
//					temp = i.next();
//					x = 500.0;
//					y = 500.0;
//					String n = temp.getName();
//					CityShape c = new CityShape(n,x,y);
//					if (g2 != null) {
//						g2.setPaint(Color.BLACK);
//						g2.fill(c);
//						System.out.println(c.getCenterX());
////						System.out.println(c.getBounds());
//						this.repaint();
//					}
//					
//				}
//				this.repaint();
//			}
			
		}
		
		public class ListDisplayPanel extends JPanel {
			
			public ListDisplayPanel() {
				super();
				this.setBackground(Color.WHITE);
				Dimension d = new Dimension(200, 650);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
			}
			
		}
		
		public class SearchFormPanel extends JPanel {
			
			public SearchFormPanel() {
				super();
				this.setBackground(Color.BLACK);
				Dimension d = new Dimension(650, 50);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
			}
			
		}
		
		public class EditButtonPanel extends JPanel {
			
			public EditButtonPanel() {
				super();
				this.setBackground(Color.BLUE);
				Dimension d = new Dimension(200, 50);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
				JButton edit = new JButton("Edit");
				edit.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						new EditFrame();
					}
				});
				this.add(edit);
			}
		}
	}
	
//	public class CityShape extends Ellipse2D.Double implements Shape, MouseListener{
//		
//		private final static double SIZE = 200.0;
//		private String cityName;
//		
//		public CityShape(String name,double x,double y){
//			super(x,y,SIZE,SIZE);
//			cityName = name;
//		}
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			// TODO Auto-generated method stub
//			System.out.println(cityName+"was clicked");
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
}
