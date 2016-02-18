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
import backend.POI;
import backend.Place;


public class MapFrame extends JFrame{
	// constants
	private static final int FRAME_WIDTH = 1100;
	private static final int FRAME_HEIGHT = 930;
	private static final String FRAME_TITLE = "Kansas";
	// fields
	private Console cs;
	private MapPanel mp;
//	private int state;
	private City selectedCity;
	private POI selectedPOI;
//	private Place selectedPlace;
	private Place selectedFromPlace;
	private Place selectedToPlace;
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
		
		mp.mdp.addCityToMap();

		this.setVisible(true);
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
								
								selectedCity = cl.getCity();
								selectedPOI = null;
//								TODO: ldp.displayInfo(selectedPlace);
								
								// Set the to and from fields 
								if (mp.sfp.lockFrom.isSelected()) {
									if (!mp.sfp.lockTo.isSelected()) {
										// Updating To city
										mp.sfp.to.setText(cl.getLabel());
										selectedToPlace = cl.getCity();
//										if (!cl.getCity().getName().equals(cl.getLabel())) throw new RuntimeException("Labels are different!");
									}
								} else {
									// Updating From city
									mp.sfp.from.setText(cl.getLabel());
									selectedFromPlace = cl.getCity();
								}
								return;
							}
						}
						selectedCity = null;
						selectedPOI = null;
//						TODO: ldp.clearDisplayInfo();
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
					cls.add(new CircleLabel(temp.getName(), x, y, 25, c));
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
		
		public class ListDisplayPanel extends JPanel implements MouseListener{
			private ArrayList<Rectangle> arr;
			private ArrayList<JButton> cityListButtons;
			private ArrayList<JButton> cityInfoButtons;
			private JTextArea txt;
			
			public ListDisplayPanel() {//there is going to be parameter of some data structure of cities.
				super();
				cityListButtons=new ArrayList<>();
				cityInfoButtons=new ArrayList<>();
				Dimension d = new Dimension(250, 650);
				this.setMinimumSize(d);
				this.setPreferredSize(d);
				this.setMaximumSize(d);
				this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//				this.drawCityInfoButtons();
				this.initInfoButtons();
				this.initCitListButtons();
				this.drawCityListButtons();
			}
			
			private void initInfoButtons() {
				Dimension dimText = new Dimension(250, 300);
				txt = new JTextArea("Information about a city");//InfoOfCity
				Font font = new Font("", Font.PLAIN, 15);
				txt.setFont(font);
				txt.setMinimumSize(dimText);
				txt.setPreferredSize(dimText);
				txt.setMaximumSize(dimText);
				txt.setEditable(false);
				txt.setAlignmentX(CENTER_ALIGNMENT);
				Dimension d = new Dimension(250, 50);
				
				JButton goBack = new JButton("BACK");
				goBack.setMinimumSize(d);
				goBack.setPreferredSize(d);
				goBack.setMaximumSize(d);
				goBack.setAlignmentX(CENTER_ALIGNMENT);
				goBack.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ListDisplayPanel.this.removeAll();
						drawCityListButtons();
						ListDisplayPanel.this.repaint();
					}
				});
				
				for (int i = 1; i < 5; i++) {
					JButton POIButton = new JButton();
					POIButton.setText("POI " + i);
					POIButton.setMinimumSize(d);
					POIButton.setPreferredSize(d);
					POIButton.setMaximumSize(d);
					POIButton.setAlignmentX(CENTER_ALIGNMENT);
					cityInfoButtons.add(POIButton);
					POIButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							txt.setText("POI Clicked");
						}
					});
				}
				cityInfoButtons.add(goBack);
			}
			
			private void drawCityInfoButtons() {

				this.add(txt);
				for (int i = 0; i < cityInfoButtons.size(); i++) {
					this.add(cityInfoButtons.get(i));
				}
				
				updateUI();
				
			}
			private void initCitListButtons(){
				Dimension d = new Dimension(250, 50);
				ArrayList<City> arr= currentMap.getAlpCityList();
				for (int i = 0; i < arr.size(); i++) {
					JButton l = new JButton();
					l.setText(arr.get(i).getName());
					l.setMinimumSize(d);
					l.setPreferredSize(d);
					l.setMaximumSize(d);
					l.setAlignmentX(CENTER_ALIGNMENT);
					cityListButtons.add(l);
					l.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							ListDisplayPanel.this.removeAll();
							drawCityInfoButtons();
							ListDisplayPanel.this.repaint();
						}
					});
				}
			}
			private void drawCityListButtons() {
				for(int i=0;i<cityListButtons.size();i++){
					this.add(cityListButtons.get(i));
				}				
			}

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
						if (selectedCity != null) {
							if (selectedPOI == null) {
								new EditFrame(selectedCity, currentMap.getAlpCityList(), currentMap);
							} else {
								new EditFrame(selectedPOI, selectedCity.getAlpPOITree(), currentMap);
							}
						}
					}
				});
				this.add(edit);
			}
		}
	}
}
