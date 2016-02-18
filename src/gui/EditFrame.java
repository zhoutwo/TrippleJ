package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private final City currentCity;
	private final POI currentPOI;
	private final ArrayList<City> cityList;
	private final HashMap<String, City> cityMap;
	private final ArrayList<POI> poiList;
	private final HashMap<String, POI> poiMap;
	private final String[] names;
	private final Map map;
	
	public EditFrame(City c, ArrayList<City> cl, Map m) {
		super();
		Dimension d = new Dimension(300, 300);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		
		isCity = true;
		currentCity = c;
		currentPOI = null;
		cityList = cl;
		cityMap = new HashMap<String, City>();
		poiList = null;
		poiMap = null;
		map = m;
		
		names = new String[cl.size()];
		for (int i = 0; i < cl.size(); i++) {
			cityMap.put(cl.get(i).getName(), cl.get(i));
			names[i] = cl.get(i).getName();
		}
		
		EditPanel ep = new EditPanel();
		this.add(ep);
		this.setTitle("Editing " + (isCity ? "City" : "Point-Of-Interest"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null); // Centers it.
		this.setVisible(true);
	}
	
	public EditFrame(POI p, ArrayList<POI> pl, Map m) {
		super();
		Dimension d = new Dimension(300, 300);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		
		isCity = false;
		currentCity = null;
		currentPOI = p;
		cityList = null;
		cityMap = null;
		poiList = pl;
		poiMap = new HashMap<String, POI>();
		map = m;
		
		names = new String[pl.size()];
		for (int i = 0; i < pl.size(); i++) {
			poiMap.put(pl.get(i).getName(), pl.get(i));
			names[i] = pl.get(i).getName();
		}
		
		EditPanel ep = new EditPanel();
		this.add(ep);
		this.setTitle("Editing " + (isCity ? "City" : "Point-Of-Interest"));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null); // Centers it.
		this.setVisible(true);
	}
	
	public class EditPanel extends JPanel {	
		// 1th row
//		private final Box.Filler placeHolder;
		// 2nd row
		private final ComboBoxRow nameComboRow = new ComboBoxRow("Name: ", names);;
		// 3rd row
		private final TextFieldRow xRow = new TextFieldRow("X: ");
		// 4th row
		private final TextFieldRow yRow = new TextFieldRow("Y: ");
		// 5th row
		private final FormattedTextFieldRow ratingRow = new FormattedTextFieldRow("Rating: ", true);
		// 6th row
		private final FormattedTextFieldRow populationRow = new FormattedTextFieldRow("Population: ", false);
		private final ComboBoxRow typeRow = new ComboBoxRow("Type: ", new String[0]);
		// 7th row
		private final FormattedTextFieldRow costRow = new FormattedTextFieldRow("Cost: ", false);
		// last row
		private final SubmitPanel submitRow = new SubmitPanel();
		
		public EditPanel() {
			super();
			// BoxLayout.Y_AXIS tells the layout manager that we want to add things vertically.
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			
			
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
			this.removeAll();
			this.add(Box.createVerticalGlue());
			this.add(nameComboRow);
			this.add(xRow);
			this.add(yRow);
			this.add(ratingRow);
			this.add(isCity ? populationRow : typeRow);
			this.add(Box.createVerticalGlue());
			if (!isCity) {
				this.add(costRow);
			}
			this.add(submitRow);
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
			private final boolean isRating;
			
			public FormattedTextFieldRow(String lt, boolean isRating) {
				super(lt);
				this.isRating = isRating;
				this.remove(t);
				// Why is this not working.
				DecimalFormat format = new DecimalFormat();
				
				if (isRating) {
//					format.setMaximumIntegerDigits(1);
//					format.setMaximumFractionDigits(1);
					format.applyPattern("0.0"); // TODO: Can't set fraction numbers
				} else {
					format.setMaximumFractionDigits(0);
				}
				NumberFormatter formatter = new NumberFormatter(format);
				if (isRating) formatter.setMaximum(5);
				ft = new JFormattedTextField(formatter);
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
				EditFrame.this.setVisible(false);
				EditFrame.this.dispose();
			}
			
			private boolean submitForm() {//TODO
				FormData data;
				if (isCity) {
					data = new FormData(currentCity, nameComboRow.getValue(), Integer.parseInt(xRow.getValue()), Integer.parseInt(yRow.getValue()), Double.parseDouble(ratingRow.getValue()), Integer.parseInt(populationRow.getValue()));
				} else {
					data = new FormData(currentPOI, nameComboRow.getValue(), Integer.parseInt(xRow.getValue()), Integer.parseInt(yRow.getValue()), currentCity, typeRow.getValue(), Double.parseDouble(ratingRow.getValue()), Double.parseDouble(costRow.getValue())); 
				}
				return map.updateFromFormData(data);
			}
		}
	}
}
	
