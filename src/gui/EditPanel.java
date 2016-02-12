package gui;

import javax.swing.SpringLayout;
import javax.swing.text.MaskFormatter;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import backend.City;
import backend.POI;

public class EditPanel extends JPanel {
	private boolean isCreateMode;
	private boolean isCity;
	private final City currentCity;
	private final POI currentPOI;
	private final ArrayList<City> cityList;
	private final ArrayList<POI> poiList;
	
	// Form elements
	private final BoxLayout generalLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
	// 1st row
	private final JPanel radioPanel = new JPanel();
	private final ButtonGroup radioGroup = new ButtonGroup();
	private final JRadioButton create = new JRadioButton("Create");
	private final JRadioButton edit = new JRadioButton("Edit");
	// 2nd row
	private final JPanel namePanel = new JPanel();
	private final SpringLayout nameRow = new SpringLayout();
	private final JLabel nameLabel = new JLabel("Name: ");
	private final JTextField nameField = new JTextField();
	private final JComboBox nameCombo = new JComboBox();
	// 3rd row
	private final JPanel xPanel = new JPanel();
	private final SpringLayout xRow = new SpringLayout();
	private final JLabel xLabel = new JLabel("X: ");
	private final JTextField xField = new JTextField();
	// 4th row
	private final JPanel yPanel = new JPanel();
	private final SpringLayout yRow = new SpringLayout();
	private final JLabel yLabel = new JLabel("Y: ");
	private final JTextField yField = new JTextField();
	// 5th row
	private final JPanel ratingPanel = new JPanel();
	private final SpringLayout ratingRow = new SpringLayout();
	private final JLabel ratingLabel = new JLabel("Rating: ");
	private final JFormattedTextField ratingField = new JFormattedTextField("#.#"); // To be instantiated in the constructor.
	// 6th row
	private final JPanel typePanel = new JPanel();
	private final SpringLayout typeRow = new SpringLayout();
	private final JLabel typeLabel = new JLabel("Type: ");
	private final JComboBox<String> typeCombo = new JComboBox((new ArrayList<String>()).toArray()); //TODO: Put the actual array
	// 7th row
	private final JPanel populationPanel = new JPanel();
	private final SpringLayout populationRow = new SpringLayout();
	private final JLabel populationLabel = new JLabel("Population: ");
	private final JFormattedTextField populationField; // To be instantiated in the constructor.
	// last row
	private final JPanel lastPanel = new JPanel();
	private final SpringLayout lastRow = new SpringLayout();
	private final JButton submit = new JButton("Submit");
	private final JButton cancel = new JButton("Cancel");

	public EditPanel(ArrayList<City> cityList) {
		super();
		this.cityList = cityList;
		this.currentCity = null;
		this.currentPOI = null;
		this.poiList = null;
		this.populationField = new JFormattedTextField(this.getMaskForPopulationField());
	}
	
	private MaskFormatter getMaskForPopulationField() {
		MaskFormatter mask = new MaskFormatter();
		mask.setValidCharacters("1234567890");
		return mask;
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
}
