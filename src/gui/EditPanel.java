package gui;

import javax.swing.text.MaskFormatter;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import backend.City;
import backend.POI;

public class EditPanel extends JPanel {
	private boolean isCreateMode;
	private boolean isCity;
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
	public EditPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.cityList = null; //cityList;
		this.currentCity = null;
		this.currentPOI = null;
		this.poiList = null;
		
		this.add(modeRow);
		this.add(nameRow);
		this.add(xRow);
		this.add(yRow);
		this.add(ratingRow);
		this.add(typeRow);
		this.add(populationRow);
		this.add(submitRow);
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
