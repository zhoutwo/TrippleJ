package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import backend.City;
import backend.FormData;
import backend.POI;

public class EditPanel extends JPanel {
	private boolean isCreateMode;
	private boolean isCity;
	private final JFrame frame;
	private final City currentCity;
	private final POI currentPOI;
	private final ArrayList<City> cityList;
	private final ArrayList<POI> poiList;
	
	// 1st row
	private final ModePanel modeRow = new ModePanel();
	// 2nd row
	private final TextFieldRow nameRow = new TextFieldRow("Name: ");
	private final ComboBoxRow nameComboRow = new ComboBoxRow("Name: ", new String[0]);
	// 3rd row
	private final TextFieldRow xRow = new TextFieldRow("X: ");
	// 4th row
	private final TextFieldRow yRow = new TextFieldRow("Y: ");
	// 5th row
	private final FormattedTextFieldRow ratingRow = new FormattedTextFieldRow("Rating: ", true);
	// 6th row
	private final ComboBoxRow typeRow = new ComboBoxRow("Type: ", new String[0]);
	// 7th row
	private final FormattedTextFieldRow populationRow = new FormattedTextFieldRow("Population: ", false);
	// last row
	private final SubmitPanel submitRow = new SubmitPanel();
	
//	ArrayList<City> cityList
	public EditPanel(JFrame frame) {
		super();
		this.frame = frame;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension windowDimension = new Dimension(300, 300);
		this.setMinimumSize(windowDimension);
		this.setPreferredSize(windowDimension);
		this.setMaximumSize(windowDimension);
		this.cityList = null; //cityList;
		this.currentCity = null;
		this.currentPOI = null;
		this.poiList = null;
		
		Dimension placeHolderDimension = new Dimension(300, 5);
		this.add(new Box.Filler(placeHolderDimension, placeHolderDimension, placeHolderDimension));
		
		this.add(modeRow);
		this.add(Box.createVerticalGlue());
		this.add(nameRow);
		this.add(xRow);
		this.add(yRow);
		this.add(ratingRow);
		this.add(typeRow);
		this.add(populationRow);
		this.add(Box.createVerticalGlue());
		this.add(submitRow);
		this.add(Box.createVerticalGlue());
	}
	
	private void closeWindow() {
//		this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING)); // If this closes all windows, then why pass in the frame?
		this.frame.setVisible(false);
		this.frame.dispose();
	}
	
	private boolean submitForm() {
		return false;
	}

	private void populateFormEdit() {
		this.populateFormBase();
	}
	
	private void populateFormNew() {
		this.populateFormBase();
	}
	
	private void populateFormBase() {
		
	}
	
	private void switchMode() {
		if (this.isCreateMode == true && this.currentCity == null) {
			// Pop up say don't know what to edit.
		} else {
			this.isCreateMode = !this.isCreateMode;
			
		}
	}
	
	public class ModePanel extends JPanel {
		private final ButtonGroup g;
		private final JRadioButton c;
		private final JRadioButton e;
		
		public ModePanel() {
			super();
			this.setPreferredSize(new Dimension(250, 30));
			this.setMaximumSize(new Dimension(250, 30));
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			g = new ButtonGroup();
			c = new JRadioButton("Create");
			e = new JRadioButton("Edit");
			g.add(c);
			g.add(e);
			this.add(c);
			this.add(e);
			this.add(Box.createHorizontalGlue());
		}
	}
	
	public class TextFieldRow extends JPanel {
		private final JLabel l;
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
				format.setMinimumIntegerDigits(1);
				format.setMaximumIntegerDigits(2);
				format.setMaximumFractionDigits(2);
				formatter.setMaximum(10);
			} else {
				format.setMaximumFractionDigits(0);
			}
//			formatter.setFormat(format);
			ft = new JFormattedTextField(formatter);
			Dimension ftd = new Dimension(150, 30);
			ft.setMinimumSize(ftd);
			ft.setPreferredSize(ftd);
			ft.setMaximumSize(ftd);
			this.add(ft);
		}
		
		public void setValue(String s) {
			throw new UnsupportedOperationException();
		}
		
		public String getValue() {
			return ft.getText();
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
		
		public String getValue() {
			return itemList[cb.getSelectedIndex()];
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
	}
}
