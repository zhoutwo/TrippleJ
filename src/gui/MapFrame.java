package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.StyledEditorKit.BoldAction;

import backend.City;
import backend.Map;


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
			
			private ArrayList<CircleLabel> cls;
			
			public MapDisplayPanel() {
				super();
				this.setBackground(Color.GREEN);
				Dimension d = new Dimension(650, 650);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
				
				this.addMouseListener(new MouseListener() {

					public void mouseClicked(MouseEvent e) {
						for (CircleLabel cl : cls) {
							if (cl.contains(e.getX(), e.getY())) {
								// set the to and from fields 
								if(mp.sfp.lockFrom.isSelected()){
									mp.sfp.to.setText(cl.getLabel());
								}
								else if(mp.sfp.lockTo.isSelected()){
									mp.sfp.from.setText(cl.getLabel());
								}
								else{
									// both to and from are locked in 
									// do something or do nothing
								}
								return;
							}
						}
						System.out.println("Mouse click detected on map!");
					}

					public void mouseEntered(MouseEvent e) {
					}

					public void mouseExited(MouseEvent e) {
					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseReleased(MouseEvent e) {					
					}
					
				});
			}
			
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				for (CircleLabel cl : cls) {
					g2.setPaint(Color.RED);
					g2.draw(cl);
					g2.setPaint(Color.YELLOW);
					g2.fill(cl);
					g2.setPaint(Color.BLACK);
					g2.drawString(cl.getLabel(), (float) cl.getMaxX(), (float) cl.getCenterY());
				}
			}
			
			private void addCityToMap(){
				cls = new ArrayList<CircleLabel>();
				Iterator<City> i = currentMap.getPopTree().iterator();
				int x;
				int y;
				City temp;
				Point location;
				for (City c : currentMap.getPopTree()) {
					temp = i.next();
					location = temp.getMapLoc();
					x = (int)location.getX();
					y = (int)location.getY();
					cls.add(new CircleLabel(temp.getName(),x,y,25));
				}
//				this.repaint();
			}
			
			public class CircleLabel extends Ellipse2D.Double {
				private final String t;
				
				public CircleLabel (String t, int x, int y, int s) {
					super(x, y, s, s);
					this.t = t;
				}
				
				public String getLabel() {
					return t;
				}
			}
		}
		
		public class ListDisplayPanel extends JPanel implements MouseListener{
			private int size;
			private ArrayList<Rectangle> arr;
			private ArrayList<JButton> cityListButtons;
			private ArrayList<JButton> cityInfoButtons;
			
			public ListDisplayPanel() {//there is going to be parameter of some data structure of cities.
				super();
				size=10;
				cityListButtons=new ArrayList<>();
				cityInfoButtons=new ArrayList<>();
				this.setPreferredSize(new Dimension(250, 650));
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				this.setMaximumSize(new Dimension(250, 650));
//				this.drawCityInfoButtons();
				this.initCitListButtons();
				this.drawCityListButtons();
			}
			private void initInfoButtons() {
				
			}
			private void removeAll1() {
				this.removeAll();
				this.validate();
				this.repaint();
			}
			
			
			
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				
			}
			
			private void drawCityInfoButtons() {
				Dimension d = new Dimension(250, 50);
				for (int i = 1; i < size; i++) {
					JButton l = new JButton();
					l.setText("City " + i);
					l.setMinimumSize(d);
					l.setPreferredSize(d);
					l.setMaximumSize(d);
					cityListButtons.add(l);
					l.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent arg0) {
							removeAll1();
							drawCityInfoButtons();
							repaint();
						}

					});
				}
				this.setPreferredSize(new Dimension(250, 650));
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				this.setMaximumSize(new Dimension(250, 650));
				Dimension dimText = new Dimension(250, 300);
				JTextArea txt = new JTextArea("Some information");// put a
																	// string of
																	// inforamtion
																	// here
				Font font = new Font("", Font.PLAIN, 15);
				txt.setFont(font);
				txt.setMinimumSize(dimText);
				txt.setPreferredSize(dimText);
				txt.setMaximumSize(dimText);
				txt.setEditable(false);
				this.add(txt);
				for (int i = 1; i < 5; i++) {
					JButton POIButton = new JButton();
					POIButton.setText("POI " + i);
					POIButton.setMinimumSize(d);
					POIButton.setPreferredSize(d);
					POIButton.setMaximumSize(d);
					cityInfoButtons.add(POIButton);
					POIButton.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent arg0) {
							removeAll1();
							drawCityListButtons();
						}

					});
				}
				for (int i = 0; i < cityInfoButtons.size(); i++) {
					this.add(cityInfoButtons.get(i));
				}
				
			}
			private void initCitListButtons(){
				Dimension d = new Dimension(250, 50);
				for(int i=1;i<size;i++){
					JButton l = new JButton();
					l.setText("City "+i);
					l.setMinimumSize(d);
					l.setPreferredSize(d);
					l.setMaximumSize(d);
					cityListButtons.add(l);
					l.addActionListener(new ActionListener() {
					
										public void actionPerformed(ActionEvent arg0) {
											removeAll1();
											drawCityInfoButtons();
											repaint();
										}
					
										
									});
				}
			}
			private void drawCityListButtons() {
				for(int i=0;i<cityListButtons.size();i++){
					this.add(cityListButtons.get(i));
				}				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
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
}
