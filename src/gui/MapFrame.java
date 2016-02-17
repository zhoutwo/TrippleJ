package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
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
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;

import backend.City;
import backend.Map;
import utils.FlexRedBlackTree;
import utils.RoundButton;


public class MapFrame extends JFrame{
	// constants
	private static final int FRAME_WIDTH = 1050;
	private static final int FRAME_HEIGHT = 930;
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
		
//		ArrayList<RoundButton> ab = 
		mp.mdp.addCityToMap();
//		for(int i=0;i<ab.size();i++){
//			mp.mdp.add(ab.get(i));
//		}
		this.setVisible(true);
//		mp.mdp.add(new JComponent(){
//			@Override
//		    public void paintComponent(Graphics g) {
//				System.out.println("i wam");
//		        super.paintComponent(g);
//		        g.setColor(Color.BLACK);
//		        g.fillOval(200, 200, 70, 70);
//		    }
//		});
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
			
			private void addCityToMap(){
				Iterator<City> i = currentMap.getPopTree().iterator();
				City temp;
				int x;
				int y;
//				ArrayList<RoundButton> ab = new ArrayList<RoundButton>();
				RoundButton c;
				while(i.hasNext()){
					temp = i.next();
					x = 100;
					y = 100;
					String n = temp.getName();
					c = new RoundButton(n,x,y,25);
//					ab.add(c);
//					if (g2 != null) {
//						g2.setPaint(Color.BLACK);
//						g2.fill(c);
////						System.out.println(c.getCenterX());
////						System.out.println(c.getBounds());
//						this.repaint();
//					}
					x = x+50;
					y = y+50;
					add(c);
				}
//				this.repaint();
//				return ab;
			}
			
		}
		
		public class ListDisplayPanel extends JPanel implements MouseListener{
			private int size;
			private ArrayList<Rectangle> arr;
			private ArrayList<JButton> buttons;
			
			public ListDisplayPanel() {
				super();
				size=10;
				buttons=new ArrayList<>();
				this.setPreferredSize(new Dimension(250, 650));
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				this.setMaximumSize(new Dimension(250, 650));
				Dimension ld = new Dimension(250, 50);
				for(int i=1;i<size;i++){
					JButton l = new JButton();
					l.setText("City "+i);
					l.setMinimumSize(ld);
					l.setPreferredSize(ld);
					l.setMaximumSize(ld);
					buttons.add(l);
				}
				for(int i=0;i<buttons.size();i++){
					this.add(buttons.get(i));
				}
//				JButton cb = new JButton("City 1");
//				Dimension cd = new Dimension(250, 30);
//				cb.setMinimumSize(cd);
//				cb.setPreferredSize(cd);
//				cb.setMaximumSize(cd);
//				l.addActionListener(new ActionListener() {
//
//					public void actionPerformed(ActionEvent arg0) {
//						removeAll1();
//					}
//
//					
//				});
//				this.add(l);
				
				
				
				
//				Dimension d = new Dimension(200, 650);
//				size=13;
//				this.setMinimumSize(d);
//				this.setPreferredSize(d);
//				this.setMaximumSize(d);
//				this.addMouseListener(this);
//				GridBagConstraints g=new GridBagConstraints();
//				g.gridx = 0;
//				g.gridy = 0;
//				g.weightx = 0.50;
//				g.weighty = 0.50;
//				g.ipadx = 10;
//				g.ipady = 10;
//				JScrollPane sp=new JScrollPane();
//				Box box=new Box(3);
//				box=box.createHorizontalBox();
//				sp.getViewport().add(box.createVerticalGlue(), g);
//				this.add(sp,BorderLayout.CENTER);
				
				
				
				
				
			}
			private void removeAll1() {
				this.removeAll();
				this.repaint();
			}
			
//			public ListDisplayPanel() {
//				super();
//				this.setBackground(Color.WHITE);
//				Dimension d = new Dimension(200, 650);
//				size=13;
//				this.setMinimumSize(d);
//				this.setPreferredSize(d);
//				this.setMaximumSize(d);
//				this.addMouseListener(this);
//			}
//			public GridBagConstraints initScrollPane(JScrollPane scrollPane ) {
//			    scrollPane.setLayout( new ScrollPaneLayout() );
//			    scrollPane.setBorder( BorderFactory.createEmptyBorder( 2, 2, 2, 2 ) );
//			    GridBagConstraints gb = new GridBagConstraints();
//			    gb.gridx = 0;
//			    gb.gridy = 0;
//			    gb.insets = new Insets( 2, 2, 2, 2 );
//			    return gb;
//			}
			
			
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				drawBoxes(g);
			}
			public void drawBoxes(Graphics g){
				
				
				
				
				
				
				Graphics2D g1=(Graphics2D) g;
				Rectangle r= new Rectangle(0, 0, 248, 50); 
				g1.setPaint(Color.yellow);
				for(int i=0;i<size;i++){
					r.setLocation(0, i*55);
					g1.draw(r);
					g1.fill(r);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//mouse.contains(e.getX(),e.getY())
				System.out.println("first box clicked");
				if(e.getButton()==1){
//					if()
				}
				
				
			}
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		
		public class SearchFormPanel extends JPanel {
			
			private JTextField from;
			private JTextField to;
			private JCheckBox lockFrom;
			private JCheckBox lockTo;
			private ButtonGroup options;
			private JRadioButton time;
			private JRadioButton noToll;
			private JRadioButton rating;
			private JRadioButton distance;
			
			public SearchFormPanel() {
				super();
				GroupLayout sl = new GroupLayout(this);
				
				// Some configuration of the GroupLayout
				this.setLayout(sl);
				sl.setAutoCreateGaps(true);
				sl.setAutoCreateContainerGaps(true);
				
//				this.setBackground(Color.BLACK);
				Dimension d = new Dimension(650, 80);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
				
				// Start initializing screen elements
				JLabel fromLabel = new JLabel("From: ");
				JLabel toLabel = new JLabel("To: ");
				from = new JTextField();
				to = new JTextField();
				lockFrom = new JCheckBox("lockf");
				lockTo = new JCheckBox("lockt");
				
				// Radio buttons belong to a ButtonGroup
				options = new ButtonGroup();
				time = new JRadioButton("time");
				noToll = new JRadioButton("no toll");
				rating = new JRadioButton("rating");
				distance = new JRadioButton("distance");
				options.add(time);
				options.add(noToll);
				options.add(rating);
				options.add(distance);
				
				JButton findRoute = new JButton("Find Route");
				JButton reset = new JButton("Reset");
				
				// Start adding elements
				sl.setHorizontalGroup(
						sl.createSequentialGroup()
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(fromLabel)
									.addComponent(toLabel)
									)
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(from)
									.addComponent(to)
									)
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(lockFrom)
									.addComponent(lockTo)
									)
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.LEADING)
									.addComponent(time)
									.addComponent(noToll)
									.addComponent(rating)
									.addComponent(distance)
									)
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(findRoute, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(reset, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									)
						);
				
				sl.setVerticalGroup(
						sl.createSequentialGroup()
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(fromLabel)
									.addComponent(from)
									.addComponent(lockFrom)
									.addGroup(sl.createSequentialGroup()
											.addComponent(time)
											.addComponent(noToll)
											)
									.addComponent(findRoute)
									)
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(toLabel)
									.addComponent(to)
									.addComponent(lockTo)
									.addGroup(sl.createSequentialGroup()
											.addComponent(rating)
											.addComponent(distance)
											)
									.addComponent(reset)
									)
							
							
						);
				System.out.println(this.getPreferredSize());
			}
			
		}
		
		public class EditButtonPanel extends JPanel {
			
			public EditButtonPanel() {
				super();
				this.setBackground(Color.BLUE);
				Dimension d = new Dimension(200, 80);
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
