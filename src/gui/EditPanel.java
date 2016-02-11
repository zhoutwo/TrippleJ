package gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import backend.City;
import backend.POI;

public class EditPanel extends JPanel {
	private boolean isCreateMode;
	private boolean isCity;
	private final City parentCity;
	private final POI currentPOI;

	public EditPanel() {
		super();
		this.isCreateMode = true;
		this.isCity = true;
		this.parentCity = null;
		this.currentPOI = null;
		this.populateFormNew();
	}
	
	public EditPanel(City parentCity) {
		super();
		this.isCreateMode = false;
		this.isCity = true;
		this.parentCity = parentCity;
		this.currentPOI = null;
		this.populateFormEdit();
	}
	
	public EditPanel(POI currentPOI) {
		super();
		this.isCreateMode = false;
		this.isCity = false;
		this.parentCity = null;
		this.currentPOI = currentPOI;
		this.populateFormEdit();
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
		if (this.isCreateMode == true && this.parentCity == null) {
			// Pop up say don't know what to edit.
		} else {
			this.isCreateMode = !this.isCreateMode;
			
		}
	}
}
