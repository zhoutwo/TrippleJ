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
/**
 * Edit Frame class displays edit panel that helps user to edit the information of places
 * isCity - boolean that checks if the place is city or POI 
 * currentCity - represents current city
 * currentPOI - represent current POI
 * cityMap - A HashMap that links city names to cities
 * poiMap - A HashMap that links POI names to pois
 * names - names for the list
 * map - current map for the MapFrame
 */
public class EditFrame extends JFrame {
	private final boolean isCity;
	private City currentCity;
	private POI currentPOI;
	private final HashMap<String, City> cityMap;
	private final HashMap<String, POI> poiMap;
	private final String[] names;
	private final Map map;
	
	/**
	 * The constructor initialize and display the frame for the panels.
	 * This constructor is to edit the city.
	 * 
	 * @param city current city to be edited 
	 * @param cityList list of cities
	 * @param map map that is shown in the MapFrame
	 */
	public EditFrame(City city, ArrayList<City> cityList, Map map) {
		super();
		// Set size for the display.
		Dimension d = new Dimension(300, 300);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		
		isCity = true;
		currentCity = city;
		currentPOI = null;
		cityMap = new HashMap<String, City>();
		poiMap = null;
		this.map = map;
		
		// Making another HashMap so it will be easy to retrieve Place objects from the list
		names = new String[cityList.size()];
		for (int i = 0; i < cityList.size(); i++) {
			cityMap.put(cityList.get(i).getName(), cityList.get(i));
			names[i] = cityList.get(i).getName();
		}
		
		EditPanel ep = new EditPanel();
		this.add(ep);
		this.setTitle("Editing " + (isCity ? "City" : "Point-Of-Interest"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // We will only close this window if we press "X"
		this.setLocationRelativeTo(null); // Centers it.
		this.setVisible(true);
	}
	
	/**
	 * The constructor initialize and display the frame for the panels.
	 * This constructor is to edit the POI.
	 * 
	 * @param p point of interest to be edited
	 * @param parent city that contains the point of interest
	 * @param map map that is displayed in MapFrame
	 */
	public EditFrame(POI poi, City parent, Map map) {
		super();
		Dimension d = new Dimension(300, 300);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		
		isCity = false;
		currentCity = parent;
		currentPOI = poi;
		cityMap = null;
		poiMap = new HashMap<String, POI>();
		this.map = map;
		
		// Making another HashMap so it will be easy to retrieve Place objects from the list
		ArrayList<POI> poiList = parent.getAlpPOITree().toArrayList();
		names = new String[poiList.size()];
		for (int i = 0; i < poiList.size(); i++) {
			poiMap.put(poiList.get(i).getName(), poiList.get(i));
			names[i] = poiList.get(i).getName();
		}
		
		EditPanel ep = new EditPanel();
		this.add(ep);
		this.setTitle("Editing " + (isCity ? "City" : "Point-Of-Interest"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // We will only close this window if we press "X"
		this.setLocationRelativeTo(null); // Centers it.
		this.setVisible(true);
	}
	/**
	 * This class represents the panel for editing the place.
	 * 
	 */
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
		
		
		/**
		 * The constructor initialize the panel.
		 */
		public EditPanel() {
			super();
			// BoxLayout.Y_AXIS tells the layout manager that we want to add things vertically.
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			nameComboRow.comboBox.addItemListener(new ItemListener() {
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
		
		/**
		 * This method obtains the data about population when editing.
		 * If the place to be edited is POI, it would not get population information.
		 */
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
		/**
		 * This class represents the text field row for the edit panel
		 * label - The JLabel before the text field
		 * textField - The text field
		 */
		public class TextFieldRow extends JPanel {
			protected final JLabel label;
			protected final JTextField textField;
			
			/**
			 * The constructor initialize the panel.
			 * @param lt String for the label
			 */
			public TextFieldRow(String lt) {
				super();
				this.setPreferredSize(new Dimension(250, 30));
				this.setMaximumSize(new Dimension(250, 30));
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				// Initialize layout
				label = new JLabel(lt);
				Dimension ld = new Dimension(80, 30);
				label.setMinimumSize(ld);
				label.setPreferredSize(ld);
				label.setMaximumSize(ld);
				textField = new JTextField();
				Dimension td = new Dimension(150, 30);
				textField.setMinimumSize(td);
				textField.setPreferredSize(td);
				textField.setMaximumSize(td);
				this.add(label);
				this.add(Box.createHorizontalGlue());
				this.add(textField);
			}
			
			/**
			 * set the text for the text field.
			 * @param s String to be changed in the text field
			 */
			public void setValue(String s) {
				textField.setText(s);
			}
			/**
			 * return the String that is in the text field.
			 * @return
			 */
			public String getValue() {
				return textField.getText();
			}
		}
		
		/**
		 * This class represents the text field row that is formatted
		 * ft - String for the label
		 */
		public class FormattedTextFieldRow extends TextFieldRow{
			private JFormattedTextField formatTextField;
			
			public FormattedTextFieldRow(String lt, boolean isRating) {
				super(lt);
				this.remove(textField);
				// Setting format for the content based on the item
				DecimalFormat format = new DecimalFormat();
				if (isRating) {
					format.setMaximumIntegerDigits(1);
					format.setMaximumFractionDigits(1);
					format.applyPattern("0.0");
				} else {
					format.setMaximumFractionDigits(0);
				}
				formatTextField = new JFormattedTextField(format);
				if (isRating) {
					// This is the listener to set a maximum rating of 5.0
					formatTextField.addPropertyChangeListener("value", new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent arg0) {
							if (Double.parseDouble(FormattedTextFieldRow.this.formatTextField.getValue().toString()) > 5) {
								FormattedTextFieldRow.this.formatTextField.setValue(5);
							}
						}
					});
				}
				Dimension ftd = new Dimension(150, 30);
				formatTextField.setMinimumSize(ftd);
				formatTextField.setPreferredSize(ftd);
				formatTextField.setMaximumSize(ftd);
				this.add(formatTextField);
			}
			
			public void setValue(String s) {
				formatTextField.setValue(Double.parseDouble(s));
			}
			
			public String getValue() {
				return formatTextField.getText();
			}
			
			public void setEnabled(boolean b) {
				label.setEnabled(b);
				formatTextField.setEditable(b);
			}
		}
		
		public class ComboBoxRow extends JPanel {
			private final JComboBox<String> comboBox;
			private final JLabel label;
			private final String[] itemList;
			
			public ComboBoxRow(String lt, String[] string) {
				super();
				this.setPreferredSize(new Dimension(250, 30));
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				this.setMaximumSize(new Dimension(250, 30));
				
				// Initializing layout
				itemList = string;
				label = new JLabel(lt);
				Dimension ld = new Dimension(80, 30);
				label.setMinimumSize(ld);
				label.setPreferredSize(ld);
				label.setMaximumSize(ld);
				comboBox = new JComboBox<String>(string);
				Dimension cd = new Dimension(150, 30);
				comboBox.setMinimumSize(cd);
				comboBox.setPreferredSize(cd);
				comboBox.setMaximumSize(cd);
				this.add(label);
				this.add(Box.createHorizontalGlue());
				this.add(comboBox);
			}
			
			public void setValue(String s) {
				comboBox.setSelectedItem(s);
			}
			
			public String getValue() {
				return itemList[comboBox.getSelectedIndex()];
			}
			
			public void setEnable(boolean b) {
				label.setEnabled(b);
				comboBox.setEnabled(b);
			}
		}
		
		public class SubmitPanel extends JPanel {
			private final JButton submit;
			private final JButton cancel;
			
			public SubmitPanel() {
				super();
				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
				this.setPreferredSize(new Dimension(250, 30));
				submit = new JButton("Submit");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
		            {
		                if (submitForm()) closeWindow();
		            }
				});
				cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
		            {
		                closeWindow();
		            }
				});
				this.add(Box.createHorizontalGlue());
				this.add(submit);
				this.add(Box.createHorizontalGlue());
				this.add(cancel);
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
	
