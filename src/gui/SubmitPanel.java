package gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SubmitPanel extends JPanel {
	private final JButton submit;
	private final JButton cancel;
	
	public SubmitPanel() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(250, 30));
		submit = new JButton("Submit");
		cancel = new JButton("Cancel");
		this.add(Box.createHorizontalGlue());
		this.add(submit);
		this.add(Box.createHorizontalGlue());
		this.add(cancel);
		this.add(Box.createHorizontalGlue());
	}
}
