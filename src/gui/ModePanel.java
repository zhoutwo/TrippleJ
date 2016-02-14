package gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ModePanel extends JPanel {
	private final ButtonGroup g;
	private final JRadioButton c;
	private final JRadioButton e;
	
	public ModePanel() {
		super();
		this.setPreferredSize(new Dimension(250, 30));
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
