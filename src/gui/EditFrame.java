package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import backend.City;
import backend.FormData;
import backend.Map;
import backend.POI;
import backend.Place;

public class EditFrame extends JFrame {
	private final boolean isCity;
	private City currentCity;
	private POI currentPOI;
	private final HashMap<String, City> cityMap;
	private final HashMap<String, POI> poiMap;
	private final String[] names;
	private final Map map;
	
	public EditFrame(City c, ArrayList<City> cl, Map m) {
		super();
		// Set size for the display.
		Dimension d = new Dimension(300, 300);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		
		isCity = true;
		currentCity = c;
		currentPOI = null;
		cityMap = new HashMap<String, City>();
		poiMap = null;
		map = m;
		
		// Making another HashMap so it will be easy to retrieve Place objects from the list
		names = new String[cl.size()];
		for (int i = 0; i < cl.size(); i++) {
			cityMap.put(cl.get(i).getName(), cl.get(i));
			names[i] = cl.get(i).getName();
		}
		
		EditPanel ep = new EditPanel();
		this.add(ep);
		this.setTitle("Editing " + (isCity ? "City" : "Point-Of-Interest"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // We will only close this window if we press "X"
		this.setLocationRelativeTo(null); // Centers it.
		this.setVisible(true);
	}
	
	public EditFrame(POI p, City parent, Map m) {
		super();
		Dimension d = new Dimension(300, 300);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		
		isCity = false;
		currentCity = parent;
		currentPOI = p;
		cityMap = null;
		poiMap = new HashMap<String, POI>();
		map = m;
		
		// Making another HashMap so it will be easy to retrieve Place objects from the list
		ArrayList<POI> pl = parent.getAlpPOITree().toArrayList();
		names = new String[pl.size()];
		for (int i = 0; i < pl.size(); i++) {
			poiMap.put(pl.get(i).getName(), pl.get(i));
			names[i] = pl.get(i).getName();
		}
		
		EditPanel ep = new EditPanel();
		this.add(ep);
		this.setTitle("Editing " + (isCity ? "City" : "Point-Of-Interest"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // We will only close this window if we press "X"
		this.setLocationRelativeTo(null); // Centers it.
		this.setVisible(true);
	}
	
	public class EditPanel extends JPanel {	

		// 1st row
		private final ComboBoxRow nameComboRow = new ComboBoxRow("Name: ", names);;
		// 2nd row
		private final TextFieldRow xRow = new TextFieldRow("X: ");
		// 3th row
		private final TextFieldRow yRow = new TextFieldRow("Y: ");
		// 4th row
		private final FormattedTextFieldRow ratingRow = new FormattedTextFieldRow("Rating: ", true);
		// 5th row
		private final FormattedTextFieldRow populationRow = new FormattedTextFieldRow("Population: ", false);
		private final ComboBoxRow typeRow = new ComboBoxRow("Type: ", POI.AVAILABLE_TYPES);
		// 6th row
		private final FormattedTextFieldRow costRow = new FormattedTextFieldRow("Cost: ", false);
		// last row
		private final SubmitPanel submitRow = new SubmitPanel();
		
		public EditPanel() {
			super();
			// BoxLayout.Y_AXIS tells the layout manager that we want to add things vertically.
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			nameComboRow.cb.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						if (isCity) {
							currentCity = cityMap.get(arg0.getItem());
						} else {
							currentPOI = poiMap.get(arg0.getItem());
						}
						populateInfo();
					}
				}
			});
			
			populateInfo();
			populateFormBasic();
		}
		
		private void populateInfo() {
			Place p = isCity ? currentCity : currentPOI;
			nameComboRow.setValue(p.getName());
			xRow.setValue(p.getLocation().getX() + "");
			yRow.setValue(p.getLocation().getY() + "");
			ratingRow.setValue(p.getRating() + "");
			if (isCity) {
				populationRow.setValue(currentCity.getPopulation() + "");
			} else {
				typeRow.setValue(currentPOI.getType());
				costRow.setValue(currentPOI.getCost() + "");
			}
		}
		
		private void populateFormBasic() {
			// Add white space
			this.add(Box.createVerticalGlue());
			this.add(nameComboRow);
			this.add(xRow);
			this.add(yRow);
			this.add(ratingRow);
			// If it is a city, then put populationRow, otherwise put typeRow
			this.add(isCity ? populationRow : typeRow);
			// If it is a POI, then put costRow.
			if (!isCity) {
				this.add(costRow);
			}
			// Add white space 
			this.add(Box.createVerticalGlue());
			// Add submit button and cancel button
			this.add(submitRow);
			// Add white space
			this.add(Box.createVerticalGlue());
			EditFrame.this.pack();
			EditFrame.this.repaint();
		}
		
		public class TextFieldRow extends JPanel {
			protected final JLabel l;
			protected final JTextField t;
			
			public TextFieldRow(String lt) {
				super();
				this.setPreferredSize(new Dimension(250, 30));
				this.setMaximumSize(new Dimension(250, 30));
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				// Initialize layout
				l = new JLabel(lt);
				Dimension ld = new Dimension(80, 30);
				l.setMinimumSize(ld);
				l.setPreferredSize(ld);
				l.setMaximumSize(ld);
				t = new JTextField();
				Dimension td = new Dimension(150, 30);
				t.setMinimumSize(td);
				t.setPreferredSize(td);
				t.setMaximumSize(td);
				this.add(l);
				this.add(Box.createHorizontalGlue());
				this.add(t);
			}
			
			public void setValue(String s) {
				t.setText(s);
			}
			
			public String getValue() {
				return t.getText();
			}
		}
		
		public class FormattedTextFieldRow extends TextFieldRow{
			private JFormattedTextField ft;
			
			public FormattedTextFieldRow(String lt, boolean isRating) {
				super(lt);
				this.remove(t);
				// Setting format for the content based on the item
				DecimalFormat format = new DecimalFormat();
				if (isRating) {
					format.setMaximumIntegerDigits(1);
					format.setMaximumFractionDigits(1);
					format.applyPattern("0.0");
				} else {
					format.setMaximumFractionDigits(0);
				}
				ft = new JFormattedTextField(format);
				if (isRating) {
					// This is the listener to set a maximum rating of 5.0
					ft.addPropertyChangeListener("value", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {
							if (Double.parseDouble(FormattedTextFieldRow.this.ft.getValue().toString()) > 5) {
								FormattedTextFieldRow.this.ft.setValue(5);
							}
						}
					});
				}
				Dimension ftd = new Dimension(150, 30);
				ft.setMinimumSize(ftd);
				ft.setPreferredSize(ftd);
				ft.setMaximumSize(ftd);
				this.add(ft);
			}
			
			public void setValue(String s) {
				ft.setValue(Double.parseDouble(s));
			}
			
			public String getValue() {
				return ft.getText();
			}
			
			public void setEnabled(boolean b) {
				l.setEnabled(b);
				ft.setEditable(b);
			}
		}
		
