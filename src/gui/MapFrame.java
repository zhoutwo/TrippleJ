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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.StyledEditorKit.BoldAction;

import backend.City;
import backend.Link;
import backend.Map;
import backend.POI;
import backend.Place;
import utils.RoadType;


public class MapFrame extends JFrame{
	// constants
	private static final int FRAME_WIDTH = 1100;
	private static final int FRAME_HEIGHT = 930;
	private static final String FRAME_TITLE = "Kansas";
	private static final int CITY_SIZE = 20;
	
	// fields
	private MapPanel mp;
//	private int state;
	private final Stack<Place> selectedPlaces;
//	private City selectedCity;
//	private POI selectedPOI;
//	private Place selectedPlace;
	private Place selectedFromPlace;
	private Place selectedToPlace;
	private Map currentMap;
	
	
	public MapFrame(Map map){
		super();
		currentMap = map;
		Dimension d = new Dimension(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(FRAME_TITLE);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		
		selectedPlaces = new Stack<Place>();
		
		mp = new MapPanel();
		this.add(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Centers it
		
		mp.mdp.addCityToMap();

		this.setVisible(true);
		// the following code is for testing purposes only 
//		ArrayList<City> testcitylist = currentMap.getPopCityList();
//		for(int i=0;i<testcitylist.size();i++){
//			ArrayList<Link> nbors = testcitylist.get(i).getNeighbors();
//			for(int k=0;k<nbors.size();k++){
//				System.out.println("from "+testcitylist.get(i).getName()+nbors.get(k).toString());
//			}
//		}
//		System.out.println("this one "+currentMap.getPlaces().toString());
//		ArrayList<Place> route = currentMap.getRoute(" Kansas City"," Overland Park","distance");
//		System.out.println(route);
	}

	private void placeSelected(Place p) {
		selectedPlaces.push(p);
		// Set the to and from fields 
		if (mp.sfp.lockFrom.isSelected()) {
			if (!mp.sfp.lockTo.isSelected()) {
				// Updating To city
				mp.sfp.to.setText(p.getName());
				selectedToPlace = p;
//				if (!cl.getCity().getName().equals(cl.getLabel())) throw new RuntimeException("Labels are different!");
			}
		} else {
			// Updating From city
			mp.sfp.from.setText(p.getName());
			selectedFromPlace = p;
		}
		mp.ldp.drawList();
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
			private ArrayList<RoadLine> roads;
			
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
								
								placeSelected(cl.getCity());
								return;
							}
						}
						selectedPlaces.clear();
						ldp.drawList();
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
				for (RoadLine rd : roads) {
					if(rd.getRtype().equals(RoadType.INTERSTATE)){
						g2.setPaint(Color.RED);
					}
					else if(rd.getRtype().equals(RoadType.HIGHWAY)){
						g2.setPaint(Color.BLACK);
					}
					else{
						g2.setPaint(Color.GRAY);
					}
					g2.draw(rd);
					g2.fill(rd);
//					g2.setPaint(Color.BLACK);
//					g2.drawString(rd.getLabel(), (float) cl.getMaxX(), (float) cl.getCenterY());
				}
				for (CircleLabel cl : cls) {
					g2.setPaint(Color.YELLOW);
					g2.draw(cl);
//					g2.setPaint(Color.YELLOW);
					g2.fill(cl);
					g2.setPaint(Color.BLACK);
					g2.drawString(cl.getLabel(), (float) cl.getMaxX(), (float) cl.getCenterY());
				}
			}
			
