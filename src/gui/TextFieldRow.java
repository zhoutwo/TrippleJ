package gui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TextFieldRow extends JPanel {
	private final JLabel l;
	protected final JTextField t;
	
	public TextFieldRow(String lt) {
		super();
		this.setPreferredSize(new Dimension(250, 30));
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