		public class ComboBoxRow extends JPanel {
			private final JComboBox<String> cb;
			private final JLabel l;
			private final String[] itemList;
			
			public ComboBoxRow(String lt, String[] s) {
				super();
				this.setPreferredSize(new Dimension(250, 30));
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				this.setMaximumSize(new Dimension(250, 30));
				
				// Initializing layout
				itemList = s;
				l = new JLabel(lt);
				Dimension ld = new Dimension(80, 30);
				l.setMinimumSize(ld);
				l.setPreferredSize(ld);
				l.setMaximumSize(ld);
				cb = new JComboBox<String>(s);
				Dimension cd = new Dimension(150, 30);
				cb.setMinimumSize(cd);
				cb.setPreferredSize(cd);
				cb.setMaximumSize(cd);
				this.add(l);
				this.add(Box.createHorizontalGlue());
				this.add(cb);
			}
			
			public void setValue(String s) {
				cb.setSelectedItem(s);
			}
			
			public String getValue() {
				return itemList[cb.getSelectedIndex()];
			}
			
			public void setEnable(boolean b) {
				l.setEnabled(b);
				cb.setEnabled(b);
			}
		}
		
		public class SubmitPanel extends JPanel {
			private final JButton s;
			private final JButton c;
			
			public SubmitPanel() {
				super();
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				this.setPreferredSize(new Dimension(250, 30));
				s = new JButton("Submit");
				s.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
		            {
		                if (submitForm()) closeWindow();
		            }
				});
				c = new JButton("Cancel");
				c.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
		            {
		                closeWindow();
		            }
				});
				this.add(Box.createHorizontalGlue());
				this.add(s);
				this.add(Box.createHorizontalGlue());
				this.add(c);
				this.add(Box.createHorizontalGlue());
			}
			
			private void closeWindow() {
				// This is how you close only the current window, not all of them.
				EditFrame.this.setVisible(false);
				EditFrame.this.dispose();
			}
			
			private boolean submitForm() {
				FormData data;
				// Creates FormData based on whether it is city or not.
				if (isCity) {
					data = new FormData(currentCity, nameComboRow.getValue(), Double.parseDouble(xRow.getValue()), Double.parseDouble(yRow.getValue()), Double.parseDouble(ratingRow.getValue()), Integer.parseInt(populationRow.getValue().replace(",","")));
				} else {
					data = new FormData(currentPOI, nameComboRow.getValue(), Double.parseDouble(xRow.getValue()), Double.parseDouble(yRow.getValue()), currentCity, typeRow.getValue(), Double.parseDouble(ratingRow.getValue()), Double.parseDouble(costRow.getValue())); 
				}
				return map.updateFromFormData(data);
			}
		}
	}
}
	