			private void addCityToMap(){
				cls = new ArrayList<CircleLabel>();
				roads = new ArrayList<RoadLine>();
				ArrayList<Link> links;
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
					cls.add(new CircleLabel(temp.getName(), x, y, CITY_SIZE, c));
					links = temp.getNeighbors();
					for(int t=0;t<links.size();t++){
						roads.add(new RoadLine(links.get(t).getRoadType(),location.getX()+(CITY_SIZE/2), location.getY()+(CITY_SIZE/2), links.get(t).getPlace().getMapLoc().getX()+(CITY_SIZE/2), links.get(t).getPlace().getMapLoc().getY()+(CITY_SIZE/2)));
					}
				}
			}
			
			public class RoadLine extends Line2D.Double{
				private RoadType type;
				
				public RoadLine(RoadType ptype,double w,double x,double y,double z){
					super(w,x,y,z);
					type = ptype;
				}
				
				public RoadType getRtype(){
					return type;
				}
			}
			
			public class CircleLabel extends Ellipse2D.Double {
				private final String t;
				private final City c;
				
				public CircleLabel (String t, int x, int y, int s, City c) {
					super(x, y, s, s);
					this.t = t;
					this.c = c;
				}
				
				public String getLabel() {
					return t;
				}
				
				public City getCity() {
					return c;
				}
			}
		}
		
		public class ListDisplayPanel extends JPanel{
			private final InfoArea txt;
			private final BackButton back;
			private final JPanel list;
			private final JPanel orderOptions;
			private final ButtonGroup orders;
			private final JRadioButton alp;
			private final JRadioButton pop;
			private final JRadioButton rat;
			
			public ListDisplayPanel() {//there is going to be parameter of some data structure of cities.
				super();
				txt = new InfoArea();
				Dimension d = new Dimension(250, 650);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
				
				back = new BackButton();

				// Initialize List Panel
				list = new JPanel();
//				list.setMinimumSize(d);
//				list.setPreferredSize(d);
//				list.setMaximumSize(d);
				list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
				this.add(list);
				
				// Create gap to place options at the bottom.
				this.add(Box.createVerticalGlue());
				
				// Initialize radio buttons
				orderOptions = new JPanel();
				orderOptions.setLayout(new BoxLayout(orderOptions, BoxLayout.X_AXIS));
				orders = new ButtonGroup();
				ChangeListener rbl = new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						drawList();
					}
				};
				alp = new JRadioButton("Alphabetical");
				alp.addChangeListener(rbl);
				pop = new JRadioButton("Population");
				pop.addChangeListener(rbl);
				rat = new JRadioButton("Rating");
				rat.addChangeListener(rbl);
				orderOptions.add(alp);
				orderOptions.add(pop);
				orderOptions.add(rat);
				orders.add(alp);
				orders.add(pop);
				orders.add(rat);
				alp.setSelected(true);
				
				this.add(orderOptions);
				
				drawList();
			}
			
			private void drawList() {
				list.removeAll();
				if (selectedPlaces.isEmpty()) {
					for (City c : getCityList()) {
						list.add(new PlaceButton(c.getName(), c));
					}
				} else if (selectedPlaces.peek() instanceof City) {
					City c = (City) selectedPlaces.peek();
					txt.setPlace(c);
					list.add(txt);
					for (POI p : getPOIList(c)) {
						list.add(new PlaceButton(p.getName(), p));
					}
					list.add(back);
				} else {
					POI poi = (POI) selectedPlaces.pop();
					City c = (City) selectedPlaces.peek();
					selectedPlaces.push(poi);
					txt.setPlace(poi);
					list.add(txt);
					for (POI p : getPOIList(c)) {
						list.add(new PlaceButton(p.getName(), p));
					}
					list.add(back);
				}
				this.updateUI();
			}
			
			public ArrayList<City> getCityList() {
				if (orders.getSelection().equals(alp.getModel())) {
					return currentMap.getAlpCityList();
				} else if (orders.getSelection().equals(rat.getModel())) {
					return currentMap.getRatCityList();
				} else {
					return currentMap.getPopCityList();
				}
			}
			
			public ArrayList<POI> getPOIList(City c) {
				if (orders.getSelection().equals(alp.getModel())) {
					return c.getAlpPOITree().toArrayList();
				} else {
					return c.getRatPOITree().toArrayList();
				}
			}
			
			public class InfoArea extends JTextArea {
				
				public InfoArea() {
					super();
					Dimension d = new Dimension(250, 300);
					this.setMinimumSize(d);
					this.setPreferredSize(d);
					this.setMaximumSize(d);
					this.setEditable(false);
					this.setAlignmentX(CENTER_ALIGNMENT);
				}
				
				public void setPlace(Place p) {
					setText(null);
					append(p.getName() + '\n');
					append("Rating: " + p.getRating() + "/5.0\n");
					if (p instanceof City) {
						append("Population: " + ((City) p).getPopulation());
					} else {
						append("Type: " + ((POI) p).getType() + '\n');
						append("Estimated Cost:" + ((POI) p).getCost());
					}					
				}
			}
			
			public class PlaceButton extends JButton {
				private final Place p;
				public PlaceButton(String s, Place place) {
					super(s);
					Dimension d = new Dimension(250, 50);
					this.setMinimumSize(d);
					this.setPreferredSize(d);
					this.setMaximumSize(d);
					this.setAlignmentX(CENTER_ALIGNMENT);
					
					p = place;
					
					this.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (!selectedPlaces.isEmpty() && selectedPlaces.peek() instanceof POI) {
								selectedPlaces.pop();
							} else {
//								alp.setSelected(true);
								if (pop.isSelected()) alp.setSelected(true);
								pop.setEnabled(false);
							}
							placeSelected(p);
						}
					});
				}
				
				public Place getPlace() {
					return p;
				}
			}
			
			public class BackButton extends JButton {
				public BackButton() {
					super("Back");
					Dimension d = new Dimension(250, 50);
					this.setMinimumSize(d);
					this.setPreferredSize(d);
					this.setMaximumSize(d);
					this.setAlignmentX(CENTER_ALIGNMENT);
					
					this.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							selectedPlaces.clear();
							pop.setEnabled(true);
							drawList();
						}
					});
				}
			}
		}
		
		public class SearchFormPanel extends JPanel {
			
			private JTextField from;
			private JTextField to;
			private JCheckBox lockFrom;
			private JCheckBox lockTo;
			private ButtonGroup options;
			private JRadioButton time;
//			private JRadioButton noToll;
//			private JRadioButton rating;
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
				from = new JTextField() {
					// Override this method because if it is not enabled we don't want to change text;
					public void setText(String t) {
						if (this.isEnabled()) {
							super.setText(t);
						}
					}
				};
				
				// To is disabled until from is locked.
				to = new JTextField() {
					// Override this method because if it is not enabled we don't want to change text;
					public void setText(String t) {
						if (this.isEnabled()) {
							super.setText(t);
						}
					}
				};
				to.setEnabled(false);
				
				lockFrom = new JCheckBox("lockf");
				lockFrom.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							// Lock From field
							from.setEnabled(false);
							
							// Enable changing of To field
							to.setEnabled(true);
							lockTo.setEnabled(true);
						} else {
							// Unlock From field
							from.setEnabled(true);
							
							// Disable To field
							to.setEnabled(false);
							lockTo.setEnabled(false);
						}
					}
				});
				
				// To is disabled until from is locked.
				lockTo = new JCheckBox("lockt");
				lockTo.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (e.getStateChange() == ItemEvent.SELECTED) {
							// Lock To field
							to.setEnabled(false);
						} else {
							// Unlock To field
							to.setEnabled(true);
						}
					}
				});
				lockTo.setEnabled(false);
				
				// Radio buttons belong to a ButtonGroup
				options = new ButtonGroup();
				time = new JRadioButton("time");
