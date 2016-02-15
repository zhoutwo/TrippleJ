package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import backend.Map;

public class MapPanel extends JPanel {
//	private Map stateMap;
	
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
		this.add(new MapDisplayPanel(), c);
		// Inserting ListDisplayPanel
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.50;
		c.weighty = 0.50;
		c.ipadx = 50;
		c.ipady = 50;
		this.add(new ListDisplayPanel(), c);
		// Inserting SearchFormPanel
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.50;
		c.weighty = 0.50;
		c.ipadx = 50;
		c.ipady = 50;
		this.add(new SearchFormPanel(), c);
		// Inserting EditButtonPanel
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.50;
		c.weighty = 0.50;
		c.ipadx = 50;
		c.ipady = 50;
		this.add(new EditButtonPanel(), c);
	}
	
	public class MapDisplayPanel extends JPanel {
		
		public MapDisplayPanel() {
			super();
			this.setBackground(Color.RED);
			Dimension d = new Dimension(650, 650);
			this.setMinimumSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
		}
		
	}
	
	public class ListDisplayPanel extends JPanel {
		
		public ListDisplayPanel() {
			super();
			this.setBackground(Color.WHITE);
			Dimension d = new Dimension(200, 650);
			this.setMinimumSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
		}
		
	}
	
	public class SearchFormPanel extends JPanel {
		
		public SearchFormPanel() {
			super();
			this.setBackground(Color.BLACK);
			Dimension d = new Dimension(650, 50);
			this.setMinimumSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
		}
		
	}
	
	public class EditButtonPanel extends JPanel {
		
		public EditButtonPanel() {
			super();
			this.setBackground(Color.BLUE);
			Dimension d = new Dimension(200, 50);
			this.setMinimumSize(d);
			this.setPreferredSize(d);
			this.setMaximumSize(d);
		}
		
	}
}
