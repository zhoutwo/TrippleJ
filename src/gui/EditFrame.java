package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

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

public class EditFrame extends JFrame {
	
	public EditFrame() {
		super();
		Dimension d = new Dimension(300, 300);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setResizable(false);
		this.add(new EditPanel(null, new ArrayList<City>(), null));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null); // Centers it.
		this.setVisible(true);
	}
	
	public class EditPanel extends JPanel {
		private final boolean isCity;
		private final City currentCity;
		private final POI currentPOI;
		private final ArrayList<City> cityList;
		private final ArrayList<POI> poiList;
		private final Map map;
		
		// 1th row
//		private final Box.Filler placeHolder;
		// 2nd row
		private final ComboBoxRow nameComboRow = new ComboBoxRow("Name: ", new String[0]);
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
		
		public EditPanel(City c, ArrayList<City> cl, Map m) {
			super();
			// BoxLayout.Y_AXIS tells the layout manager that we want to add things vertically.
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			isCity = true;
			currentCity = c;
			currentPOI = null;
			cityList = cl;
			poiList = null;
			map = m;
			
			populateFormBasic();
		}
		
		public EditPanel(POI p, ArrayList<POI> pl, Map m) {
			super();
			// BoxLayout.Y_AXIS tells the layout manager that we want to add things vertically.
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			isCity = false;
			currentCity = null;
			currentPOI = p;
			cityList = null;
			poiList = pl;
			map = m;
			
			populateFormBasic();
		}
		
		private void populateFormBasic() {
			this.removeAll();
			this.add(Box.createVerticalGlue());
			this.add(nameComboRow);
			this.add(xRow);
			this.add(yRow);
			this.add(ratingRow);
//			typeRow.setEnabled(!isCity);
//			this.add(typeRow);
//			populationRow.setEnabled(isCity);
			this.add(isCity ? populationRow : typeRow);
			this.add(Box.createVerticalGlue());
			if (!isCity) {
				this.add(submitRow);
			}
			this.add(submitRow);
			this.add(Box.createVerticalGlue());
			EditFrame.this.pack();
			EditFrame.this.repaint();
		}
		
		public class ModePanel extends JPanel {
//			private final ButtonGroup g;
//			private final JRadioButton c;
//			private final JRadioButton e;
			
			/**
			 * 
			 */
			public ModePanel() {
				super();
//				this.setPreferredSize(new Dimension(250, 30));
//				this.setMaximumSize(new Dimension(250, 30));
//				this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//				g = new ButtonGroup();
//				c = new JRadioButton("Create");
//				e = new JRadioButton("Edit");
//				g.add(c);
//				g.add(e);
//				this.add(c);
//				this.add(e);
//				this.add(Box.createHorizontalGlue());
//				c.addItemListener(new ItemListener() {
//					public void itemStateChanged(ItemEvent arg0) {
//						if (isCreateMode != c.isSelected()) switchMode();
//					}
//				});
			}
//			
//			public boolean isCreateModeSelected() {
//				return c.isSelected();
//			}
//			
//			private void setCreateModeSelected(boolean b) {
//				c.setSelected(b);
//				e.setSelected(!b);
//			}
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
			
			public FormattedTextFieldRow(String lt, boolean isRating) {
				super(lt);
				this.remove(t);
				// Why is this not working.
				NumberFormat format = NumberFormat.getInstance();
				NumberFormatter formatter = new NumberFormatter(format);
				if (isRating) {
					format.setMaximumIntegerDigits(1);
					format.setMaximumFractionDigits(1);
					formatter.setMaximum(5);
				} else {
					format.setMaximumFractionDigits(0);
				}
//				formatter.setFormat(format);
				ft = new JFormattedTextField(formatter);
				Dimension ftd = new Dimension(150, 30);
				ft.setMinimumSize(ftd);
				ft.setPreferredSize(ftd);
				ft.setMaximumSize(ftd);
				this.add(ft);
			}
			
			public void setValue(String s) {
				ft.setValue(s);
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
				cb.setSelectedItem(s); //TODO This might not work
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
			
			private boolean submitForm() {
				FormData data = null;
				if (isCity) {
//					data = new FormData(isCreateMode, currentCity, nameRow.getValue(), Integer.parseInt(xRow.getValue()), Integer.parseInt(yRow.getValue()), Double.parseDouble(ratingRow.getValue()), Integer.parseInt(populationRow.getValue()));
				} else {
//					data = new FormData(isCreateMode, currentPOI, nameRow.getValue(), Integer.parseInt(xRow.getValue()), Integer.parseInt(yRow.getValue()), currentCity, typeRow.getValue(), Double.parseDouble(ratingRow.getValue()), Double.parseDouble(null)); 
				}
				return map.upsert(data);
			}
		}
	}
}
	