//				noToll = new JRadioButton("no toll");
//				noToll.setEnabled(false);
//				rating = new JRadioButton("rating");
//				rating.setEnabled(false);
				distance = new JRadioButton("distance");
				options.add(time);
//				options.add(noToll);
//				options.add(rating);
				options.add(distance);
				
				JButton findRoute = new JButton("Find Route");
				findRoute.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						
					}
				});
				JButton reset = new JButton("Reset");
				reset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						from.setEnabled(true);
						from.setText(null);
						lockFrom.setEnabled(true);
						lockFrom.setSelected(false);
						lockTo.setEnabled(false);
						lockTo.setSelected(false);
						to.setEnabled(true);
						to.setText(null);
						to.setEnabled(false);
						options.clearSelection();
						
						// Reset selected
						selectedPlaces.clear();
						
						// Reset ListDisplayPanel
						MapPanel.this.ldp.alp.setSelected(true);
						MapPanel.this.ldp.pop.setEnabled(true);
						MapPanel.this.ldp.drawList();
					}
				});
				
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
//									.addComponent(noToll)
//									.addComponent(rating)
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
//											.addComponent(noToll)
											)
									.addComponent(findRoute)
									)
							.addGroup(sl.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(toLabel)
									.addComponent(to)
									.addComponent(lockTo)
									.addGroup(sl.createSequentialGroup()
//											.addComponent(rating)
											.addComponent(distance)
											)
									.addComponent(reset)
									)
							
							
						);
			}
			
		}
		
		public class EditButtonPanel extends JPanel {
			
			public EditButtonPanel() {
				super();
				this.setBackground(Color.BLUE);
				Dimension d = new Dimension(250, 80);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
				JButton edit = new JButton("Edit");
				edit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (selectedPlaces.peek() != null) {
							if (selectedPlaces.peek() instanceof City) {
								new EditFrame((City) selectedPlaces.peek(), currentMap.getAlpCityList(), currentMap);
							} else {
								POI poi = (POI) selectedPlaces.pop();
								City c = (City) selectedPlaces.peek();
								selectedPlaces.push(poi);
								new EditFrame(poi, c.getAlpPOITree().toArrayList(), currentMap);
							}
						}
					}
				});
				this.add(edit);
			}
		}
	}
}
